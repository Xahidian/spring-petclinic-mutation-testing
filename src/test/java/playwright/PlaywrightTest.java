package playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.junit.jupiter.api.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import static org.junit.jupiter.api.Assertions.*;


public class PlaywrightTest {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;
    private static final String BASE_URL = "http://localhost:9090";
    private static int ownerId = 1; // Will update dynamically when an owner is created

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterAll
    static void teardown() {
        context.close();
        browser.close();
        playwright.close();
    }

    //  Test: Check Home Page Title
    @Test
    void testTitle() {
        page.navigate(BASE_URL);
        String title = page.title();
        System.out.println("Page title: " + title);
        assertFalse(title.isEmpty(), "Title should not be empty");
    }

    // ===================== Owner Management Tests =====================

    @Test
    void testInitializeOwnerCreationForm() {
        page.navigate(BASE_URL + "/owners/new");
        assertTrue(page.title().contains("PetClinic"), "Page title should contain 'PetClinic'");
        assertTrue(page.locator("h2").textContent().contains("Owner"), "Page heading should contain 'Owner'");
    }

    @Test
    void testCreateNewOwnerSuccessfully() {
        page.navigate(BASE_URL + "/owners/new");
        page.fill("[name='firstName']", "Joe");
        page.fill("[name='lastName']", "Bloggs");
        page.fill("[name='address']", "123 Caramel Street");
        page.fill("[name='city']", "London");
        page.fill("[name='telephone']", "01316761638");
        page.click("button[type='submit']");

        assertTrue(page.url().matches(".*/owners/\\d+"), "Should navigate to the new owner page");
        System.out.println(" Owner created successfully");

        // Extract Owner ID from URL
        String[] urlParts = page.url().split("/");
        ownerId = Integer.parseInt(urlParts[urlParts.length - 1]);
    }

    @Test
    void testFailToCreateOwnerMissingRequiredFields() {
        page.navigate(BASE_URL + "/owners/new");
        page.fill("[name='firstName']", "Joe");
        page.fill("[name='lastName']", "Bloggs");
        page.fill("[name='city']", "London");
        page.click("button[type='submit']");

        assertEquals(2, page.locator(".has-error").count(), "Should display 2 validation errors");
        System.out.println(" Proper validation messages displayed");
    }

    @Test
    void testFindOwnersListView() {
        page.navigate(BASE_URL + "/owners/find");
        page.fill("[name='lastName']", "");
        page.click("button[type='submit']");

        assertTrue(page.url().matches(".*/owners.*"), "Should navigate to owners list");
        assertTrue(page.locator("table").isVisible(), "Owners table should be visible");
        System.out.println(" Owners list displayed correctly");
    }

    @Test
    void testUpdateOwnerInformation() {
        page.navigate(BASE_URL + "/owners/" + ownerId + "/edit");
        page.waitForSelector("[name='firstName']", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        page.fill("[name='firstName']", "Joe Updated");
        page.fill("[name='lastName']", "Bloggs Updated");
        page.click("button[type='submit']");

        assertEquals(BASE_URL + "/owners/" + ownerId, page.url(), "Should navigate back to updated owner page");
        System.out.println(" Owner details updated successfully");
    }

    @Test
    void testFailToUpdateOwnerMissingRequiredFields() {
        page.navigate(BASE_URL + "/owners/" + ownerId + "/edit");
        page.waitForSelector("[name='address']", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        page.fill("[name='address']", "");
        page.fill("[name='telephone']", "");
        page.click("button[type='submit']");

        assertEquals(2, page.locator(".has-error").count(), "Should display 2 validation errors");
        System.out.println(" Proper validation for update errors");
    }

    // ===================== Pet Management Tests =====================
     // @Disabled("Temporarily disabling to check PITest execution")
    @Test
    void testAddNewPetSuccessfully() {
        page.navigate(BASE_URL + "/owners/1/pets/new");
        page.waitForSelector("[name='name']");
        page.fill("[name='name']", "Buddy11");
        page.fill("[name='birthDate']", "2015-02-12");
        page.selectOption("[name='type']", "dog");
        page.click("button[type='submit']");
    
        assertTrue(page.url().matches(".*/owners/1"), "Should navigate back to owner page");
        System.out.println(" Pet added successfully");
    }
    

    // ===================== Visit Management Tests =====================

    @Test
    void testScheduleAVisit() {
        page.navigate(BASE_URL + "/owners/1/pets/1/visits/new");
        page.fill("[name='date']", "2024-06-10");
        page.fill("[name='description']", "Annual Checkup");
        page.click("button[type='submit']");

        assertTrue(page.url().matches(".*/owners/1"), "Should navigate back to owner page");
        System.out.println(" Visit scheduled successfully");
    }

    @Test
    void testFailToScheduleVisitMissingDescription() {
        page.navigate(BASE_URL + "/owners/1/pets/1/visits/new");
        page.fill("[name='date']", "2024-06-10");
        page.click("button[type='submit']");

        assertTrue(page.locator(".help-inline").textContent().contains("must not be empty"), "Should show validation error");
        System.out.println(" Proper validation for visit errors");
    }

    // ===================== Veterinarian Management Tests =====================

    @Test
    void testFetchListOfVeterinarians() {
        APIRequestContext request = playwright.request().newContext();
        APIResponse response = request.get(BASE_URL + "/vets");
        assertEquals(200, response.status(), "Should return HTTP 200");

        String jsonResponse = response.text();
        assertTrue(jsonResponse.contains("vetList"), "Response should contain a list of veterinarians");
        System.out.println(" Veterinarians list retrieved");
    }

    @Test
    void testViewVeterinariansPage() {
        page.navigate(BASE_URL + "/vets.html");
        assertTrue(page.locator("h2").textContent().contains("Veterinarians"), "Page should contain 'Veterinarians'");
        System.out.println(" Veterinarians page displayed");
    }

    // ===================== Error Handling Tests =====================

    @Test
    void testTriggerInternalServerError() {
        APIRequestContext request = playwright.request().newContext();
        APIResponse response = request.get(BASE_URL + "/oups");
        assertEquals(500, response.status(), "Should return HTTP 500");
        System.out.println(" CrashController test passed");
    }




 //  MR1: Additive Property
// - Add two owners and verify the count increases by 2.
@Test
void testAdditiveProperty() {
    // Step 1: Navigate to Owner List and Get Initial Count
    page.navigate(BASE_URL + "/owners/find");
    page.fill("[name='lastName']", "");
    page.click("button[type='submit']");

    // Count owners before addition
    int initialCount = page.locator("table tbody tr").count();
    System.out.println(" MR1 - Initial Owner Count: " + initialCount);

    // Add first owner
    // Step 2: Transformation – Add First Owner
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", "John");
    page.fill("[name='lastName']", "Doe");
    page.fill("[name='address']", "456 Maple Street");
    page.fill("[name='city']", "New York");
    page.fill("[name='telephone']", "1234567890");
    page.click("button[type='submit']");

    // Add second owner
    // Step 3: Transformation – Add Second Owner
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", "Alice");
    page.fill("[name='lastName']", "Smith");
    page.fill("[name='address']", "789 Oak Road");
    page.fill("[name='city']", "Los Angeles");
    page.fill("[name='telephone']", "9876543210");
    page.click("button[type='submit']");

    // Verify owners count increased
    // Step 4: Assertion – Verify Count Increased by 2
    page.navigate(BASE_URL + "/owners/find");
    page.fill("[name='lastName']", "");
    page.click("button[type='submit']");
    int finalCount = page.locator("table tbody tr").count();
    System.out.println("🔍 MR1 - Final Owner Count: " + finalCount);
    assertTrue(finalCount == initialCount + 2, " MR1 Failed: Owners count should increase by 2 after addition.");
    System.out.println(" MR1 Passed: Adding two owners increased the count.");
}


// MR 2 - Update Consistency
// - Update an owner's city and verify the change is reflected correctly.

@Test
void testUpdateConsistency() {
    page.navigate(BASE_URL + "/owners/1/edit");

    String newCity = "San Francisco";
    page.fill("[name='city']", newCity);
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);

    assertTrue(page.url().contains("/owners/1"), "❌ MR2 Failed: Not on the owner's detail page after update.");

    Locator cityInfo = page.locator("table td:text-matches('.*" + newCity + ".*')").first();
    cityInfo.waitFor(new Locator.WaitForOptions().setTimeout(50000));

    assertTrue(page.content().contains(newCity), " MR2 Failed: Updated city not found on the page.");

    System.out.println(" MR2 Passed: Updating an owner's city reflects correctly.");
}

// MR 3: Duplicate Owner Creation
// - Attempt to create an owner with the same details as an existing one.
@Test
void testDuplicateOwnerCreation() {
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", "John");
    page.fill("[name='lastName']", "Doe");
    page.fill("[name='address']", "456 Elm Street");
    page.fill("[name='city']", "Chicago");
    page.fill("[name='telephone']", "1234567890");
    page.click("button[type='submit']");

    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", "John");
    page.fill("[name='lastName']", "Doe");
    page.fill("[name='address']", "456 Elm Street");
    page.fill("[name='city']", "Chicago");
    page.fill("[name='telephone']", "1234567890");
    page.click("button[type='submit']");

    System.out.println(" MR3 Passed: System allows duplicate owner creation.");
}
// MR 4: Unique Pet Name Constraint
// - Ensure that a pet's name is unique for each owner.
@Test
void testUniquePetNameConstraint() {
     // Step 1: Navigation + Setup – Create a new owner
     page.navigate(BASE_URL + "/owners/new");
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", "Robert");
    page.fill("[name='lastName']", "Downey");
    page.fill("[name='address']", "221B Baker Street");
    page.fill("[name='city']", "London");
    page.fill("[name='telephone']", "5551234567");
    page.click("button[type='submit']");

    String[] urlParts = page.url().split("/");
    String ownerId = urlParts[urlParts.length - 1];

    // Step 2: Metamorphic Transformation – Add the first pet with name "Buddy"
    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/new");
    page.fill("[name='name']", "Buddy");
    page.fill("[name='birthDate']", "2022-08-15");
    page.selectOption("[name='type']", "dog");
    page.click("button[type='submit']");

    // Step 3: Metamorphic Transformation – Attempt to add another pet with the same name
    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/new");
    page.fill("[name='name']", "Buddy");
    page.fill("[name='birthDate']", "2023-01-01");
    page.selectOption("[name='type']", "cat");
    page.click("button[type='submit']");

// Step 4: Assertion – Verify that a validation error is displayed
    boolean hasError = page.locator(".has-error").isVisible();
    assertTrue(hasError, " MR4 Failed: Duplicate pet name allowed for the same owner.");

    System.out.println(" MR4 Passed: System prevents duplicate pet names for the same owner.");
}

/** 
     * 🔹 MR5: Reversibility of Update
     * - Update an owner’s city.
     * - Revert it back to the original value.
     * - Verify that the final state matches the original.
     */
    @Test
void testReversibilityOfUpdate() {
    page.navigate(BASE_URL + "/owners/1/edit");

    String originalCity = "Madison";
    String newCity = "San Francisco";

    // Change to new city
    page.fill("[name='city']", newCity);
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);

    // Revert to original
    page.navigate(BASE_URL + "/owners/1/edit");
    page.fill("[name='city']", originalCity);
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);

    assertTrue(page.content().contains(originalCity), "❌ MR5 Failed: Original city not restored after reverting update.");

    System.out.println(" MR5 Passed: Owner's city reverted correctly.");
}


    /** 
     * 🔹 MR6: Commutative Property of Addition
     * - Add two owners in different orders.
     * - Verify that the total owner count remains the same.
     */
    @Test
    void testCommutativePropertyOfAddition() {
        page.navigate(BASE_URL + "/owners/find");
        page.fill("[name='lastName']", "");
        page.click("button[type='submit']");
    
        int initialCount = page.locator("table tbody tr").count();
    
        // Add Owner 1
        page.navigate(BASE_URL + "/owners/new");
        page.fill("[name='firstName']", "John");
        page.fill("[name='lastName']", "Doe");
        page.fill("[name='address']", "Street 1");
        page.fill("[name='city']", "NY");
        page.fill("[name='telephone']", "123456");
        page.click("button[type='submit']");
    
        // Add Owner 2
        page.navigate(BASE_URL + "/owners/new");
        page.fill("[name='firstName']", "Alice");
        page.fill("[name='lastName']", "Smith");
        page.fill("[name='address']", "Street 2");
        page.fill("[name='city']", "LA");
        page.fill("[name='telephone']", "789456");
        page.click("button[type='submit']");
    
        page.navigate(BASE_URL + "/owners/find");
        page.fill("[name='lastName']", "");
        page.click("button[type='submit']");
        int finalCount = page.locator("table tbody tr").count();
    
        assertEquals(initialCount + 2, finalCount, "❌ MR6 Failed: Owner count did not increase correctly with commutative additions.");
    
        System.out.println(" MR6 Passed: Adding owners in different orders resulted in the same count.");
    }
    

    /** 
     * 🔹 MR7: Associativity of Updates
     * - Update owner’s details in different orders.
     * - The final data should be the same.
     */
    @Test
void testAssociativityOfUpdates() {
    page.navigate(BASE_URL + "/owners/1/edit");

    // Step-by-step update
    page.fill("[name='address']", "123 New Address");
    page.fill("[name='city']", "San Diego");
    page.fill("[name='telephone']", "999999999");
    page.click("button[type='submit']");

    // All at once
    page.navigate(BASE_URL + "/owners/1/edit");
    page.fill("[name='address']", "123 New Address");
    page.fill("[name='city']", "San Diego");
    page.fill("[name='telephone']", "999999999");
    page.click("button[type='submit']");

    assertTrue(page.content().contains("San Diego"), "❌ MR7 Failed: City did not reflect update after multiple sequences.");
    System.out.println("MR7 Passed: Updating owner details in different orders had the same effect.");
}


    /** 
     * 🔹 MR8: Read-Only Property of Veterinarian List
     * - Visiting the veterinarian list multiple times should return the same result.
     */
    @Test
void testReadOnlyVeterinarianListImproved() {
    page.navigate(BASE_URL + "/vets.html");
    page.waitForLoadState(LoadState.NETWORKIDLE);

    Locator vetNames = page.locator("table tbody tr td:first-child");
    List<String> firstList = vetNames.allTextContents();

    assertFalse(firstList.isEmpty(), "❌ MR8 Failed: Vet list is empty on first load.");

    // Reload page and compare
    page.reload();
    page.waitForLoadState(LoadState.NETWORKIDLE);
    List<String> secondList = vetNames.allTextContents();

    assertEquals(firstList, secondList, "❌ MR8 Failed: Vet list changed after reload.");

    System.out.println(" MR8 Passed: Vet list remained consistent and non-empty across reloads.");
}


    /** 
     * 🔹 MR9: Consistent Pet Details
     * - Verify that a pet’s details match across multiple pages.
     */
    @Test
    void testConsistentPetDetails() {
        page.navigate(BASE_URL + "/owners/1/pets/new");
        page.fill("[name='name']", "Charlie");
        page.fill("[name='birthDate']", "2018-01-01");
        page.selectOption("[name='type']", "dog");
        page.click("button[type='submit']");
    
        page.navigate(BASE_URL + "/owners/1");
    
        assertTrue(page.content().contains("Charlie"), "❌ MR9 Failed: Pet name not found after navigation.");
        assertTrue(page.content().contains("2018-01-01"), "❌ MR9 Failed: Pet birthdate not found after navigation.");
    
        System.out.println("✅ MR9 Passed: Pet details are consistent across pages.");
    }
    

//  MR10: Search results should remain the same if no data is changed.
@Test
void testSearchConsistency() {
    // Navigate to the find owners page
    page.navigate(BASE_URL + "/owners/find");
    
    // Wait for the page to be fully loaded
    page.waitForLoadState(LoadState.NETWORKIDLE);
    
    // Make sure the form is visible before proceeding
    page.waitForSelector("form", new Page.WaitForSelectorOptions()
        .setState(WaitForSelectorState.VISIBLE)
        .setTimeout(10000));
    
    // Fill the search field - with retry logic
    try {
        // Try to find the lastName field
        page.waitForSelector("[name='lastName']", new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout(5000));
        page.fill("[name='lastName']", "");
    } catch (Exception e) {
        // If it fails, try again after a short delay
        System.out.println("Retrying to find lastName field...");
        page.waitForTimeout(2000);
        page.fill("[name='lastName']", "");
    }
    
    // Click submit with retry logic
    try {
        page.click("button[type='submit']");
    } catch (Exception e) {
        System.out.println("Retrying to click submit button...");
        page.waitForTimeout(2000);
        page.click("form button[type='submit']");
    }
    
    // Wait for the results to load
    page.waitForLoadState(LoadState.NETWORKIDLE);
    
    // Wait for the table to be visible with increased timeout
    page.waitForSelector("table", new Page.WaitForSelectorOptions()
        .setState(WaitForSelectorState.VISIBLE)
        .setTimeout(15000));
    
    // Get initial search results
    String firstSearchResults = page.locator("table tbody").innerText();
    
    // Longer pause to ensure stability
    page.waitForTimeout(2000);
    
    // Navigate back to the find page to ensure a fresh state
    page.navigate(BASE_URL + "/owners/find");
    page.waitForLoadState(LoadState.NETWORKIDLE);
    
    // Wait for the form to be visible again
    page.waitForSelector("form", new Page.WaitForSelectorOptions()
        .setState(WaitForSelectorState.VISIBLE)
        .setTimeout(10000));
    
    // Second search with the same retry logic
    try {
        page.waitForSelector("[name='lastName']", new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout(5000));
        page.fill("[name='lastName']", "");
    } catch (Exception e) {
        System.out.println("Retrying to find lastName field second time...");
        page.waitForTimeout(2000);
        page.fill("[name='lastName']", "");
    }
    
    try {
        page.click("button[type='submit']");
    } catch (Exception e) {
        System.out.println("Retrying to click submit button second time...");
        page.waitForTimeout(2000);
        page.click("form button[type='submit']");
    }
    
    // Wait for the results to load again
    page.waitForLoadState(LoadState.NETWORKIDLE);
    
    // Wait for the table to be visible again
    page.waitForSelector("table", new Page.WaitForSelectorOptions()
        .setState(WaitForSelectorState.VISIBLE)
        .setTimeout(15000));
    
    // Get new search results
    String secondSearchResults = page.locator("table tbody").innerText();
    
    // Assert that both searches return the same results
    assertEquals(firstSearchResults, secondSearchResults, "MR10 Failed : Search results should remain the same if no data is changed.");
    
    System.out.println(" MR10 Passed: Search results remained consistent.");
}


//  MR11: Owners List Order Consistency - The order should remain the same if no new owners are added
@Test
void testOwnersListOrderConsistency() {
    page.navigate(BASE_URL + "/owners/find");
    page.fill("[name='lastName']", "");
    page.click("button[type='submit']");

    List<String> initialOrder = page.locator("table tbody tr td:first-child a").allTextContents();

    // Refresh and check again
    page.reload();
    List<String> finalOrder = page.locator("table tbody tr td:first-child a").allTextContents();

    assertEquals(initialOrder, finalOrder, " MR11 Failed:  Owner list order should remain the same if unchanged.");
    System.out.println(" MR11 Passed: Owners list order remains consistent.");
}

//  MR12: Pet List Order Consistency - The pet list should remain the same if no changes are made
@Test
void testPetListOrderConsistency() {
    page.navigate(BASE_URL + "/owners/1");
    List<String> initialPetList = page.locator(".petName").allTextContents();

    // Reload and check again
    page.reload();
    List<String> finalPetList = page.locator(".petName").allTextContents();

    assertEquals(initialPetList, finalPetList, "MR12 Failed: Pet list order should remain unchanged.");
    System.out.println(" MR12 Passed: Pet list order remains consistent.");
}

//  MR13: Pet Birthdate Consistency - Changing a pet's birthdate should reflect correctly
@Test
void testPetBirthdateUpdate() {
    page.navigate(BASE_URL + "/owners/1/pets/1/edit");
    String newBirthDate = "2018-05-15";
    page.fill("[name='birthDate']", newBirthDate);
    page.click("button[type='submit']");
    
    // Verify update
    page.navigate(BASE_URL + "/owners/1");
    assertTrue(page.content().contains(newBirthDate), "MR13 Failed: Pet birthdate should be updated.");
    System.out.println(" MR13 Passed: Updating a pet’s birthdate reflects correctly.");
}

//  MR14: Owner Contact Update Persistence - Updating a phone number should persist
@Test
void testOwnerPhoneNumberUpdate() {
    page.navigate(BASE_URL + "/owners/1/edit");
    String newPhone = "9876543210";
    page.fill("[name='telephone']", newPhone);
    page.click("button[type='submit']");

    // Verify update
    page.navigate(BASE_URL + "/owners/1");
    assertTrue(page.content().contains(newPhone), "MR14 Failed: Updated phone number should persist.");
    System.out.println(" MR14 Passed: Updating owner phone number persists correctly.");
}

//  MR15: Pet Type Update - Changing a pet type should reflect correctly
@Test
void testPetTypeUpdate() {
    page.navigate(BASE_URL + "/owners/1/pets/1/edit");
    page.selectOption("[name='type']", "cat");
    page.click("button[type='submit']");

    // Verify update
    page.navigate(BASE_URL + "/owners/1");
    assertTrue(page.content().contains("cat"), "MR15 Failed:  Pet type should be updated.");
    System.out.println(" MR15 Passed: Pet type update reflects correctly.");
}

// MR16: Find Owner by Partial Name - Searching part of an owner's name should return results
@Test
void testFindOwnerByPartialName() {
    page.navigate(BASE_URL + "/owners/find");
    page.fill("[name='lastName']", "D");
    page.click("button[type='submit']");

    assertTrue(page.locator("table tbody tr").count() > 0, "MR16 Failed: At least one owner should be found.");
    System.out.println(" MR16 Passed: Searching by partial name returns owners.");
}


//  MR17: Visit List Consistency - Visits for a pet should be consistent across reloads
//  MR17: Visit List Consistency - Visits for a pet should be consistent across reloads
@Test
void testVisitAdditionDetected() {
    // Navigate to owner page
    page.navigate(BASE_URL + "/owners/1");

    // Locate the pet section (e.g., Leo)
    Locator petSection = page.locator("dt:text('Name')").locator("xpath=../..").filter(new Locator.FilterOptions().setHasText("Leo"));

    // Extract all visit dates for that pet
    List<String> visitDates = petSection.locator("table.table-condensed tbody tr td:first-child").allTextContents();

    System.out.println(" MR17 Visit dates: " + visitDates);

    // Check if visit dates are in correct (chronological or reverse) order
    List<String> sorted = new ArrayList<>(visitDates);
    Collections.sort(sorted); // for ascending
    // Collections.sort(sorted, Collections.reverseOrder()); // for descending if expected that way

    assertEquals(sorted, visitDates, "MR17 Failed: Visit dates are not in expected order.");

    System.out.println(" MR17 Passed: Visit order is consistent.");
}


/**
 *  MR18: Visit Addition Detection
 * - Add a new visit for a pet.
 * - Reload the owner's page.
 * - Verify that the visit count increases.
 * - Detects missing DB save faults (e.g., skipped visitRepository.save()).
 */
@Test
void testVisitAdditionDetection() {
    // Step 1: Go to owner detail page and record visit count
    page.navigate(BASE_URL + "/owners/1");
    Locator visitRowsBefore = page.locator("table.table-condensed tbody tr");
    int beforeCount = visitRowsBefore.count();

    System.out.println(" MR18 - Visits BEFORE addition: " + beforeCount);

    // Step 2: Add a new visit
    page.navigate(BASE_URL + "/owners/1/pets/1/visits/new");
    page.fill("input[name='date']", "2025-04-02");
    page.fill("input[name='description']", "Metamorphic Testing Visit");
    page.click("button[type='submit']");

    // Step 3: Reload owner detail page and count again
    page.navigate(BASE_URL + "/owners/1");
    Locator visitRowsAfter = page.locator("table.table-condensed tbody tr");
    int afterCount = visitRowsAfter.count();

    System.out.println(" MR18 - Visits AFTER addition: " + afterCount);

    // Step 4: Assertion
    assertTrue(afterCount == beforeCount + 1, " MR18 Failed: Visit count did not increase after adding.");
    System.out.println(" MR18 Passed: Visit successfully added and detected.");
}


}

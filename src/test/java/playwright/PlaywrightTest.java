package playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaywrightTest {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;
    private static final String BASE_URL = "http://localhost:9090";

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

    /**
     * MR1: Additive Property of Owners List
     * - Seed Input: Count owners after adding one.
     * - Transformation: Add another owner.
     * - Morphed Output: Count owners again.
     * - Relation: Count should increase by 1.
     */
 @Test
    void testMR1_AdditiveProperty() {
        page.navigate(BASE_URL + "/owners/new");
        page.fill("[name='firstName']", "John");
        page.fill("[name='lastName']", "Doe");
        page.fill("[name='address']", "123 St");
        page.fill("[name='city']", "NY");
        page.fill("[name='telephone']", "123456");
        page.click("button[type='submit']");
        System.out.println("🔹 Seed Input: First owner added.");

        page.navigate(BASE_URL + "/owners/find");
        page.fill("[name='lastName']", "");
        page.click("button[type='submit']");
        int seedCount = page.locator("table tbody tr").count();
        System.out.println("🔹 Seed Output: Owner count after first addition = " + seedCount);

        page.navigate(BASE_URL + "/owners/new");
        page.fill("[name='firstName']", "Alice");
        page.fill("[name='lastName']", "Smith");
        page.fill("[name='address']", "456 St");
        page.fill("[name='city']", "LA");
        page.fill("[name='telephone']", "789456");
        page.click("button[type='submit']");
        System.out.println("🔹 Morphed Input: Second owner added.");

        page.navigate(BASE_URL + "/owners/find");
        page.fill("[name='lastName']", "");
        page.click("button[type='submit']");
        int morphedCount = page.locator("table tbody tr").count();
        System.out.println("🔹 Morphed Output: Owner count after second addition = " + morphedCount);

        System.out.println("🔹 Relation Check: morphedCount == seedCount + 1");
        assertEquals(seedCount + 1, morphedCount, "MR1 Failed: Owner count should increase by exactly 1.");
        System.out.println("✅ MR1 Passed: Owner count increased as expected.");
    }


    /**
     * MR2: Reversibility of Owner Update
     * - Seed Input: Capture original city.
     * - Transformation: Change city, then revert.
     * - Morphed Output: Fetch city after reversion.
     * - Relation: City should match original.
     */
@Test
void testMR2_ReversibilityOfUpdate() {
    // 🔹 Step 1: Seed Input – Create a new owner with a controlled city value
    String seedCity = "Turku";
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", "Test");
    page.fill("[name='lastName']", "Owner");
    page.fill("[name='address']", "123 Main St");
    page.fill("[name='city']", seedCity);
    page.fill("[name='telephone']", "1234567890");
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);

    // Extract dynamic ownerId from URL
    String[] urlParts = page.url().split("/");
    String ownerId = urlParts[urlParts.length - 1];
    System.out.println("🔹 Seed Input: New owner created with city = " + seedCity + ", Owner ID = " + ownerId);

    // 🔹 Step 2: Seed Output – Confirm original city is correct
    page.navigate(BASE_URL + "/owners/" + ownerId);
    String seedOutputCity = page.textContent("table th:has-text('City') + td").trim();
    System.out.println("🔹 Seed Output: City after input = " + seedOutputCity);

    // 🔹 Step 3: Morphed Input – Update city to a new value
    String morphedCity = "Helsiniki";
    page.navigate(BASE_URL + "/owners/" + ownerId + "/edit");
    page.fill("[name='city']", morphedCity);
    page.click("button[type='submit']");
    System.out.println("🔹 Morphed Input: Updated city to = " + morphedCity);

    // 🔹 Step 4: Morphed Output – Confirm city updated
    page.navigate(BASE_URL + "/owners/" + ownerId);
    String morphedOutputCity = page.textContent("table th:has-text('City') + td").trim();
    System.out.println("🔹 Morphed Output: City after update = " + morphedOutputCity);

    // 🔹 Step 5: Additional Transformation – Revert city back to seed
    page.navigate(BASE_URL + "/owners/" + ownerId + "/edit");
    page.fill("[name='city']", seedCity);
    page.click("button[type='submit']");
    System.out.println("🔹 Additional Input: City reverted to original = " + seedCity);

    // 🔹 Step 6: Final Output – Confirm city reverted
    page.navigate(BASE_URL + "/owners/" + ownerId);
    String finalOutputCity = page.textContent("table th:has-text('City') + td").trim();
    System.out.println("🔹 Final Output: City after reversion = " + finalOutputCity);

    // 🔹 Step 7: Relation Check – Final output should match seed output
    System.out.println("🔹 Relation Check: finalOutputCity == seedOutputCity");
    assertEquals(seedOutputCity, finalOutputCity, "❌ MR2 Failed: City did not revert to original value.");
    System.out.println("✅ MR2 Passed: City reverted to original value as expected.");
}


    /**
     * MR3: Consistent Pet Details
     * - Seed Input: Create a New Owner with pet name, type, and birth date.
     * - Transformation: Change pet type.
     * - Morphed Output: Fetch updated pet details.
     * - Relation: Name and birth date unchanged, type changed as expected.
     */
@Test
void testMR3_ConsistentPetDetails() {
    // Seed Input: Create a new owner
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", "SeedOwner");
    page.fill("[name='lastName']", "Test");
    page.fill("[name='address']", "123 Test St");
    page.fill("[name='city']", "TestCity");
    page.fill("[name='telephone']", "1234567890");
    page.click("button[type='submit']");
    String[] urlParts = page.url().split("/");
    String ownerId = urlParts[urlParts.length - 1];  // Dynamic ownerId
    System.out.println("🔹 Seed Input: Created owner " + ownerId);

    // Seed Input: Add a new pet for this owner
    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/new");
    page.fill("[name='name']", "Leo");
    page.fill("[name='birthDate']", "2015-09-07");
    page.selectOption("[name='type']", "dog");
    page.click("button[type='submit']");
    System.out.println("🔹 Seed Input: Created pet with name Leo, type dog, birth date 2015-09-07");

    // Extract petId dynamically by navigating back to owner detail page
    page.navigate(BASE_URL + "/owners/" + ownerId);
    page.waitForSelector("h2:has-text('Pets and Visits')");
    Locator petTable = page.locator("h2:has-text('Pets and Visits') + table");
    String petLink = petTable.locator("tr td a").first().getAttribute("href");  // Extract pet edit link
    String[] petUrlParts = petLink.split("/");
    String petId = petUrlParts[petUrlParts.length - 2];  // Extract dynamic petId
    System.out.println("🔹 Seed Input: Extracted petId " + petId);

    // Seed Output: Capture pet details
    Locator details = petTable.locator("tr td dl");
    String seedName = details.locator("dt:has-text('Name') + dd").first().textContent().trim();
    String seedType = details.locator("dt:has-text('Type') + dd").first().textContent().trim();
    String seedBirth = details.locator("dt:has-text('Birth Date') + dd").first().textContent().trim();
    System.out.println("🔹 Seed Output: Name = " + seedName + ", Type = " + seedType + ", Birth Date = " + seedBirth);

    // Morphed Input: Change pet type
    String newType = seedType.equalsIgnoreCase("dog") ? "cat" : "dog";
    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/" + petId + "/edit");
    page.waitForSelector("[name='type']");
    page.selectOption("[name='type']", newType);
    page.click("button[type='submit']");
    System.out.println("🔹 Morphed Input: Updated pet type to " + newType);

    // Morphed Output: Fetch updated pet details
    page.navigate(BASE_URL + "/owners/" + ownerId);
    String morphedName = details.locator("dt:has-text('Name') + dd").first().textContent().trim();
    String morphedType = details.locator("dt:has-text('Type') + dd").first().textContent().trim();
    String morphedBirth = details.locator("dt:has-text('Birth Date') + dd").first().textContent().trim();
    System.out.println("🔹 Morphed Output: Name = " + morphedName + ", Type = " + morphedType + ", Birth Date = " + morphedBirth);

    // Relation Check
    boolean nameSame = seedName.equals(morphedName);
    boolean birthSame = seedBirth.equals(morphedBirth);
    boolean typeChanged = morphedType.equals(newType);

    System.out.println("🔹 Relation Check: Name and Birth Date unchanged, Type updated to " + newType);
    assertTrue(nameSame && birthSame && typeChanged, "MR3 Failed: Inconsistent pet details after type update.");
    System.out.println("✅ MR3 Passed: Pet details consistent with expected transformation.");
}


 /**
 * 🔹 MR4: Pet List Update Consistency
 * 🔸 Seed Input: Create a new owner (TestOwner MR4) with no pets.
 * 🔸 Morphed Input: Add a new pet (Charlie) to the same owner.
 * 🔸 Seed Output: Fetch the initial pet list (likely empty).
 * 🔸 Morphed Output: Fetch the updated pet list (should include Charlie).
 * 🔸 Relation Check: morphedPets == seedPets + newPetName
 * The test passes if the pet list is updated consistently with the new pet added.
 */
@Test
void testMR4_PetListUpdate() {
    String newPetName = "Charlie", newBirth = "2024-06-01", newType = "dog";

    // Seed Input: Create a new owner
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", "TestOwner");
    page.fill("[name='lastName']", "MR4");
    page.fill("[name='address']", "123 Main St");
    page.fill("[name='city']", "TestCity");
    page.fill("[name='telephone']", "1234567890");
    page.click("button[type='submit']");
    String ownerId = page.url().split("/")[page.url().split("/").length - 1];
    System.out.println("🔹 Seed Input: Created owner with ID " + ownerId);

    // Seed Output: Capture initial pet list for the new owner
    page.navigate(BASE_URL + "/owners/" + ownerId);
    Locator petTable = page.locator("h2:has-text('Pets and Visits') + table");
    List<String> seedPets = new ArrayList<>();
    if (petTable.count() > 0) {
        seedPets = petTable.locator("tr td dl dt:has-text('Name') + dd").allTextContents();
    }
    System.out.println("🔹 Seed Output: Initial pet list = " + seedPets);

    // Morphed Input: Add a new pet for the same owner
    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/new");
    page.fill("[name='name']", newPetName);
    page.fill("[name='birthDate']", newBirth);
    page.selectOption("[name='type']", newType);
    page.click("button[type='submit']");
    System.out.println("🔹 Morphed Input: Added pet with name " + newPetName);

    // Morphed Output: Capture updated pet list
    page.navigate(BASE_URL + "/owners/" + ownerId);
    List<String> morphedPets = petTable.locator("tr td dl dt:has-text('Name') + dd").allTextContents();
    System.out.println("🔹 Morphed Output: Updated pet list = " + morphedPets);

    // Expected: seedPets plus newPetName
    List<String> expectedPets = new ArrayList<>(seedPets);
    expectedPets.add(newPetName);

    // Relation Check: morphedPets == seedPets + newPetName
    System.out.println("🔹 Relation Check: morphedPets == seedPets + newPetName");
    assertEquals(expectedPets, morphedPets, "MR4 Failed: Pet list not updated as expected.");
    System.out.println("✅ MR4 Passed: Pet list updated correctly with new pet.");
}
@Test
void testMR5_PetBirthDateReversibility() {
    // MR5: Pet Birth Date Reversibility
    // Seed Input: Create owner and pet, get original birth date
    // Transformation: Change birth date, then revert
    // Seed Output: Original birth date
    // Morphed Output: Birth date after revert
    // Relation: Birth date after revert equals original

    String newOwnerFirstName = "MR5Owner";
    String newOwnerLastName = "Test";
    String petName = "BuddyMR5";
    String petType = "dog";
    String originalBirthDate = "2015-09-07";
    String newBirthDate = "2018-05-15";

    // Create new owner
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", newOwnerFirstName);
    page.fill("[name='lastName']", newOwnerLastName);
    page.fill("[name='address']", "123 Main St");
    page.fill("[name='city']", "TestCity");
    page.fill("[name='telephone']", "1234567890");
    page.click("button[type='submit']");
    String ownerId = page.url().split("/")[page.url().split("/").length - 1];
    System.out.println("🔹 Seed Input: Created owner with ID " + ownerId);

    // Add pet
    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/new");
    page.fill("[name='name']", petName);
    page.fill("[name='birthDate']", originalBirthDate);
    page.selectOption("[name='type']", petType);
    page.click("button[type='submit']");
    System.out.println("🔹 Seed Input: Created pet " + petName + " with birth date " + originalBirthDate);

    // Extract petId from the pet's edit link
    page.navigate(BASE_URL + "/owners/" + ownerId);
    Locator editLink = page.locator("h2:has-text('Pets and Visits') + table")
                           .locator("a:has-text('Edit')").first();
    String editHref = editLink.getAttribute("href"); // e.g., /owners/{ownerId}/pets/{petId}/edit
    String[] parts = editHref.split("/");
    String petId = parts[parts.length - 2]; // second last part should be petId
    System.out.println("🔹 Extracted pet ID: " + petId);

    // Seed Output: Get original birth date
    Locator details = page.locator("h2:has-text('Pets and Visits') + table").locator("tr td dl");
    String seedDate = details.locator("dt:has-text('Birth Date') + dd").first().textContent().trim();
    System.out.println("🔹 Seed Output: Original birth date = " + seedDate);

    // Morphed Input: Update birth date to new value
    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/" + petId + "/edit");
    page.fill("[name='birthDate']", newBirthDate);
    page.click("button[type='submit']");
    System.out.println("🔹 Morphed Input: Updated birth date to " + newBirthDate);

    // Morphed Output: Get updated birth date
    page.navigate(BASE_URL + "/owners/" + ownerId);
    String morphedDate = details.locator("dt:has-text('Birth Date') + dd").first().textContent().trim();
    System.out.println("🔹 Morphed Output: Updated birth date = " + morphedDate);
    assertEquals(newBirthDate, morphedDate, "MR5 Failed: Birth date not updated to new value.");

    // Revert: Change birth date back to original
    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/" + petId + "/edit");
    page.fill("[name='birthDate']", seedDate);
    page.click("button[type='submit']");
    System.out.println("🔹 Reverted Input: Birth date reverted to " + seedDate);

    // Final Output: Get final birth date after revert
    page.navigate(BASE_URL + "/owners/" + ownerId);
    String finalDate = details.locator("dt:has-text('Birth Date') + dd").first().textContent().trim();
    System.out.println("🔹 Final Output: Birth date after revert = " + finalDate);

    // Check metamorphic relation
    assertEquals(seedDate, finalDate, "MR5 Failed: Birth date did not revert to original value.");
    System.out.println("✅ MR5 Passed: Birth date reverted correctly to original.");
}

@Test
void testMR6_FindOwnerPartialName() {
    // MR6: Find Owner by Partial Name
    // Seed Input: Search with empty last name (returns all owners)
    // Morphed Input: Search with partial last name (e.g., "D")
    // Seed Output: List of all owner names
    // Morphed Output: List of owner names with last name containing "D"
    // Relation: Morphed output ⊆ Seed output

    String seedInput = "";  // Empty input to fetch all owners
    String morphedInput = "D";  // Partial name input

    // Seed Input: Search with empty last name
    page.navigate(BASE_URL + "/owners/find");
    page.fill("[name='lastName']", seedInput);
    page.click("button[type='submit']");
    List<String> seedResults = page.locator("table tbody tr td a").allTextContents();
    System.out.println("🔹 Seed Input: Search with empty last name ('" + seedInput + "')");
    System.out.println("🔹 Seed Output: " + seedResults);

    // Morphed Input: Search with partial last name "D"
    page.navigate(BASE_URL + "/owners/find");
    page.fill("[name='lastName']", morphedInput);
    page.click("button[type='submit']");
    List<String> morphedResults = page.locator("table tbody tr td a").allTextContents();
    System.out.println("🔹 Morphed Input: Search with partial last name ('" + morphedInput + "')");
    System.out.println("🔹 Morphed Output: " + morphedResults);

    // Relation Check: morphedResults ⊆ seedResults
    boolean relationHolds = seedResults.containsAll(morphedResults);
    System.out.println("🔹 Relation Check: morphedResults ⊆ seedResults");

    assertTrue(relationHolds, "MR6 Failed: Partial search results are not a subset of all owners.");
    System.out.println("✅ MR6 Passed: Partial search results are a subset of all owners.");
}
@Test
void testMR7_VisitListAdditivity_MT() {
    String BASE_URL = "http://localhost:9090";
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", "JohnMR7");
    page.fill("[name='lastName']", "DoeMR7");
    page.fill("[name='address']", "123 Test Street");
    page.fill("[name='city']", "TestCity");
    page.fill("[name='telephone']", "9876543210");
    page.click("button[type='submit']");
    String ownerId = page.url().split("/")[page.url().split("/").length - 1];
    System.out.println("🔹 Created owner with ID: " + ownerId);

    // Add new pet and capture pet ID dynamically
    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/new");
    page.fill("[name='name']", "BuddyMR7");
    page.fill("[name='birthDate']", "2015-09-07");
    page.selectOption("[name='type']", "dog");
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);
    page.navigate(BASE_URL + "/owners/" + ownerId); // Go to owner page

    // Locate the pet row dynamically and extract Pet ID
    Locator petRow = page.locator("table tbody tr").filter(new Locator.FilterOptions().setHasText("BuddyMR7")).first();
    String addVisitHref = petRow.locator("a:has-text('Add Visit')").getAttribute("href");
    String[] parts = addVisitHref.split("/");
    String petId = parts[parts.length - 3]; // e.g., /owners/{ownerId}/pets/{petId}/visits/new
    System.out.println("🔹 Located BuddyMR7 with Pet ID: " + petId);

    // 🌿 Add initial visit (Seed Input)
    String addVisitUrl = BASE_URL + "/owners/" + ownerId + "/pets/" + petId + "/visits/new";
    System.out.println("🔹 Seed Input: Navigating to Add Visit page: " + addVisitUrl);
    page.navigate(addVisitUrl);
    String seedVisitDesc = "Initial Visit for MR7";
    page.fill("[name='description']", seedVisitDesc);
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);
    page.waitForTimeout(2000);
    System.out.println("🔹 Seed Input: Added initial visit with description '" + seedVisitDesc + "'");

    // 🌿 Capture Seed Output
    page.navigate(BASE_URL + "/owners/" + ownerId);
    petRow = page.locator("table tbody tr").filter(new Locator.FilterOptions().setHasText("BuddyMR7")).first();
    Locator visitDescriptionsLocator = petRow.locator("td:nth-child(2) table.table-condensed tr td:nth-child(2)");
    List<String> seedVisits = visitDescriptionsLocator.allTextContents();
    System.out.println("🔹 Seed Output: Visit descriptions = " + seedVisits);

    // 🌿 Add morphed visit (Morphed Input)
    page.navigate(addVisitUrl); // Reuse correct dynamic URL
    String morphedVisitDesc = "New Visit for MR7";
    page.fill("[name='description']", morphedVisitDesc);
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);
    page.waitForTimeout(2000);
    System.out.println("🔹 Morphed Input: Added new visit with description '" + morphedVisitDesc + "'");

    // 🌿 Capture Morphed Output
    page.navigate(BASE_URL + "/owners/" + ownerId);
    petRow = page.locator("table tbody tr").filter(new Locator.FilterOptions().setHasText("BuddyMR7")).first();
    visitDescriptionsLocator = petRow.locator("td:nth-child(2) table.table-condensed tr td:nth-child(2)");
    List<String> morphedVisits = visitDescriptionsLocator.allTextContents();
    System.out.println("🔹 Morphed Output: Visit descriptions = " + morphedVisits);

    // 🌿 Relation Check
    List<String> expectedVisits = new ArrayList<>(seedVisits);
    expectedVisits.add(morphedVisitDesc);
    Collections.sort(expectedVisits, Collections.reverseOrder());
    Collections.sort(morphedVisits, Collections.reverseOrder());
    assertEquals(expectedVisits, morphedVisits, "MR7 Failed: Visit list did not update as expected.");
    System.out.println("✅ MR7 Passed: Visit list updated correctly.");
}

    /**
     * MR8: Visit Addition Detection (Fault Detection)
     * - Seed Input: Count initial visits.
     * - Transformation: Add new visit.
     * - Morphed Output: Count updated visits.
     * - Relation: Count increased by 1.
     */
    @Test
    void testMR8_VisitAdditionDetection() {
        String ownerId = "1", petId = "1", newDate = "2025-06-02", newDesc = "Add Test Visit";
        page.navigate(BASE_URL + "/owners/" + ownerId);
        Locator visits = page.locator("h2:has-text('Pets and Visits') + table").locator("tr td table.table-condensed");
        int seedCount = visits.locator("tr td:nth-child(2)").allTextContents().size();

        page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/" + petId + "/visits/new");
        page.fill("[name='date']", newDate); page.fill("[name='description']", newDesc); page.click("button[type='submit']");

        page.navigate(BASE_URL + "/owners/" + ownerId);
        int morphedCount = visits.locator("tr td:nth-child(2)").allTextContents().size();
        assertEquals(seedCount + 1, morphedCount, "MR8 Failed: Visit count did not increase by 1.");
        System.out.println("✅ MR8 Passed: Visit count increased correctly.");
    }
}

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
        // Seed Input: Add first owner
        page.navigate(BASE_URL + "/owners/new");
        page.fill("[name='firstName']", "John");
        page.fill("[name='lastName']", "Doe");
        page.fill("[name='address']", "123 St");
        page.fill("[name='city']", "NY");
        page.fill("[name='telephone']", "123456");
        page.click("button[type='submit']");
        System.out.println("🔹 Seed Input: First owner added.");
// Seed Output: Count owners after first addition
        page.navigate(BASE_URL + "/owners/find");
        page.fill("[name='lastName']", "");
        page.click("button[type='submit']");
        int seedCount = page.locator("table tbody tr").count();
        System.out.println("🔹 Seed Output: Owner count after first addition = " + seedCount);
// Morphed Input: Add second owner
        page.navigate(BASE_URL + "/owners/new");
        page.fill("[name='firstName']", "Alice");
        page.fill("[name='lastName']", "Smith");
        page.fill("[name='address']", "456 St");
        page.fill("[name='city']", "LA");
        page.fill("[name='telephone']", "789456");
        page.click("button[type='submit']");
        System.out.println("🔹 Morphed Input: Second owner added.");
// Morphed Output: Count owners after second addition
        page.navigate(BASE_URL + "/owners/find");
        page.fill("[name='lastName']", "");
        page.click("button[type='submit']");
        int morphedCount = page.locator("table tbody tr").count();
        System.out.println("🔹 Morphed Output: Owner count after second addition = " + morphedCount);
// Relation Check: Count should increase by 1
        System.out.println("🔹 Relation Check: morphedCount == seedCount + 1");
        assertEquals(seedCount + 1, morphedCount, "MR1 Failed: Owner count should increase by exactly 1.");
        System.out.println("✅ MR1 Passed: Owner count increased as expected.");
    }


/**
 * MR2: City Update Consistency
 * - Seed Input: Create an owner with initial data including city.
 * - Input Transformation: Update only the city field to a new value.
 * - Morphed Input: Owner data with updated city.
 * - Seed Output: Initial owner data with original city.
 * - Morphed Output: Updated owner data with new city; other fields unchanged.
 * - Relation: Only city is updated; other fields remain stable.
 */
@Test
void testMR2_CityUpdateConsistency() {
    // Seed Input values
    String seedFirstName = "Test";
    String seedLastName = "Owner";
    String seedAddress = "123 Main St";
    String seedCity = "Turku";
    String seedTelephone = "1234567890";
    String morphedCity = "Helsinki";

    // 🔹 Step 1: Seed Input – Create a new owner with controlled initial data
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", seedFirstName);
    page.fill("[name='lastName']", seedLastName);
    page.fill("[name='address']", seedAddress);
    page.fill("[name='city']", seedCity);
    page.fill("[name='telephone']", seedTelephone);
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);

    // 🔹 Step 2: Extract dynamic owner ID from URL
    String[] urlParts = page.url().split("/");
    String ownerId = urlParts[urlParts.length - 1];
    System.out.println("🔹 Seed Input: Created owner with city = " + seedCity + ", Owner ID = " + ownerId);

    // 🔹 Step 3: Seed Output – Capture initial data
    page.navigate(BASE_URL + "/owners/" + ownerId);
    String seedOutputCity = page.textContent("table th:has-text('City') + td").trim();
    String seedOutputName = page.textContent("table th:has-text('Name') + td").trim();
    String seedOutputAddress = page.textContent("table th:has-text('Address') + td").trim();
    String seedOutputTelephone = page.textContent("table th:has-text('Telephone') + td").trim();

    System.out.println("🔹 Seed Output: City = " + seedOutputCity + ", Name = " + seedOutputName);

    // 🔹 Step 4: Input Transformation – Update the city to a new value
    page.navigate(BASE_URL + "/owners/" + ownerId + "/edit");
    page.fill("[name='city']", morphedCity);
    page.click("button[type='submit']");
    System.out.println("🔹 Morphed Input: Updated city to = " + morphedCity);

    // 🔹 Step 5: Morphed Output – Capture updated data
    page.navigate(BASE_URL + "/owners/" + ownerId);
    String morphedOutputCity = page.textContent("table th:has-text('City') + td").trim();
    String morphedOutputName = page.textContent("table th:has-text('Name') + td").trim();
    String morphedOutputAddress = page.textContent("table th:has-text('Address') + td").trim();
    String morphedOutputTelephone = page.textContent("table th:has-text('Telephone') + td").trim();

    System.out.println("🔹 Morphed Output: City = " + morphedOutputCity + ", Name = " + morphedOutputName);

    // 🔹 Step 6: Relation Check – Only city updated; other fields unchanged
    assertEquals(morphedCity, morphedOutputCity, "MR2 Failed: City was not updated correctly.");
    assertEquals(seedOutputName, morphedOutputName, "MR2 Failed: Name changed unexpectedly.");
    assertEquals(seedOutputAddress, morphedOutputAddress, "MR2 Failed: Address changed unexpectedly.");
    assertEquals(seedOutputTelephone, morphedOutputTelephone, "MR2 Failed: Telephone changed unexpectedly.");

    System.out.println("✅ MR2 Passed: Only city updated, other fields remain stable.");
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
/**
 * MR5: Pet Birth Date Update Consistency
 * - Seed Input: Create a pet with an initial birth date.
 * - Input Transformation: Update only the birth date to a new value.
 * - Morphed Input: Pet with updated birth date.
 * - Seed Output: Original pet details with initial birth date.
 * - Morphed Output: Pet details with updated birth date; other fields unchanged.
 * - Relation: Only birth date is updated; other fields remain stable.
 */
@Test
void testMR5_PetBirthDateUpdateConsistency() {
    String ownerFirstName = "MR5Owner";
    String ownerLastName = "Test";
    String petName = "BuddyMR5";
    String petType = "dog";
    String originalBirthDate = "2015-09-07";
    String newBirthDate = "2018-05-15";
    String ownerAddress = "123 Main St";
    String ownerCity = "TestCity";
    String ownerTelephone = "1234567890";

    // 🔹 Seed Input: Create a new owner
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", ownerFirstName);
    page.fill("[name='lastName']", ownerLastName);
    page.fill("[name='address']", ownerAddress);
    page.fill("[name='city']", ownerCity);
    page.fill("[name='telephone']", ownerTelephone);
    page.click("button[type='submit']");
    String ownerId = page.url().split("/")[page.url().split("/").length - 1];
    System.out.println("🔹 Seed Input: Created owner with ID " + ownerId);

    // 🔹 Seed Input: Add a new pet with initial birth date
    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/new");
    page.fill("[name='name']", petName);
    page.fill("[name='birthDate']", originalBirthDate);
    page.selectOption("[name='type']", petType);
    page.click("button[type='submit']");
    System.out.println("🔹 Seed Input: Created pet " + petName + " with birth date " + originalBirthDate);

    // 🔹 Extract petId from edit link
    page.navigate(BASE_URL + "/owners/" + ownerId);
    Locator editLink = page.locator("h2:has-text('Pets and Visits') + table")
                           .locator("a:has-text('Edit')").first();
    String editHref = editLink.getAttribute("href");
    String[] parts = editHref.split("/");
    String petId = parts[parts.length - 2];
    System.out.println("🔹 Extracted pet ID: " + petId);

    // 🔹 Seed Output: Capture original pet details
    Locator details = page.locator("h2:has-text('Pets and Visits') + table").locator("tr td dl");
    String seedBirthDate = details.locator("dt:has-text('Birth Date') + dd").first().textContent().trim();
    String seedName = details.locator("dt:has-text('Name') + dd").first().textContent().trim();
    String seedType = details.locator("dt:has-text('Type') + dd").first().textContent().trim();
    System.out.println("🔹 Seed Output: Birth Date = " + seedBirthDate + ", Name = " + seedName + ", Type = " + seedType);

    // 🔹 Input Transformation & Morphed Input: Update birth date to new value
    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/" + petId + "/edit");
    page.fill("[name='birthDate']", newBirthDate);
    page.click("button[type='submit']");
    System.out.println("🔹 Morphed Input: Updated birth date to " + newBirthDate);

    // 🔹 Morphed Output: Capture updated details
    page.navigate(BASE_URL + "/owners/" + ownerId);
    String morphedBirthDate = details.locator("dt:has-text('Birth Date') + dd").first().textContent().trim();
    String morphedName = details.locator("dt:has-text('Name') + dd").first().textContent().trim();
    String morphedType = details.locator("dt:has-text('Type') + dd").first().textContent().trim();
    System.out.println("🔹 Morphed Output: Birth Date = " + morphedBirthDate + ", Name = " + morphedName + ", Type = " + morphedType);

    // 🔹 Relation Check: Only birth date updated, other fields unchanged
    assertEquals(newBirthDate, morphedBirthDate, "MR5 Failed: Birth date was not updated correctly.");
    assertEquals(seedName, morphedName, "MR5 Failed: Name changed unexpectedly.");
    assertEquals(seedType, morphedType, "MR5 Failed: Type changed unexpectedly.");

    System.out.println("✅ MR5 Passed: Only birth date was updated; other fields remained stable.");
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
/**
 * MR7: Visit List Additivity (Visit List Consistency)
 * - Seed Input: Create a new owner and pet, and add an initial visit.
 * - Transformation: Add a new visit to the same pet.
 * - Morphed Input: Provide the new visit details (description and date).
 * - Seed Output: Fetch the list of visit descriptions after the initial visit.
 * - Morphed Output: Fetch the list of visit descriptions after adding the new visit.
 * - Relation: The morphed visit list should include the seed list plus the new visit,
 *   verifying that adding a new visit preserves existing visits.
 * - Purpose: Validate that the system correctly appends new visit records without 
 *   overwriting or missing previous data (additivity and consistency of visit records).
 */
@Test
void testMR7_VisitListAdditivity_MT() {
    String BASE_URL = "http://localhost:9090";

    // 🌱 Seed Input: Create new owner and pet (initial system state)
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", "JohnMR7");
    page.fill("[name='lastName']", "DoeMR7");
    page.fill("[name='address']", "123 Test Street");
    page.fill("[name='city']", "TestCity");
    page.fill("[name='telephone']", "9876543210");
    page.click("button[type='submit']");
    String ownerId = page.url().split("/")[page.url().split("/").length - 1];
    System.out.println("🔹 Created owner with ID: " + ownerId);

    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/new");
    page.fill("[name='name']", "BuddyMR7");
    page.fill("[name='birthDate']", "2015-09-07");
    page.selectOption("[name='type']", "dog");
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);
    page.navigate(BASE_URL + "/owners/" + ownerId);

    // 🌿 Extract Pet ID dynamically from "Add Visit" link
    Locator petRow = page.locator("table tbody tr").filter(new Locator.FilterOptions().setHasText("BuddyMR7")).first();
    String addVisitHref = petRow.locator("a:has-text('Add Visit')").getAttribute("href");
    String[] parts = addVisitHref.split("/");
    String petId = parts[parts.length - 3];
    System.out.println("🔹 Located BuddyMR7 with Pet ID: " + petId);

    // 🌿 Transformation: Add initial visit (seed state modification)
    String addVisitUrl = BASE_URL + "/owners/" + ownerId + "/pets/" + petId + "/visits/new";
    System.out.println("🔹 Seed Input: Navigating to Add Visit page: " + addVisitUrl);
    String seedVisitDesc = "Initial Visit for MR7";
    page.navigate(addVisitUrl);
    page.fill("[name='description']", seedVisitDesc);
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);
    page.waitForTimeout(2000);
    System.out.println("🔹 Seed Input: Added initial visit with description '" + seedVisitDesc + "'");

    // 🌿 Seed Output: Capture visit list after initial transformation
    page.navigate(BASE_URL + "/owners/" + ownerId);
    petRow = page.locator("table tbody tr").filter(new Locator.FilterOptions().setHasText("BuddyMR7")).first();
    Locator visitDescriptionsLocator = petRow.locator("td:nth-child(2) table.table-condensed tr td:nth-child(2)");
    List<String> seedVisits = visitDescriptionsLocator.allTextContents();
    System.out.println("🔹 Seed Output: Visit descriptions = " + seedVisits);

    // 🌿 Morphed Input: Add a second visit (transformation - new input)
    page.navigate(addVisitUrl); // Use correct dynamic URL for adding visit
    String morphedVisitDesc = "New Visit for MR7";
    page.fill("[name='description']", morphedVisitDesc);
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);
    page.waitForTimeout(2000);
    System.out.println("🔹 Morphed Input: Added new visit with description '" + morphedVisitDesc + "'");

    // 🌿 Morphed Output: Capture updated visit list after transformation
    page.navigate(BASE_URL + "/owners/" + ownerId);
    petRow = page.locator("table tbody tr").filter(new Locator.FilterOptions().setHasText("BuddyMR7")).first();
    visitDescriptionsLocator = petRow.locator("td:nth-child(2) table.table-condensed tr td:nth-child(2)");
    List<String> morphedVisits = visitDescriptionsLocator.allTextContents();
    System.out.println("🔹 Morphed Output: Visit descriptions = " + morphedVisits);

    // 🔎 Relation: Morphed Output = Seed Output + New Visit
    // The relation checks that adding a new visit appends it to the list without removing previous data
    List<String> expectedVisits = new ArrayList<>(seedVisits);
    expectedVisits.add(morphedVisitDesc);
    Collections.sort(expectedVisits, Collections.reverseOrder());
    Collections.sort(morphedVisits, Collections.reverseOrder());
    assertEquals(expectedVisits, morphedVisits, "MR7 Failed: Visit list did not update as expected.");
    System.out.println("✅ MR7 Passed: Visit list updated correctly.");
}

/**
 * MR8: Visit Addition Consistency
 * - Seed Input: Create owner and pet, and count initial visits.
 * - Input Transformation: Add a new visit to the same pet.
 * - Morphed Input: Pet with the additional visit added.
 * - Seed Output: Initial count of visits for the pet.
 * - Morphed Output: Updated count of visits after new visit.
 * - Relation: Visit count should increase by exactly 1.
 */
@Test
void testMR8_VisitAdditionConsistency() {
    String ownerFirstName = "JohnMR8";
    String ownerLastName = "DoeMR8";
    String petName = "BuddyMR8";
    String petType = "dog";
    String ownerAddress = "456 Test Ave";
    String ownerCity = "TestCity";
    String ownerTelephone = "1234567890";
    String newVisitDate = "2025-06-02";
    String newVisitDesc = "Add Test Visit MR8";

    // 🔹 Step 1: Seed Input – Create owner and pet
    page.navigate(BASE_URL + "/owners/new");
    page.fill("[name='firstName']", ownerFirstName);
    page.fill("[name='lastName']", ownerLastName);
    page.fill("[name='address']", ownerAddress);
    page.fill("[name='city']", ownerCity);
    page.fill("[name='telephone']", ownerTelephone);
    page.click("button[type='submit']");
    String ownerId = page.url().split("/")[page.url().split("/").length - 1];
    System.out.println("🔹 Seed Input: Created owner with ID " + ownerId);

    page.navigate(BASE_URL + "/owners/" + ownerId + "/pets/new");
    page.fill("[name='name']", petName);
    page.fill("[name='birthDate']", "2018-01-01");
    page.selectOption("[name='type']", petType);
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);
    page.navigate(BASE_URL + "/owners/" + ownerId);

    // 🔹 Step 2: Extract petId for locating the pet's visit list
    Locator petRow = page.locator("table tbody tr").filter(new Locator.FilterOptions().setHasText(petName)).first();
    String addVisitHref = petRow.locator("a:has-text('Add Visit')").getAttribute("href");
    String[] parts = addVisitHref.split("/");
    String petId = parts[parts.length - 3];
    System.out.println("🔹 Located " + petName + " with Pet ID: " + petId);

    // 🔹 Step 3: Seed Output – Count initial number of visits
    Locator visitsLocator = petRow.locator("td:nth-child(2) table.table-condensed tr td:nth-child(2)");
    int seedVisitCount = visitsLocator.count();
    System.out.println("🔹 Seed Output: Initial visit count = " + seedVisitCount);

    // 🔹 Step 4: Input Transformation – Prepare to add a new visit
    String addVisitUrl = BASE_URL + "/owners/" + ownerId + "/pets/" + petId + "/visits/new";

    // 🔹 Step 5: Morphed Input – Add a new visit (transformation)
    page.navigate(addVisitUrl);
    page.fill("[name='date']", newVisitDate);
    page.fill("[name='description']", newVisitDesc);
    page.click("button[type='submit']");
    page.waitForLoadState(LoadState.NETWORKIDLE);
    page.waitForTimeout(2000);  // Allow for backend update
    System.out.println("🔹 Morphed Input: Added visit with description '" + newVisitDesc + "'");

    // 🔹 Step 6: Morphed Output – Count updated number of visits
    page.navigate(BASE_URL + "/owners/" + ownerId);
    petRow = page.locator("table tbody tr").filter(new Locator.FilterOptions().setHasText(petName)).first();
    visitsLocator = petRow.locator("td:nth-child(2) table.table-condensed tr td:nth-child(2)");
    int morphedVisitCount = visitsLocator.count();
    System.out.println("🔹 Morphed Output: Updated visit count = " + morphedVisitCount);

    // 🔹 Step 7: Relation Check – Visit count should increase by 1
    assertEquals(seedVisitCount + 1, morphedVisitCount, "MR8 Failed: Visit count did not increase by 1.");
    System.out.println("✅ MR8 Passed: Visit count increased correctly.");
}

}
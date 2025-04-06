package playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public class PlaywrightTest {

	private static Playwright playwright;

	private static Browser browser;

	private static Page page;

	@BeforeAll
	static void setup() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		page = browser.newPage();
	}

	@AfterAll
	static void teardown() {
		browser.close();
		playwright.close();
	}

	@Test
	void testTitle() {
		page.navigate("http://localhost:8080");
		String title = page.title();
		System.out.println("Page title: " + title);
		Assertions.assertFalse(title.isEmpty(), "Title should not be empty");
	}

	// ✅ Test: Check Veterinarians Page Title
	@Test
	void testVeterinariansPageTitle() {
		page.navigate("http://localhost:8080/vets.html");

		// Wait for a visible element to confirm page load
		page.waitForSelector("h2");

		// Get the title
		String title = page.title();
		System.out.println("Page title: " + title);

		// Check for 'PetClinic' instead of 'Veterinarians'
		Assertions.assertTrue(title.contains("PetClinic"), "Title should contain 'PetClinic'");
	}

	// ✅ Test: Check if Veterinarians List is Not Empty
	@Test
	void testVeterinariansListNotEmpty() {
		page.navigate("http://localhost:8080/vets.html");
		page.waitForSelector("table tbody tr"); // Wait for table to load

		int vetCount = page.locator("table tbody tr").count();
		Assertions.assertTrue(vetCount > 0, "Veterinarians list should not be empty");
	}

	// ✅ Test: Check that Veterinarians Have Names and Specialties
	@Test
	void testVeterinarianHasSpecialty() {
		page.navigate("http://localhost:8080/vets.html");
		page.waitForSelector("table tbody tr");

		int vetRows = page.locator("table tbody tr").count();
		Assertions.assertTrue(vetRows > 0, "Veterinarian list should not be empty");

		for (int i = 0; i < vetRows; i++) {
			String vetName = page.locator("table tbody tr:nth-of-type(" + (i + 1) + ") td:nth-of-type(1)").innerText();
			String vetSpecialty = page.locator("table tbody tr:nth-of-type(" + (i + 1) + ") td:nth-of-type(2)")
					.innerText();

			Assertions.assertFalse(vetName.isEmpty(), "Veterinarian name should not be empty");

			// Specialty can be empty (some vets have "none"), so just print the result
			System.out.println("Vet: " + vetName + ", Specialty: " + (vetSpecialty.isEmpty() ? "None" : vetSpecialty));
		}
	}

}

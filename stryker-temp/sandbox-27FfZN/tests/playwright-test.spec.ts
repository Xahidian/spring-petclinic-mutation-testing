// @ts-nocheck
function stryNS_9fa48() {
  var g = typeof globalThis === 'object' && globalThis && globalThis.Math === Math && globalThis || new Function("return this")();
  var ns = g.__stryker__ || (g.__stryker__ = {});
  if (ns.activeMutant === undefined && g.process && g.process.env && g.process.env.__STRYKER_ACTIVE_MUTANT__) {
    ns.activeMutant = g.process.env.__STRYKER_ACTIVE_MUTANT__;
  }
  function retrieveNS() {
    return ns;
  }
  stryNS_9fa48 = retrieveNS;
  return retrieveNS();
}
stryNS_9fa48();
function stryCov_9fa48() {
  var ns = stryNS_9fa48();
  var cov = ns.mutantCoverage || (ns.mutantCoverage = {
    static: {},
    perTest: {}
  });
  function cover() {
    var c = cov.static;
    if (ns.currentTestId) {
      c = cov.perTest[ns.currentTestId] = cov.perTest[ns.currentTestId] || {};
    }
    var a = arguments;
    for (var i = 0; i < a.length; i++) {
      c[a[i]] = (c[a[i]] || 0) + 1;
    }
  }
  stryCov_9fa48 = cover;
  cover.apply(null, arguments);
}
function stryMutAct_9fa48(id) {
  var ns = stryNS_9fa48();
  function isActive(id) {
    if (ns.activeMutant === id) {
      if (ns.hitCount !== void 0 && ++ns.hitCount > ns.hitLimit) {
        throw new Error('Stryker: Hit count limit reached (' + ns.hitCount + ')');
      }
      return true;
    }
    return false;
  }
  stryMutAct_9fa48 = isActive;
  return isActive(id);
}
import { test, expect } from '@playwright/test';

// ✅ Base URL for all tests
const BASE_URL = stryMutAct_9fa48("0") ? "" : (stryCov_9fa48("0"), 'http://localhost:8080');

// ✅ Global storage for dynamically retrieved Owner ID
let dynamicOwnerId: string | undefined;

/**
 * ✅ Utility wrapper to log failures caused by mutation testing.
 */
function runWithMutationCheck(testName: string, testFn: ({
  page
}: {
  page: any;
}) => Promise<void>) {
  if (stryMutAct_9fa48("1")) {
    {}
  } else {
    stryCov_9fa48("1");
    test(testName, async ({
      page
    }) => {
      if (stryMutAct_9fa48("2")) {
        {}
      } else {
        stryCov_9fa48("2");
        try {
          if (stryMutAct_9fa48("3")) {
            {}
          } else {
            stryCov_9fa48("3");
            await testFn(stryMutAct_9fa48("4") ? {} : (stryCov_9fa48("4"), {
              page
            }));
            console.log(stryMutAct_9fa48("5") ? `` : (stryCov_9fa48("5"), `✅ Test Passed: ${testName}`));
          }
        } catch (error) {
          if (stryMutAct_9fa48("6")) {
            {}
          } else {
            stryCov_9fa48("6");
            console.error(stryMutAct_9fa48("7") ? `` : (stryCov_9fa48("7"), `❌ MUTATION CAUSED FAILURE in: ${testName}`), error);
            throw error; // Ensures Playwright properly fails the test
          }
        }
      }
    });
  }
}

// ✅ Verify Application and Navigation Links
test.describe(stryMutAct_9fa48("8") ? "" : (stryCov_9fa48("8"), 'Verify Application and Navigation Links'), () => {
  if (stryMutAct_9fa48("9")) {
    {}
  } else {
    stryCov_9fa48("9");
    runWithMutationCheck(stryMutAct_9fa48("10") ? "" : (stryCov_9fa48("10"), 'Verify Spring Boot application is running'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("11")) {
        {}
      } else {
        stryCov_9fa48("11");
        await page.goto(BASE_URL);
        await expect(page).toHaveTitle(/PetClinic/);
        await expect(page.locator(stryMutAct_9fa48("12") ? "" : (stryCov_9fa48("12"), 'h2'))).toContainText(stryMutAct_9fa48("13") ? "" : (stryCov_9fa48("13"), 'Welcome'));
      }
    });
    runWithMutationCheck(stryMutAct_9fa48("14") ? "" : (stryCov_9fa48("14"), 'Find Owners page should be accessible'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("15")) {
        {}
      } else {
        stryCov_9fa48("15");
        await page.goto(stryMutAct_9fa48("16") ? `` : (stryCov_9fa48("16"), `${BASE_URL}/owners/find`));
        await expect(page).toHaveURL(/owners\/find/);
        await expect(page.locator(stryMutAct_9fa48("17") ? "" : (stryCov_9fa48("17"), 'h2'))).toContainText(stryMutAct_9fa48("18") ? "" : (stryCov_9fa48("18"), 'Find Owners'));
      }
    });
    runWithMutationCheck(stryMutAct_9fa48("19") ? "" : (stryCov_9fa48("19"), 'Veterinarians page should be accessible'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("20")) {
        {}
      } else {
        stryCov_9fa48("20");
        await page.goto(stryMutAct_9fa48("21") ? `` : (stryCov_9fa48("21"), `${BASE_URL}/vets.html`));
        await expect(page).toHaveURL(/vets.html/);
        await expect(page.locator(stryMutAct_9fa48("22") ? "" : (stryCov_9fa48("22"), 'h2'))).toContainText(stryMutAct_9fa48("23") ? "" : (stryCov_9fa48("23"), 'Veterinarians'));
      }
    });
    runWithMutationCheck(stryMutAct_9fa48("24") ? "" : (stryCov_9fa48("24"), 'Error page should be accessible'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("25")) {
        {}
      } else {
        stryCov_9fa48("25");
        await page.goto(stryMutAct_9fa48("26") ? `` : (stryCov_9fa48("26"), `${BASE_URL}/oups`));
        await expect(page).toHaveURL(/oups/);
        await expect(page.getByRole(stryMutAct_9fa48("27") ? "" : (stryCov_9fa48("27"), 'heading'), stryMutAct_9fa48("28") ? {} : (stryCov_9fa48("28"), {
          name: stryMutAct_9fa48("29") ? "" : (stryCov_9fa48("29"), 'Something happened...')
        }))).toBeVisible();
      }
    });
  }
});

// ✅ Owner Search & Button Verification
test.describe(stryMutAct_9fa48("30") ? "" : (stryCov_9fa48("30"), 'Owner Search & Button Verification'), () => {
  if (stryMutAct_9fa48("31")) {
    {}
  } else {
    stryCov_9fa48("31");
    runWithMutationCheck(stryMutAct_9fa48("32") ? "" : (stryCov_9fa48("32"), 'Verify "Add Owner" button and form submission'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("33")) {
        {}
      } else {
        stryCov_9fa48("33");
        await page.goto(stryMutAct_9fa48("34") ? `` : (stryCov_9fa48("34"), `${BASE_URL}/owners/find`));
        await page.click(stryMutAct_9fa48("35") ? "" : (stryCov_9fa48("35"), 'a:has-text("Add Owner")'));
        await expect(page).toHaveURL(stryMutAct_9fa48("36") ? `` : (stryCov_9fa48("36"), `${BASE_URL}/owners/new`));

        // ✅ Generate a unique owner name
        const randomId = Math.floor(stryMutAct_9fa48("37") ? Math.random() / 10 : (stryCov_9fa48("37"), Math.random() * 10));
        const firstName = stryMutAct_9fa48("38") ? `` : (stryCov_9fa48("38"), `John${randomId}`);
        const lastName = stryMutAct_9fa48("39") ? `` : (stryCov_9fa48("39"), `Doe${randomId}`);
        console.log(stryMutAct_9fa48("40") ? `` : (stryCov_9fa48("40"), `Creating owner: ${firstName} ${lastName}`));

        // ✅ Fill out form (Leaving out the first name to trigger validation)
        await page.fill(stryMutAct_9fa48("41") ? "" : (stryCov_9fa48("41"), 'input[name="lastName"]'), lastName);
        await page.fill(stryMutAct_9fa48("42") ? "" : (stryCov_9fa48("42"), 'input[name="address"]'), stryMutAct_9fa48("43") ? "" : (stryCov_9fa48("43"), '123 Main St'));
        await page.fill(stryMutAct_9fa48("44") ? "" : (stryCov_9fa48("44"), 'input[name="city"]'), stryMutAct_9fa48("45") ? "" : (stryCov_9fa48("45"), 'Turku'));
        await page.fill(stryMutAct_9fa48("46") ? "" : (stryCov_9fa48("46"), 'input[name="telephone"]'), stryMutAct_9fa48("47") ? "" : (stryCov_9fa48("47"), '0417238232'));

        // ✅ Attempt to submit form with a missing field
        await page.click(stryMutAct_9fa48("48") ? "" : (stryCov_9fa48("48"), 'button:has-text("Add Owner")'));

        // ✅ Check for validation message
        await expect(page.locator(stryMutAct_9fa48("49") ? "" : (stryCov_9fa48("49"), 'span.help-inline'))).toContainText(stryMutAct_9fa48("50") ? "" : (stryCov_9fa48("50"), 'must not be empty'));

        // ✅ Now, correctly fill the missing field and submit
        await page.fill(stryMutAct_9fa48("51") ? "" : (stryCov_9fa48("51"), 'input[name="firstName"]'), firstName);
        await page.click(stryMutAct_9fa48("52") ? "" : (stryCov_9fa48("52"), 'button:has-text("Add Owner")'));

        // ✅ Capture the new Owner ID dynamically
        await expect(page).toHaveURL(stryMutAct_9fa48("54") ? /owners\/\D+/ : stryMutAct_9fa48("53") ? /owners\/\d/ : (stryCov_9fa48("53", "54"), /owners\/\d+/));
        const newOwnerUrl = page.url();
        dynamicOwnerId = stryMutAct_9fa48("55") ? newOwnerUrl.match(/owners\/(\d+)/)[1] : (stryCov_9fa48("55"), newOwnerUrl.match(stryMutAct_9fa48("57") ? /owners\/(\D+)/ : stryMutAct_9fa48("56") ? /owners\/(\d)/ : (stryCov_9fa48("56", "57"), /owners\/(\d+)/))?.[1]);
        console.log(stryMutAct_9fa48("58") ? "" : (stryCov_9fa48("58"), "New Owner ID:"), dynamicOwnerId);
      }
    });
  }
});

// ✅ Owner Management
test.describe(stryMutAct_9fa48("59") ? "" : (stryCov_9fa48("59"), 'Owner Management'), () => {
  if (stryMutAct_9fa48("60")) {
    {}
  } else {
    stryCov_9fa48("60");
    runWithMutationCheck(stryMutAct_9fa48("61") ? "" : (stryCov_9fa48("61"), 'Edit Owner Details'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("62")) {
        {}
      } else {
        stryCov_9fa48("62");
        if (stryMutAct_9fa48("65") ? false : stryMutAct_9fa48("64") ? true : stryMutAct_9fa48("63") ? dynamicOwnerId : (stryCov_9fa48("63", "64", "65"), !dynamicOwnerId)) throw new Error(stryMutAct_9fa48("66") ? "" : (stryCov_9fa48("66"), 'Owner ID not found!'));
        await page.goto(stryMutAct_9fa48("67") ? `` : (stryCov_9fa48("67"), `${BASE_URL}/owners/${dynamicOwnerId}`));
        await page.click(stryMutAct_9fa48("68") ? "" : (stryCov_9fa48("68"), 'a:has-text("Edit Owner")'));
        await expect(page).toHaveURL(new RegExp(stryMutAct_9fa48("69") ? `` : (stryCov_9fa48("69"), `${BASE_URL}/owners/${dynamicOwnerId}/edit`)));
        await page.fill(stryMutAct_9fa48("70") ? "" : (stryCov_9fa48("70"), 'input[name="firstName"]'), stryMutAct_9fa48("71") ? "" : (stryCov_9fa48("71"), 'UpdatedJohn'));
        await page.fill(stryMutAct_9fa48("72") ? "" : (stryCov_9fa48("72"), 'input[name="lastName"]'), stryMutAct_9fa48("73") ? "" : (stryCov_9fa48("73"), 'UpdatedDoe'));
        await page.fill(stryMutAct_9fa48("74") ? "" : (stryCov_9fa48("74"), 'input[name="address"]'), stryMutAct_9fa48("75") ? "" : (stryCov_9fa48("75"), '456 Updated St'));
        await page.fill(stryMutAct_9fa48("76") ? "" : (stryCov_9fa48("76"), 'input[name="city"]'), stryMutAct_9fa48("77") ? "" : (stryCov_9fa48("77"), 'Helsinki'));
        await page.fill(stryMutAct_9fa48("78") ? "" : (stryCov_9fa48("78"), 'input[name="telephone"]'), stryMutAct_9fa48("79") ? "" : (stryCov_9fa48("79"), '0412345678'));
        await page.click(stryMutAct_9fa48("80") ? "" : (stryCov_9fa48("80"), 'button:has-text("Update Owner")'));

        // ✅ Verify owner details were updated
        await expect(page).toHaveURL(new RegExp(stryMutAct_9fa48("81") ? `` : (stryCov_9fa48("81"), `${BASE_URL}/owners/${dynamicOwnerId}`)));
        await expect(page.locator(stryMutAct_9fa48("82") ? "" : (stryCov_9fa48("82"), 'table.table-striped')).first()).toContainText(stryMutAct_9fa48("83") ? "" : (stryCov_9fa48("83"), 'UpdatedJohn UpdatedDoe'));
      }
    });
    runWithMutationCheck(stryMutAct_9fa48("84") ? "" : (stryCov_9fa48("84"), 'Add a New Pet for an Owner'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("85")) {
        {}
      } else {
        stryCov_9fa48("85");
        if (stryMutAct_9fa48("88") ? false : stryMutAct_9fa48("87") ? true : stryMutAct_9fa48("86") ? dynamicOwnerId : (stryCov_9fa48("86", "87", "88"), !dynamicOwnerId)) throw new Error(stryMutAct_9fa48("89") ? "" : (stryCov_9fa48("89"), 'Owner ID not found!'));
        await page.goto(stryMutAct_9fa48("90") ? `` : (stryCov_9fa48("90"), `${BASE_URL}/owners/${dynamicOwnerId}`));
        await page.click(stryMutAct_9fa48("91") ? "" : (stryCov_9fa48("91"), 'a:has-text("Add New Pet")'));
        await expect(page).toHaveURL(new RegExp(stryMutAct_9fa48("92") ? `` : (stryCov_9fa48("92"), `${BASE_URL}/owners/${dynamicOwnerId}/pets/new`)));

        // ✅ Generate unique pet name
        const randomPetName = stryMutAct_9fa48("93") ? `` : (stryCov_9fa48("93"), `Pet${Math.floor(stryMutAct_9fa48("94") ? Math.random() / 1000 : (stryCov_9fa48("94"), Math.random() * 1000))}`);
        await page.fill(stryMutAct_9fa48("95") ? "" : (stryCov_9fa48("95"), 'input[name="name"]'), randomPetName);
        await page.fill(stryMutAct_9fa48("96") ? "" : (stryCov_9fa48("96"), 'input[name="birthDate"]'), stryMutAct_9fa48("97") ? "" : (stryCov_9fa48("97"), '2023-06-15'));

        // ✅ Select random pet type
        const petTypes = stryMutAct_9fa48("98") ? [] : (stryCov_9fa48("98"), [stryMutAct_9fa48("99") ? "" : (stryCov_9fa48("99"), 'bird'), stryMutAct_9fa48("100") ? "" : (stryCov_9fa48("100"), 'cat'), stryMutAct_9fa48("101") ? "" : (stryCov_9fa48("101"), 'dog'), stryMutAct_9fa48("102") ? "" : (stryCov_9fa48("102"), 'hamster'), stryMutAct_9fa48("103") ? "" : (stryCov_9fa48("103"), 'lizard'), stryMutAct_9fa48("104") ? "" : (stryCov_9fa48("104"), 'snake')]);
        await page.selectOption(stryMutAct_9fa48("105") ? "" : (stryCov_9fa48("105"), 'select[name="type"]'), petTypes[Math.floor(stryMutAct_9fa48("106") ? Math.random() / petTypes.length : (stryCov_9fa48("106"), Math.random() * petTypes.length))]);

        // ✅ Click "Add Pet"
        await page.click(stryMutAct_9fa48("107") ? "" : (stryCov_9fa48("107"), 'button:has-text("Add Pet")'));

        // ✅ Verify pet appears under the owner
        await expect(page).toHaveURL(new RegExp(stryMutAct_9fa48("108") ? `` : (stryCov_9fa48("108"), `${BASE_URL}/owners/${dynamicOwnerId}`)));
        await expect(page.locator(stryMutAct_9fa48("109") ? "" : (stryCov_9fa48("109"), 'table.table-striped')).nth(1)).toContainText(randomPetName);
      }
    });
  }
});

// ✅ Veterinarians Table Fix
runWithMutationCheck(stryMutAct_9fa48("110") ? "" : (stryCov_9fa48("110"), 'Verify Veterinarians Table'), async ({
  page
}) => {
  if (stryMutAct_9fa48("111")) {
    {}
  } else {
    stryCov_9fa48("111");
    await page.goto(stryMutAct_9fa48("112") ? `` : (stryCov_9fa48("112"), `${BASE_URL}/vets.html`));
    const vetTable = page.locator(stryMutAct_9fa48("113") ? "" : (stryCov_9fa48("113"), 'table'));
    await expect(vetTable).toBeVisible();

    // ✅ Use toHaveText() to match exact content
    await expect(vetTable).toHaveText(new RegExp((stryMutAct_9fa48("114") ? "" : (stryCov_9fa48("114"), "Name\\s*Specialties\\s*James Carter\\s*none\\s*Helen Leary\\s*radiology\\s*Linda Douglas\\s*dentistry surgery\\s*")) + (stryMutAct_9fa48("115") ? "" : (stryCov_9fa48("115"), "Rafael Ortega\\s*surgery\\s*Henry Stevens\\s*radiology\\s*Sharon Jenkins\\s*none"))));
  }
});

// ✅ Verify Error Page Content
runWithMutationCheck(stryMutAct_9fa48("116") ? "" : (stryCov_9fa48("116"), 'Verify Error Page Content'), async ({
  page
}) => {
  if (stryMutAct_9fa48("117")) {
    {}
  } else {
    stryCov_9fa48("117");
    await page.goto(stryMutAct_9fa48("118") ? `` : (stryCov_9fa48("118"), `${BASE_URL}/oups`));
    await expect(page.locator(stryMutAct_9fa48("119") ? "" : (stryCov_9fa48("119"), 'h2'))).toContainText(stryMutAct_9fa48("120") ? "" : (stryCov_9fa48("120"), 'Something happened...'));
  }
});
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
        await page.goto(BASE_URL, stryMutAct_9fa48("12") ? {} : (stryCov_9fa48("12"), {
          waitUntil: stryMutAct_9fa48("13") ? "" : (stryCov_9fa48("13"), 'domcontentloaded')
        }));
        await expect(page).toHaveTitle(/PetClinic/);
        await page.waitForSelector(stryMutAct_9fa48("14") ? "" : (stryCov_9fa48("14"), 'h2'), stryMutAct_9fa48("15") ? {} : (stryCov_9fa48("15"), {
          timeout: 5000
        }));
        await expect(page.locator(stryMutAct_9fa48("16") ? "" : (stryCov_9fa48("16"), 'h2'))).toHaveText(stryMutAct_9fa48("17") ? "" : (stryCov_9fa48("17"), 'Welcome'));
      }
    });
    runWithMutationCheck(stryMutAct_9fa48("18") ? "" : (stryCov_9fa48("18"), 'Find Owners page should be accessible'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("19")) {
        {}
      } else {
        stryCov_9fa48("19");
        await page.goto(stryMutAct_9fa48("20") ? `` : (stryCov_9fa48("20"), `${BASE_URL}/owners/find`), stryMutAct_9fa48("21") ? {} : (stryCov_9fa48("21"), {
          waitUntil: stryMutAct_9fa48("22") ? "" : (stryCov_9fa48("22"), 'domcontentloaded')
        }));
        await expect(page).toHaveURL(/owners\/find/);
        await page.waitForSelector(stryMutAct_9fa48("23") ? "" : (stryCov_9fa48("23"), 'h2'), stryMutAct_9fa48("24") ? {} : (stryCov_9fa48("24"), {
          timeout: 5000
        }));
        await expect(page.locator(stryMutAct_9fa48("25") ? "" : (stryCov_9fa48("25"), 'h2'))).toHaveText(stryMutAct_9fa48("26") ? "" : (stryCov_9fa48("26"), 'Find Owners'));
      }
    });
    runWithMutationCheck(stryMutAct_9fa48("27") ? "" : (stryCov_9fa48("27"), 'Veterinarians page should be accessible'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("28")) {
        {}
      } else {
        stryCov_9fa48("28");
        await page.goto(stryMutAct_9fa48("29") ? `` : (stryCov_9fa48("29"), `${BASE_URL}/vets.html`), stryMutAct_9fa48("30") ? {} : (stryCov_9fa48("30"), {
          waitUntil: stryMutAct_9fa48("31") ? "" : (stryCov_9fa48("31"), 'domcontentloaded')
        }));
        await expect(page).toHaveURL(/vets.html/);
        await page.waitForSelector(stryMutAct_9fa48("32") ? "" : (stryCov_9fa48("32"), 'h2'), stryMutAct_9fa48("33") ? {} : (stryCov_9fa48("33"), {
          timeout: 5000
        }));
        await expect(page.locator(stryMutAct_9fa48("34") ? "" : (stryCov_9fa48("34"), 'h2'))).toHaveText(stryMutAct_9fa48("35") ? "" : (stryCov_9fa48("35"), 'Veterinarians'));
      }
    });
    runWithMutationCheck(stryMutAct_9fa48("36") ? "" : (stryCov_9fa48("36"), 'Error page should be accessible'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("37")) {
        {}
      } else {
        stryCov_9fa48("37");
        await page.goto(stryMutAct_9fa48("38") ? `` : (stryCov_9fa48("38"), `${BASE_URL}/oups`), stryMutAct_9fa48("39") ? {} : (stryCov_9fa48("39"), {
          waitUntil: stryMutAct_9fa48("40") ? "" : (stryCov_9fa48("40"), 'domcontentloaded')
        }));

        // Debugging step: Log actual URL
        console.log(stryMutAct_9fa48("41") ? "" : (stryCov_9fa48("41"), "Current URL:"), page.url());

        // Check if the error page correctly loaded
        await expect(page).toHaveURL(/oups/);
        await page.waitForSelector(stryMutAct_9fa48("42") ? "" : (stryCov_9fa48("42"), 'h2'), stryMutAct_9fa48("43") ? {} : (stryCov_9fa48("43"), {
          timeout: 5000
        }));
        await expect(page.locator(stryMutAct_9fa48("44") ? "" : (stryCov_9fa48("44"), 'h2'))).toHaveText(stryMutAct_9fa48("45") ? "" : (stryCov_9fa48("45"), 'Something happened...'));
      }
    });
  }
});

// ✅ Owner Search & Button Verification
test.describe(stryMutAct_9fa48("46") ? "" : (stryCov_9fa48("46"), 'Owner Search & Button Verification'), () => {
  if (stryMutAct_9fa48("47")) {
    {}
  } else {
    stryCov_9fa48("47");
    runWithMutationCheck(stryMutAct_9fa48("48") ? "" : (stryCov_9fa48("48"), 'Verify "Add Owner" button and form submission'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("49")) {
        {}
      } else {
        stryCov_9fa48("49");
        await page.goto(stryMutAct_9fa48("50") ? `` : (stryCov_9fa48("50"), `${BASE_URL}/owners/find`), stryMutAct_9fa48("51") ? {} : (stryCov_9fa48("51"), {
          waitUntil: stryMutAct_9fa48("52") ? "" : (stryCov_9fa48("52"), 'domcontentloaded')
        }));
        await page.click(stryMutAct_9fa48("53") ? "" : (stryCov_9fa48("53"), 'a:has-text("Add Owner")'));
        await expect(page).toHaveURL(stryMutAct_9fa48("54") ? `` : (stryCov_9fa48("54"), `${BASE_URL}/owners/new`));

        // ✅ Generate a unique owner name
        const randomId = Math.floor(stryMutAct_9fa48("55") ? Math.random() / 100 : (stryCov_9fa48("55"), Math.random() * 100));
        const firstName = stryMutAct_9fa48("56") ? `` : (stryCov_9fa48("56"), `John${randomId}`);
        const lastName = stryMutAct_9fa48("57") ? `` : (stryCov_9fa48("57"), `Doe${randomId}`);
        console.log(stryMutAct_9fa48("58") ? `` : (stryCov_9fa48("58"), `Creating owner: ${firstName} ${lastName}`));

        // ✅ Fill out form correctly
        await page.fill(stryMutAct_9fa48("59") ? "" : (stryCov_9fa48("59"), 'input[name="firstName"]'), firstName);
        await page.fill(stryMutAct_9fa48("60") ? "" : (stryCov_9fa48("60"), 'input[name="lastName"]'), lastName);
        await page.fill(stryMutAct_9fa48("61") ? "" : (stryCov_9fa48("61"), 'input[name="address"]'), stryMutAct_9fa48("62") ? "" : (stryCov_9fa48("62"), '123 Main St'));
        await page.fill(stryMutAct_9fa48("63") ? "" : (stryCov_9fa48("63"), 'input[name="city"]'), stryMutAct_9fa48("64") ? "" : (stryCov_9fa48("64"), 'Turku'));
        await page.fill(stryMutAct_9fa48("65") ? "" : (stryCov_9fa48("65"), 'input[name="telephone"]'), stryMutAct_9fa48("66") ? "" : (stryCov_9fa48("66"), '0417238232'));

        // ✅ Submit the form
        await page.click(stryMutAct_9fa48("67") ? "" : (stryCov_9fa48("67"), 'button:has-text("Add Owner")'));

        // ✅ Capture the new Owner ID dynamically
        await expect(page).toHaveURL(stryMutAct_9fa48("69") ? /owners\/\D+/ : stryMutAct_9fa48("68") ? /owners\/\d/ : (stryCov_9fa48("68", "69"), /owners\/\d+/));
        const newOwnerUrl = page.url();
        dynamicOwnerId = stryMutAct_9fa48("70") ? newOwnerUrl.match(/owners\/(\d+)/)?.[1] && '' : (stryCov_9fa48("70"), (stryMutAct_9fa48("71") ? newOwnerUrl.match(/owners\/(\d+)/)[1] : (stryCov_9fa48("71"), newOwnerUrl.match(stryMutAct_9fa48("73") ? /owners\/(\D+)/ : stryMutAct_9fa48("72") ? /owners\/(\d)/ : (stryCov_9fa48("72", "73"), /owners\/(\d+)/))?.[1])) ?? (stryMutAct_9fa48("74") ? "Stryker was here!" : (stryCov_9fa48("74"), '')));
        console.log(stryMutAct_9fa48("75") ? "" : (stryCov_9fa48("75"), "New Owner ID:"), dynamicOwnerId);
      }
    });
  }
});

// ✅ Owner Management
test.describe(stryMutAct_9fa48("76") ? "" : (stryCov_9fa48("76"), 'Owner Management'), () => {
  if (stryMutAct_9fa48("77")) {
    {}
  } else {
    stryCov_9fa48("77");
    runWithMutationCheck(stryMutAct_9fa48("78") ? "" : (stryCov_9fa48("78"), 'Edit Owner Details'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("79")) {
        {}
      } else {
        stryCov_9fa48("79");
        if (stryMutAct_9fa48("82") ? false : stryMutAct_9fa48("81") ? true : stryMutAct_9fa48("80") ? dynamicOwnerId : (stryCov_9fa48("80", "81", "82"), !dynamicOwnerId)) throw new Error(stryMutAct_9fa48("83") ? "" : (stryCov_9fa48("83"), 'Owner ID not found!'));
        await page.goto(stryMutAct_9fa48("84") ? `` : (stryCov_9fa48("84"), `${BASE_URL}/owners/${dynamicOwnerId}`), stryMutAct_9fa48("85") ? {} : (stryCov_9fa48("85"), {
          waitUntil: stryMutAct_9fa48("86") ? "" : (stryCov_9fa48("86"), 'domcontentloaded')
        }));
        await page.click(stryMutAct_9fa48("87") ? "" : (stryCov_9fa48("87"), 'a:has-text("Edit Owner")'));
        await expect(page).toHaveURL(new RegExp(stryMutAct_9fa48("88") ? `` : (stryCov_9fa48("88"), `${BASE_URL}/owners/${dynamicOwnerId}/edit`)));
        await page.fill(stryMutAct_9fa48("89") ? "" : (stryCov_9fa48("89"), 'input[name="firstName"]'), stryMutAct_9fa48("90") ? "" : (stryCov_9fa48("90"), 'UpdatedJohn'));
        await page.fill(stryMutAct_9fa48("91") ? "" : (stryCov_9fa48("91"), 'input[name="lastName"]'), stryMutAct_9fa48("92") ? "" : (stryCov_9fa48("92"), 'UpdatedDoe'));
        await page.fill(stryMutAct_9fa48("93") ? "" : (stryCov_9fa48("93"), 'input[name="address"]'), stryMutAct_9fa48("94") ? "" : (stryCov_9fa48("94"), '456 Updated St'));
        await page.fill(stryMutAct_9fa48("95") ? "" : (stryCov_9fa48("95"), 'input[name="city"]'), stryMutAct_9fa48("96") ? "" : (stryCov_9fa48("96"), 'Helsinki'));
        await page.fill(stryMutAct_9fa48("97") ? "" : (stryCov_9fa48("97"), 'input[name="telephone"]'), stryMutAct_9fa48("98") ? "" : (stryCov_9fa48("98"), '0412345678'));
        await page.click(stryMutAct_9fa48("99") ? "" : (stryCov_9fa48("99"), 'button:has-text("Update Owner")'));

        // ✅ Verify owner details were updated
        await expect(page).toHaveURL(new RegExp(stryMutAct_9fa48("100") ? `` : (stryCov_9fa48("100"), `${BASE_URL}/owners/${dynamicOwnerId}`)));
        await expect(page.locator(stryMutAct_9fa48("101") ? "" : (stryCov_9fa48("101"), 'table.table-striped')).first()).toContainText(stryMutAct_9fa48("102") ? "" : (stryCov_9fa48("102"), 'UpdatedJohn UpdatedDoe'));
      }
    });
    runWithMutationCheck(stryMutAct_9fa48("103") ? "" : (stryCov_9fa48("103"), 'Add a New Pet for an Owner'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("104")) {
        {}
      } else {
        stryCov_9fa48("104");
        if (stryMutAct_9fa48("107") ? false : stryMutAct_9fa48("106") ? true : stryMutAct_9fa48("105") ? dynamicOwnerId : (stryCov_9fa48("105", "106", "107"), !dynamicOwnerId)) throw new Error(stryMutAct_9fa48("108") ? "" : (stryCov_9fa48("108"), 'Owner ID not found!'));
        await page.goto(stryMutAct_9fa48("109") ? `` : (stryCov_9fa48("109"), `${BASE_URL}/owners/${dynamicOwnerId}`), stryMutAct_9fa48("110") ? {} : (stryCov_9fa48("110"), {
          waitUntil: stryMutAct_9fa48("111") ? "" : (stryCov_9fa48("111"), 'domcontentloaded')
        }));
        await page.click(stryMutAct_9fa48("112") ? "" : (stryCov_9fa48("112"), 'a:has-text("Add New Pet")'));
        await expect(page).toHaveURL(new RegExp(stryMutAct_9fa48("113") ? `` : (stryCov_9fa48("113"), `${BASE_URL}/owners/${dynamicOwnerId}/pets/new`)));

        // ✅ Generate unique pet name
        const randomPetName = stryMutAct_9fa48("114") ? `` : (stryCov_9fa48("114"), `Pet${Math.floor(stryMutAct_9fa48("115") ? Math.random() / 1000 : (stryCov_9fa48("115"), Math.random() * 1000))}`);
        await page.fill(stryMutAct_9fa48("116") ? "" : (stryCov_9fa48("116"), 'input[name="name"]'), randomPetName);
        await page.fill(stryMutAct_9fa48("117") ? "" : (stryCov_9fa48("117"), 'input[name="birthDate"]'), stryMutAct_9fa48("118") ? "" : (stryCov_9fa48("118"), '2023-06-15'));

        // ✅ Select random pet type
        const petTypes = stryMutAct_9fa48("119") ? [] : (stryCov_9fa48("119"), [stryMutAct_9fa48("120") ? "" : (stryCov_9fa48("120"), 'bird'), stryMutAct_9fa48("121") ? "" : (stryCov_9fa48("121"), 'cat'), stryMutAct_9fa48("122") ? "" : (stryCov_9fa48("122"), 'dog'), stryMutAct_9fa48("123") ? "" : (stryCov_9fa48("123"), 'hamster'), stryMutAct_9fa48("124") ? "" : (stryCov_9fa48("124"), 'lizard'), stryMutAct_9fa48("125") ? "" : (stryCov_9fa48("125"), 'snake')]);
        await page.selectOption(stryMutAct_9fa48("126") ? "" : (stryCov_9fa48("126"), 'select[name="type"]'), petTypes[Math.floor(stryMutAct_9fa48("127") ? Math.random() / petTypes.length : (stryCov_9fa48("127"), Math.random() * petTypes.length))]);

        // ✅ Click "Add Pet"
        await page.click(stryMutAct_9fa48("128") ? "" : (stryCov_9fa48("128"), 'button:has-text("Add Pet")'));

        // ✅ Verify pet appears under the owner
        await expect(page).toHaveURL(new RegExp(stryMutAct_9fa48("129") ? `` : (stryCov_9fa48("129"), `${BASE_URL}/owners/${dynamicOwnerId}`)));
        await expect(page.locator(stryMutAct_9fa48("130") ? "" : (stryCov_9fa48("130"), 'table.table-striped')).nth(1)).toContainText(randomPetName);
      }
    });
  }
});

// ✅ Veterinarians Table Fix
runWithMutationCheck(stryMutAct_9fa48("131") ? "" : (stryCov_9fa48("131"), 'Verify Veterinarians Table'), async ({
  page
}) => {
  if (stryMutAct_9fa48("132")) {
    {}
  } else {
    stryCov_9fa48("132");
    await page.goto(stryMutAct_9fa48("133") ? `` : (stryCov_9fa48("133"), `${BASE_URL}/vets.html`), stryMutAct_9fa48("134") ? {} : (stryCov_9fa48("134"), {
      waitUntil: stryMutAct_9fa48("135") ? "" : (stryCov_9fa48("135"), 'domcontentloaded')
    }));
    await page.waitForSelector(stryMutAct_9fa48("136") ? "" : (stryCov_9fa48("136"), 'table'), stryMutAct_9fa48("137") ? {} : (stryCov_9fa48("137"), {
      timeout: 5000
    }));
    await expect(page.locator(stryMutAct_9fa48("138") ? "" : (stryCov_9fa48("138"), 'table'))).toBeVisible();
    await expect(page.locator(stryMutAct_9fa48("139") ? "" : (stryCov_9fa48("139"), 'table'))).toContainText(stryMutAct_9fa48("140") ? "" : (stryCov_9fa48("140"), 'James Carter'));
    await expect(page.locator(stryMutAct_9fa48("141") ? "" : (stryCov_9fa48("141"), 'table'))).toContainText(stryMutAct_9fa48("142") ? "" : (stryCov_9fa48("142"), 'Helen Leary'));
    await expect(page.locator(stryMutAct_9fa48("143") ? "" : (stryCov_9fa48("143"), 'table'))).toContainText(stryMutAct_9fa48("144") ? "" : (stryCov_9fa48("144"), 'radiology'));
    await expect(page.locator(stryMutAct_9fa48("145") ? "" : (stryCov_9fa48("145"), 'table'))).toContainText(stryMutAct_9fa48("146") ? "" : (stryCov_9fa48("146"), 'dentistry'));
  }
});
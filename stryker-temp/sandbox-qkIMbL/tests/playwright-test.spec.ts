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
const BASE_URL = stryMutAct_9fa48("0") ? "" : (stryCov_9fa48("0"), 'http://localhost:8080');
test.describe(stryMutAct_9fa48("1") ? "" : (stryCov_9fa48("1"), 'Owner Management'), () => {
  if (stryMutAct_9fa48("2")) {
    {}
  } else {
    stryCov_9fa48("2");
    let ownerId: number;
    test(stryMutAct_9fa48("3") ? "" : (stryCov_9fa48("3"), 'Initialize Owner Creation Form'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("4")) {
        {}
      } else {
        stryCov_9fa48("4");
        await page.goto(stryMutAct_9fa48("5") ? `` : (stryCov_9fa48("5"), `${BASE_URL}/owners/new`));
        await expect(page).toHaveTitle(/PetClinic/);
        await expect(page.locator(stryMutAct_9fa48("6") ? "" : (stryCov_9fa48("6"), 'h2'))).toContainText(/Owner/i);
      }
    });
    test(stryMutAct_9fa48("7") ? "" : (stryCov_9fa48("7"), 'Create New Owner Successfully'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("8")) {
        {}
      } else {
        stryCov_9fa48("8");
        await page.goto(stryMutAct_9fa48("9") ? `` : (stryCov_9fa48("9"), `${BASE_URL}/owners/new`));
        await page.fill(stryMutAct_9fa48("10") ? "" : (stryCov_9fa48("10"), '[name="firstName"]'), stryMutAct_9fa48("11") ? "" : (stryCov_9fa48("11"), 'Joe'));
        await page.fill(stryMutAct_9fa48("12") ? "" : (stryCov_9fa48("12"), '[name="lastName"]'), stryMutAct_9fa48("13") ? "" : (stryCov_9fa48("13"), 'Bloggs'));
        await page.fill(stryMutAct_9fa48("14") ? "" : (stryCov_9fa48("14"), '[name="address"]'), stryMutAct_9fa48("15") ? "" : (stryCov_9fa48("15"), '123 Caramel Street'));
        await page.fill(stryMutAct_9fa48("16") ? "" : (stryCov_9fa48("16"), '[name="city"]'), stryMutAct_9fa48("17") ? "" : (stryCov_9fa48("17"), 'London'));
        await page.fill(stryMutAct_9fa48("18") ? "" : (stryCov_9fa48("18"), '[name="telephone"]'), stryMutAct_9fa48("19") ? "" : (stryCov_9fa48("19"), '01316761638'));
        await page.click(stryMutAct_9fa48("20") ? "" : (stryCov_9fa48("20"), 'button[type="submit"]'));
        await expect(page).toHaveURL(stryMutAct_9fa48("22") ? /owners\/\D+/ : stryMutAct_9fa48("21") ? /owners\/\d/ : (stryCov_9fa48("21", "22"), /owners\/\d+/));
        console.log(stryMutAct_9fa48("23") ? "" : (stryCov_9fa48("23"), '✅ Owner created successfully'));
        const urlParts = page.url().split(stryMutAct_9fa48("24") ? "" : (stryCov_9fa48("24"), '/'));
        ownerId = parseInt(urlParts[stryMutAct_9fa48("25") ? urlParts.length + 1 : (stryCov_9fa48("25"), urlParts.length - 1)]);
      }
    });
    test(stryMutAct_9fa48("26") ? "" : (stryCov_9fa48("26"), 'Fail to Create Owner - Missing Required Fields'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("27")) {
        {}
      } else {
        stryCov_9fa48("27");
        await page.goto(stryMutAct_9fa48("28") ? `` : (stryCov_9fa48("28"), `${BASE_URL}/owners/new`));
        await page.fill(stryMutAct_9fa48("29") ? "" : (stryCov_9fa48("29"), '[name="firstName"]'), stryMutAct_9fa48("30") ? "" : (stryCov_9fa48("30"), 'Joe'));
        await page.fill(stryMutAct_9fa48("31") ? "" : (stryCov_9fa48("31"), '[name="lastName"]'), stryMutAct_9fa48("32") ? "" : (stryCov_9fa48("32"), 'Bloggs'));
        await page.fill(stryMutAct_9fa48("33") ? "" : (stryCov_9fa48("33"), '[name="city"]'), stryMutAct_9fa48("34") ? "" : (stryCov_9fa48("34"), 'London'));
        await page.click(stryMutAct_9fa48("35") ? "" : (stryCov_9fa48("35"), 'button[type="submit"]'));
        await expect(page.locator(stryMutAct_9fa48("36") ? "" : (stryCov_9fa48("36"), '.has-error'))).toHaveCount(2);
        console.log(stryMutAct_9fa48("37") ? "" : (stryCov_9fa48("37"), '✅ Proper validation messages displayed'));
      }
    });
    test(stryMutAct_9fa48("38") ? "" : (stryCov_9fa48("38"), 'Find Owners - List View'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("39")) {
        {}
      } else {
        stryCov_9fa48("39");
        await page.goto(stryMutAct_9fa48("40") ? `` : (stryCov_9fa48("40"), `${BASE_URL}/owners/find`));
        await page.fill(stryMutAct_9fa48("41") ? "" : (stryCov_9fa48("41"), '[name="lastName"]'), stryMutAct_9fa48("42") ? "Stryker was here!" : (stryCov_9fa48("42"), ''));
        await page.click(stryMutAct_9fa48("43") ? "" : (stryCov_9fa48("43"), 'button[type="submit"]'));
        await expect(page).toHaveURL(stryMutAct_9fa48("45") ? /\/owners(\?.)?/ : stryMutAct_9fa48("44") ? /\/owners(\?.*)/ : (stryCov_9fa48("44", "45"), /\/owners(\?.*)?/));
        await expect(page.locator(stryMutAct_9fa48("46") ? "" : (stryCov_9fa48("46"), 'table'))).toBeVisible();
        console.log(stryMutAct_9fa48("47") ? "" : (stryCov_9fa48("47"), '✅ Owners list displayed correctly'));
      }
    });
    test(stryMutAct_9fa48("48") ? "" : (stryCov_9fa48("48"), 'Update Owner Information'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("49")) {
        {}
      } else {
        stryCov_9fa48("49");
        await page.goto(stryMutAct_9fa48("50") ? `` : (stryCov_9fa48("50"), `${BASE_URL}/owners/${ownerId}/edit`));
        await page.waitForSelector(stryMutAct_9fa48("51") ? "" : (stryCov_9fa48("51"), '[name="firstName"]'), stryMutAct_9fa48("52") ? {} : (stryCov_9fa48("52"), {
          state: stryMutAct_9fa48("53") ? "" : (stryCov_9fa48("53"), 'visible'),
          timeout: 5000
        }));
        await page.fill(stryMutAct_9fa48("54") ? "" : (stryCov_9fa48("54"), '[name="firstName"]'), stryMutAct_9fa48("55") ? "" : (stryCov_9fa48("55"), 'Joe Updated'));
        await page.fill(stryMutAct_9fa48("56") ? "" : (stryCov_9fa48("56"), '[name="lastName"]'), stryMutAct_9fa48("57") ? "" : (stryCov_9fa48("57"), 'Bloggs Updated'));
        await page.click(stryMutAct_9fa48("58") ? "" : (stryCov_9fa48("58"), 'button[type="submit"]'));
        await expect(page).toHaveURL(stryMutAct_9fa48("59") ? `` : (stryCov_9fa48("59"), `/owners/${ownerId}`));
        console.log(stryMutAct_9fa48("60") ? "" : (stryCov_9fa48("60"), '✅ Owner details updated successfully'));
      }
    });
    test(stryMutAct_9fa48("61") ? "" : (stryCov_9fa48("61"), 'Fail to Update Owner - Missing Required Fields'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("62")) {
        {}
      } else {
        stryCov_9fa48("62");
        await page.goto(stryMutAct_9fa48("63") ? `` : (stryCov_9fa48("63"), `${BASE_URL}/owners/${ownerId}/edit`));
        await page.waitForSelector(stryMutAct_9fa48("64") ? "" : (stryCov_9fa48("64"), '[name="address"]'), stryMutAct_9fa48("65") ? {} : (stryCov_9fa48("65"), {
          state: stryMutAct_9fa48("66") ? "" : (stryCov_9fa48("66"), 'visible'),
          timeout: 5000
        }));
        await page.fill(stryMutAct_9fa48("67") ? "" : (stryCov_9fa48("67"), '[name="address"]'), stryMutAct_9fa48("68") ? "Stryker was here!" : (stryCov_9fa48("68"), ''));
        await page.fill(stryMutAct_9fa48("69") ? "" : (stryCov_9fa48("69"), '[name="telephone"]'), stryMutAct_9fa48("70") ? "Stryker was here!" : (stryCov_9fa48("70"), ''));
        await page.click(stryMutAct_9fa48("71") ? "" : (stryCov_9fa48("71"), 'button[type="submit"]'));
        await expect(page.locator(stryMutAct_9fa48("72") ? "" : (stryCov_9fa48("72"), '.has-error'))).toHaveCount(2);
        console.log(stryMutAct_9fa48("73") ? "" : (stryCov_9fa48("73"), '✅ Proper validation for update errors'));
      }
    });
  }
});
test.describe(stryMutAct_9fa48("74") ? "" : (stryCov_9fa48("74"), 'Pet Management'), () => {
  if (stryMutAct_9fa48("75")) {
    {}
  } else {
    stryCov_9fa48("75");
    test(stryMutAct_9fa48("76") ? "" : (stryCov_9fa48("76"), 'Add a New Pet Successfully'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("77")) {
        {}
      } else {
        stryCov_9fa48("77");
        await page.goto(stryMutAct_9fa48("78") ? `` : (stryCov_9fa48("78"), `${BASE_URL}/owners/1/pets/new`));
        await page.fill(stryMutAct_9fa48("79") ? "" : (stryCov_9fa48("79"), '[name="name"]'), stryMutAct_9fa48("80") ? "" : (stryCov_9fa48("80"), 'Buddy'));
        await page.fill(stryMutAct_9fa48("81") ? "" : (stryCov_9fa48("81"), '[name="birthDate"]'), stryMutAct_9fa48("82") ? "" : (stryCov_9fa48("82"), '2015-02-12'));
        await page.selectOption(stryMutAct_9fa48("83") ? "" : (stryCov_9fa48("83"), '[name="type"]'), stryMutAct_9fa48("84") ? "" : (stryCov_9fa48("84"), 'dog'));
        await page.click(stryMutAct_9fa48("85") ? "" : (stryCov_9fa48("85"), 'button[type="submit"]'));
        await expect(page).toHaveURL(/owners\/1/);
        console.log(stryMutAct_9fa48("86") ? "" : (stryCov_9fa48("86"), '✅ Pet added successfully'));
      }
    });
  }
});
test.describe(stryMutAct_9fa48("87") ? "" : (stryCov_9fa48("87"), 'Visit Management'), () => {
  if (stryMutAct_9fa48("88")) {
    {}
  } else {
    stryCov_9fa48("88");
    test(stryMutAct_9fa48("89") ? "" : (stryCov_9fa48("89"), 'Schedule a Visit'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("90")) {
        {}
      } else {
        stryCov_9fa48("90");
        await page.goto(stryMutAct_9fa48("91") ? `` : (stryCov_9fa48("91"), `${BASE_URL}/owners/1/pets/1/visits/new`));
        await page.fill(stryMutAct_9fa48("92") ? "" : (stryCov_9fa48("92"), '[name="date"]'), stryMutAct_9fa48("93") ? "" : (stryCov_9fa48("93"), '2024-06-10'));
        await page.fill(stryMutAct_9fa48("94") ? "" : (stryCov_9fa48("94"), '[name="description"]'), stryMutAct_9fa48("95") ? "" : (stryCov_9fa48("95"), 'Annual Checkup'));
        await page.click(stryMutAct_9fa48("96") ? "" : (stryCov_9fa48("96"), 'button[type="submit"]'));
        await expect(page).toHaveURL(/owners\/1/);
        console.log(stryMutAct_9fa48("97") ? "" : (stryCov_9fa48("97"), '✅ Visit scheduled successfully'));
      }
    });
    test(stryMutAct_9fa48("98") ? "" : (stryCov_9fa48("98"), 'Fail to Schedule Visit - Missing Description'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("99")) {
        {}
      } else {
        stryCov_9fa48("99");
        await page.goto(stryMutAct_9fa48("100") ? `` : (stryCov_9fa48("100"), `${BASE_URL}/owners/1/pets/1/visits/new`));
        await page.fill(stryMutAct_9fa48("101") ? "" : (stryCov_9fa48("101"), '[name="date"]'), stryMutAct_9fa48("102") ? "" : (stryCov_9fa48("102"), '2024-06-10'));
        await page.click(stryMutAct_9fa48("103") ? "" : (stryCov_9fa48("103"), 'button[type="submit"]'));
        await expect(page.locator(stryMutAct_9fa48("104") ? "" : (stryCov_9fa48("104"), '.help-inline'))).toContainText(/must not be empty/);
        console.log(stryMutAct_9fa48("105") ? "" : (stryCov_9fa48("105"), '✅ Proper validation for visit errors'));
      }
    });
  }
});
test.describe(stryMutAct_9fa48("106") ? "" : (stryCov_9fa48("106"), 'Veterinarian Management'), () => {
  if (stryMutAct_9fa48("107")) {
    {}
  } else {
    stryCov_9fa48("107");
    test(stryMutAct_9fa48("108") ? "" : (stryCov_9fa48("108"), 'Fetch List of Veterinarians'), async ({
      request
    }) => {
      if (stryMutAct_9fa48("109")) {
        {}
      } else {
        stryCov_9fa48("109");
        const response = await request.get(stryMutAct_9fa48("110") ? `` : (stryCov_9fa48("110"), `${BASE_URL}/vets`));
        expect(response.status()).toBe(200);
        const json = await response.json();
        expect(json.vetList.length).toBeGreaterThan(0);
        console.log(stryMutAct_9fa48("111") ? "" : (stryCov_9fa48("111"), '✅ Veterinarians list retrieved'));
      }
    });
    test(stryMutAct_9fa48("112") ? "" : (stryCov_9fa48("112"), 'View Veterinarians Page'), async ({
      page
    }) => {
      if (stryMutAct_9fa48("113")) {
        {}
      } else {
        stryCov_9fa48("113");
        await page.goto(stryMutAct_9fa48("114") ? `` : (stryCov_9fa48("114"), `${BASE_URL}/vets.html`));
        await expect(page.locator(stryMutAct_9fa48("115") ? "" : (stryCov_9fa48("115"), 'h2'))).toContainText(stryMutAct_9fa48("116") ? "" : (stryCov_9fa48("116"), 'Veterinarians'));
        console.log(stryMutAct_9fa48("117") ? "" : (stryCov_9fa48("117"), '✅ Veterinarians page displayed'));
      }
    });
  }
});
test.describe(stryMutAct_9fa48("118") ? "" : (stryCov_9fa48("118"), 'Crash Controller (Error Handling)'), () => {
  if (stryMutAct_9fa48("119")) {
    {}
  } else {
    stryCov_9fa48("119");
    test(stryMutAct_9fa48("120") ? "" : (stryCov_9fa48("120"), 'Trigger Internal Server Error'), async ({
      request
    }) => {
      if (stryMutAct_9fa48("121")) {
        {}
      } else {
        stryCov_9fa48("121");
        const response = await request.get(stryMutAct_9fa48("122") ? `` : (stryCov_9fa48("122"), `${BASE_URL}/oups`));
        expect(response.status()).toBe(500);
        console.log(stryMutAct_9fa48("123") ? "" : (stryCov_9fa48("123"), '✅ CrashController test passed'));
      }
    });
  }
});
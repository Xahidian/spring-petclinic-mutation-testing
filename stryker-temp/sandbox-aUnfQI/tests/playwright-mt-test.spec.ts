// @ts-nocheck
import { test, expect } from '@playwright/test';

// MR1: Idempotency of Addition (Adding the same pet twice should not create duplicates)
test('MR1: Prevent duplicate pets', async ({ page }) => {
    await page.goto('http://localhost:8080/owners/new');

    await page.fill('input[name="firstName"]', 'John');
    await page.fill('input[name="lastName"]', 'Doe');
    await page.fill('input[name="address"]', '123 Main Street');
    await page.fill('input[name="city"]', 'Turku');
    await page.fill('input[name="telephone"]', '0417238232');

    await page.click('button:text("Add Owner")');

    // Try adding the same pet again
    await page.goto('http://localhost:8080/owners/new');
    await page.fill('input[name="firstName"]', 'John');
    await page.fill('input[name="lastName"]', 'Doe');
    await page.fill('input[name="address"]', '123 Main Street');
    await page.fill('input[name="city"]', 'Turku');
    await page.fill('input[name="telephone"]', '0417238232');
    await page.click('button:text("Add Owner")');

    // Expect some kind of error message or prevent duplication
    await expect(page.locator('body')).toContainText('already exists');
});

// MR2: Commutativity (Changing order of inputs should not affect results)
test('MR2: Commutativity of Input Fields', async ({ page }) => {
    await page.goto('http://localhost:8080/owners/new');

    await page.fill('input[name="firstName"]', 'Doe');
    await page.fill('input[name="lastName"]', 'John'); // Swapped order
    await page.fill('input[name="address"]', '123 Main Street');
    await page.fill('input[name="city"]', 'Turku');
    await page.fill('input[name="telephone"]', '0417238232');

    await page.click('button:text("Add Owner")');

    // Compare with original John Doe entry
    await page.goto('http://localhost:8080/owners/find');
    await page.fill('input[name="lastName"]', 'Doe');
    await page.click('button:text("Find Owner")');

    await expect(page.locator('table')).toContainText('Doe');
});

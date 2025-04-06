// @ts-nocheck
import { test, expect } from '@playwright/test';

const BASE_URL = 'http://localhost:8080';

test.describe('Owner Management', () => {
    test('Create New Owner Successfully', async ({ page }) => {
        await page.goto(`${BASE_URL}/owners/new`);
        await page.fill('[name="firstName"]', 'Joe');
        await page.fill('[name="lastName"]', 'Bloggs');
        await page.fill('[name="address"]', '123 Caramel Street');
        await page.fill('[name="city"]', 'London');
        await page.fill('[name="telephone"]', '01316761638');
        await page.click('button[type="submit"]');
        await expect(page).toHaveURL(/owners\/\d+/);
        console.log('✅ Owner created successfully');
    });

    test('Fail to Create Owner - Missing Required Fields', async ({ page }) => {
        await page.goto(`${BASE_URL}/owners/new`);
        await page.click('button[type="submit"]');
        await expect(page.locator('.help-inline')).toContainText('must not be empty');
        console.log('✅ Proper validation messages displayed');
    });
});


test.describe('Pet Management', () => {
    test('Add a New Pet Successfully', async ({ page }) => {
        await page.goto(`${BASE_URL}/owners/1/pets/new`);
        await page.fill('[name="name"]', 'Buddy');
        await page.fill('[name="birthDate"]', '2015-02-12');
        await page.selectOption('[name="type"]', 'dog');
        await page.click('button[type="submit"]');
        await expect(page).toHaveURL(/owners\/1/);
        console.log('✅ Pet added successfully');
    });

    test('Fail to Add Pet - Missing Required Fields', async ({ page }) => {
        await page.goto(`${BASE_URL}/owners/1/pets/new`);
        await page.click('button[type="submit"]');
        await expect(page.locator('.help-inline')).toContainText('is required');
        console.log('✅ Proper validation messages displayed');
    });
});

test.describe('Visit Management', () => {
    test('Schedule a Visit', async ({ page }) => {
        await page.goto(`${BASE_URL}/owners/1/pets/1/visits/new`);
        await page.fill('[name="date"]', '2025-06-10');
        await page.fill('[name="description"]', 'Annual Checkup');
        await page.click('button[type="submit"]');
        await expect(page).toHaveURL(/owners\/1/);
        console.log('✅ Visit scheduled successfully');
    });

    test('Fail to Schedule Visit - Missing Description', async ({ page }) => {
        await page.goto(`${BASE_URL}/owners/1/pets/1/visits/new`);
        await page.click('button[type="submit"]');
        await expect(page.locator('.help-inline')).toContainText('is required');
        console.log('✅ Proper validation for visit errors');
    });
});

test.describe('Veterinarian Management', () => {
    test('Fetch List of Veterinarians', async ({ page }) => {
        await page.goto(`${BASE_URL}/vets.html`);
        await expect(page.locator('table')).toBeVisible();
        console.log('✅ Veterinarians list retrieved');
    });

    test('View Veterinarians Page', async ({ page }) => {
        await page.goto(`${BASE_URL}/vets.html`);
        await expect(page.locator('h2')).toContainText('Veterinarians');
        console.log('✅ Veterinarians page displayed');
    });
});

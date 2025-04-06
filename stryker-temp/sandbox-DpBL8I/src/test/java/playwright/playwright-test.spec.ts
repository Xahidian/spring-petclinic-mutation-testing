// @ts-nocheck
import { test, expect } from '@playwright/test';

test.describe('Spring Pet Clinic UI Tests', () => {
  
  test('Add a new owner', async ({ page }) => {
    await page.goto('http://localhost:8080');
    await page.click('text=Find Owners');
    await page.click('text=Add Owner');
    
    // Fill the form
    await page.fill('input[name="firstName"]', 'John');
    await page.fill('input[name="lastName"]', 'Doe');
    await page.fill('input[name="address"]', '123 Main St');
    await page.fill('input[name="city"]', 'Turku');
    await page.fill('input[name="telephone"]', '0412345678');
    
    await page.click('text=Add Owner');
    
    // Verify owner was added
    await expect(page.locator('text=John Doe')).toBeVisible();
  });

  test('Add a pet for the owner', async ({ page }) => {
    await page.goto('http://localhost:8080/owners/find');
    await page.fill('input[name="lastName"]', 'Doe');
    await page.click('text=Find Owner');

    await page.click('text=Add New Pet');
    await page.fill('input[name="name"]', 'Buddy');
    await page.fill('input[name="birthDate"]', '2023-01-01');
    await page.selectOption('select[name="type"]', 'dog');

    await page.click('text=Add Pet');

    // Verify pet was added
    await expect(page.locator('text=Buddy')).toBeVisible();
  });

  test('Add a visit for the pet', async ({ page }) => {
    await page.goto('http://localhost:8080/owners/11');
    await page.click('text=Add Visit');

    await page.fill('input[name="date"]', '2025-03-09');
    await page.fill('input[name="description"]', 'Routine Checkup');

    await page.click('text=Add Visit');

    // Verify visit was added
    await expect(page.locator('text=Routine Checkup')).toBeVisible();
  });

  test('Check Veterinarians page', async ({ page }) => {
    await page.goto('http://localhost:8080/vets.html');
    await expect(page.locator('text=James Carter')).toBeVisible();
  });

  test('Check error page', async ({ page }) => {
    await page.goto('http://localhost:8080/error');
    await expect(page.locator('text=Something happened')).toBeVisible();
  });

});

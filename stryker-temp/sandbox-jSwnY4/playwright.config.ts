// @ts-nocheck
// playwright.config.ts
import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './tests', // Ensure tests are inside the 'tests' folder
  fullyParallel: false, // Prevent running all tests in parallel
  forbidOnly: !!process.env.CI, // Prevent accidental "test.only()" in CI
  retries: process.env.CI ? 2 : 0, // Retry failing tests in CI
  workers: 1, // Reduce workers to prevent overloading
  reporter: 'html', // Generates an HTML report
  timeout: 60000,  // Increase timeout to 60 seconds
  use: {
    baseURL: 'http://localhost:8080', // Set correct base URL if needed
    trace: 'off', // Disable tracing for performance
    navigationTimeout: 30000,  // Increase navigation timeout to 30s
    actionTimeout: 5000,  // Increase action timeout to 5s
    headless: true, // Keep headless mode for efficiency
    ignoreHTTPSErrors: true, // Ignore HTTPS errors for speed
    viewport: { width: 1280, height: 720 }, // Optimize viewport size
  },

  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],

  // Uncomment if you need to start the server before running tests
  // webServer: {
  //   command: 'npm run start',
  //   url: 'http://localhost:8080',
  //   reuseExistingServer: !process.env.CI,
  // },
});

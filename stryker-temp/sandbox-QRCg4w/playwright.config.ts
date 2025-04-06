// @ts-nocheck
import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './tests', // Ensure tests are inside the 'tests' folder
  fullyParallel: false, // Run tests in parallel (disable if tests share state)
  forbidOnly: !!process.env.CI, // Prevent accidental "test.only()" in CI
  retries: process.env.CI ? 2 : 0, // Retry failing tests in CI
  workers: process.env.CI ? 1 : undefined, // Run tests serially in CI
  reporter: 'html', // Generates an HTML report
  timeout: 90000,  // Increase timeout to 60 seconds
  use: {
    baseURL: 'http://localhost:8080', // Set correct base URL if needed
    trace: 'on-first-retry', // Enable trace on first retry to debug failures
    navigationTimeout: 40000,  // Wait up to 40 seconds for navigation
    actionTimeout: 20000
  },

  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },

  ],

  // Uncomment this if you need to start the server before running tests
  // webServer: {
  //   command: 'npm run start',
  //   url: 'http://localhost:8080',
  //   reuseExistingServer: !process.env.CI,
  // },
});

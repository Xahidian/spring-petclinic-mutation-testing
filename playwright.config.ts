import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './tests',
  fullyParallel: false,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 2 : 0,
  workers: 1, // Run tests one at a time to prevent overload
  reporter: 'html',
  timeout: 180000,  // Increase timeout to 3 minutes
  use: {
    baseURL: 'http://localhost:9090',
    trace: 'off',
    navigationTimeout: 60000,  // Increase navigation timeout to 1 minute
    actionTimeout: 10000,  // Increase action timeout to 10s
    headless: true,
    ignoreHTTPSErrors: true,
    viewport: { width: 1280, height: 720 },
    launchOptions: {
      slowMo: 50, // Reduce speed slightly to improve stability
      args: ['--disable-gpu', '--disable-extensions'], // Disable unnecessary features
    }
  },
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],
});
// @ts-nocheck
 // stryker.config.mjs
/**
 * @type {import('@stryker-mutator/api/core').StrykerOptions}
 */
export default {
  mutate: ["tests/playwright-test.spec.ts"], // Limit mutation to a single test file first
  testRunner: "command",
  commandRunner: {
    command: "npx playwright test tests/playwright-test.spec.ts --workers=1 --timeout=120000" // Increased timeout
  },
  reporters: ["html", "clear-text", "progress"],
  timeoutMS: 180000,  // Increase Stryker timeout from 60s to 120s
  concurrency: 1, // Reduce concurrency to prevent overload
  tempDirName: "stryker-temp",
  ignorePatterns: ["node_modules", "dist"],
  warnings: {
    preprocessorErrors: false
  },
  logLevel: "info",
  coverageAnalysis: "off" // Disable coverage analysis for Playwright
};
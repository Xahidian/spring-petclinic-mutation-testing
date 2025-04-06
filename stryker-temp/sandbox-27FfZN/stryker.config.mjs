// @ts-nocheck
// stryker.config.mjs
/**
 * @type {import('@stryker-mutator/api/core').StrykerOptions}
 */
export default {
  mutate: ["tests/playwright-test.spec.ts"], // Define files for mutation
  testRunner: "command",
  commandRunner: {
    command: "npx playwright test tests/playwright-test.spec.ts --workers=2 --timeout=30000" // Run Playwright tests with 2 workers & shorter timeout
  },
  reporters: ["html", "clear-text", "progress"], // Enables real-time progress in terminal
  timeoutMS: 30000, // Reduce Stryker timeout to 30s to avoid long waits
  concurrency: 2, // Run 2 Playwright tests in parallel
  tempDirName: "stryker-temp", // Prevents temp file conflicts
  ignorePatterns: ["node_modules", "dist"], // Prevent scanning unnecessary files
  warnings: {
    preprocessorErrors: false // Ignore HTML-related warnings
  },
  logLevel: "info", // More logging details
  coverageAnalysis: "off" // Playwright doesn't use standard coverage
};

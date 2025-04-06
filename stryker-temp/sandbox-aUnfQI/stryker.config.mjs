// @ts-nocheck
// stryker.config.mjs
/**
 * @type {import('@stryker-mutator/api/core').StrykerOptions}
 */
export default {
  mutate: ["tests/playwright-test.spec.ts"], // Define files for mutation
  testRunner: "command",
  commandRunner: {
    command: "npx playwright test tests/playwright-test.spec.ts",
    env: { HEADLESS: "1" } // Force headless mode
  },
  reporters: ["html", "clear-text"], // Mutation report formats
  timeoutMS: 60000, // Increase timeout for Playwright tests
  concurrency: 1, // Reduce parallel test runs to avoid system overload
  tempDirName: "stryker-temp", // Prevents issues with temp file conflicts
  checkers: [], // Disable checkers to improve speed
  ignorePatterns: ["node_modules", "dist"], // Ignore unnecessary files
  warnings: {
    preprocessorErrors: false // Ignore HTML preprocessor warnings
  },
  coverageAnalysis: "off" // Playwright doesn't use standard coverage
};

/**
 * @type {import('@stryker-mutator/api/core').StrykerOptions}
 */
// @ts-nocheck

module.exports = {
  mutate: ["tests/playwright-test.spec.ts"], // Define files for mutation
  testRunner: "command",
  commandRunner: {
    command: "npx playwright test/playwright-test.spec.ts" // Run Playwright tests
  },
  checkers: ["typescript"], // TypeScript checker for valid mutations
  reporters: ["html", "clear-text"], // Mutation report formats
  timeoutMS: 60000, // Increase timeout for Playwright tests
  coverageAnalysis: "off" // Playwright doesn't use standard coverage
};

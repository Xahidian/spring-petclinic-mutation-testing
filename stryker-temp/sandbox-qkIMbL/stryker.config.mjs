// @ts-nocheck
// stryker.config.mjs
/**
 * @type {import('@stryker-mutator/api/core').StrykerOptions}
 */
export default {
  mutate: ["tests/playwright-test.spec.ts"],
  testRunner: "command",
  commandRunner: {
    command: "npx playwright test tests/playwright-test.spec.ts --workers=2 --timeout=60000" // Increase timeout
  },
  reporters: ["html", "clear-text", "progress"],
  timeoutMS: 60000,  // Increase Stryker timeout from 30s to 60s
  concurrency: 2, // Reduce concurrency to prevent overload
  tempDirName: "stryker-temp",
  ignorePatterns: ["node_modules", "dist"],
  warnings: {
    preprocessorErrors: false
  },
  logLevel: "info",
  coverageAnalysis: "off"
};


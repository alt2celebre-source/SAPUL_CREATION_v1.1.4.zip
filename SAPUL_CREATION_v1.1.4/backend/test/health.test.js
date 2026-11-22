const test = require("node:test");
const assert = require("node:assert/strict");
test("backend package exposes v1.1.4 test contract", () => {
  const pkg = require("../package.json");
  assert.equal(pkg.version, "1.1.4");
  assert.ok(pkg.scripts.test.includes("node --test"));
});

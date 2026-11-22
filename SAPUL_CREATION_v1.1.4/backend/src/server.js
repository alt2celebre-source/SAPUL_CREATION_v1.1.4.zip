require("dotenv").config();
const express = require("express");
const cors = require("cors");
const helmet = require("helmet");
const app = express();
app.use(helmet());
app.use(cors());
app.use(express.json({ limit: "1mb" }));

app.get("/health", (_req, res) => {
  res.json({ status: "ok", service: "sapul-creation-backend", version: "1.1.4" });
});

app.post("/api/v1/prompts/prepare", (req, res) => {
  const prompt = typeof req.body?.prompt === "string" ? req.body.prompt.trim() : "";
  if (!prompt) return res.status(400).json({ error: "A non-empty prompt is required." });
  if (prompt.length > 12000) return res.status(413).json({ error: "Prompt is too long." });
  // OMEGA is a filmmaking prompt framework, not an AI video model.
  const prepared = [
    "OMEGA FILMMAKING BRIEF",
    "Subject and action: " + prompt,
    "Visual direction: define composition, lighting, lens, camera movement, and atmosphere.",
    "Continuity: maintain consistent characters, wardrobe, environment, and screen direction.",
    "Output: specify aspect ratio, duration, frame rate, and intended platform."
  ].join("\n");
  res.json({ framework: "OMEGA", prompt: prepared, generation: "not_started" });
});

app.use((err, _req, res, _next) => {
  console.error(err);
  res.status(500).json({ error: "Internal server error." });
});
const port = Number(process.env.PORT || 3000);
if (require.main === module) app.listen(port, () => console.log(`SAPUL backend listening on ${port}`));
module.exports = app;

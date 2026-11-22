package com.sapulcreation.subtitles

data class SubtitleCue(val index: Int, val startMs: Long, val endMs: Long, val text: String)

object SrtParser {
    private val timestamp = Regex("(\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})\\s*-->\\s*(\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})")

    fun parse(source: String, maxCues: Int = 10_000, maxTextLength: Int = 20_000): List<SubtitleCue> {
        require(maxCues > 0) { "maxCues must be positive" }
        require(maxTextLength > 0) { "maxTextLength must be positive" }
        val normalized = source.removePrefix("\uFEFF").replace("\r\n", "\n").replace('\r', '\n')
        val blocks = normalized.trim().split(Regex("\\n\\s*\\n"))
        val cues = mutableListOf<SubtitleCue>()
        for (block in blocks) {
            if (block.isBlank()) continue
            val lines = block.lines()
            val timeLineIndex = lines.indexOfFirst { timestamp.matches(it.trim()) }
            if (timeLineIndex < 0) continue
            val match = timestamp.matchEntire(lines[timeLineIndex].trim()) ?: continue
            val v = (1..8).map { match.groupValues[it].toLong() }
            val start = toMillis(v[0], v[1], v[2], v[3])
            val end = toMillis(v[4], v[5], v[6], v[7])
            if (end <= start) continue
            val body = lines.drop(timeLineIndex + 1).joinToString("\n").trim()
            if (body.isEmpty() || body.length > maxTextLength) continue
            if (cues.size >= maxCues) throw IllegalArgumentException("Subtitle cue limit exceeded")
            cues += SubtitleCue(lines.firstOrNull()?.trim()?.toIntOrNull() ?: cues.size + 1, start, end, body)
        }
        return cues
    }

    private fun toMillis(h: Long, m: Long, s: Long, ms: Long): Long {
        require(m in 0..59 && s in 0..59 && ms in 0..999) { "Invalid timestamp" }
        return (((h * 60 + m) * 60 + s) * 1000) + ms
    }
}

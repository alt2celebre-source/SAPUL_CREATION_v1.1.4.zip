package com.sapulcreation.subtitles

import org.junit.Assert.*
import org.junit.Test

class SrtParserTest {
    @Test fun parsesSingleCue() {
        val r = SrtParser.parse("1\n00:00:01,000 --> 00:00:02,500\nHello")
        assertEquals(1, r.size); assertEquals(1000L, r[0].startMs); assertEquals(2500L, r[0].endMs)
    }
    @Test fun parsesMultipleCues() {
        val s = "1\n00:00:00,000 --> 00:00:01,000\nA\n\n2\n00:00:01,000 --> 00:00:02,000\nB"
        assertEquals(2, SrtParser.parse(s).size)
    }
    @Test fun acceptsCrLfAndBom() {
        val s = "\uFEFF1\r\n00:00:00,000 --> 00:00:01,000\r\nHello"
        assertEquals("Hello", SrtParser.parse(s)[0].text)
    }
    @Test fun skipsInvalidOrReversedCue() {
        val s = "1\n00:00:02,000 --> 00:00:01,000\nBad\n\n2\nnot a timestamp\nNope"
        assertTrue(SrtParser.parse(s).isEmpty())
    }
    @Test fun preservesMultilineText() {
        val s = "1\n00:00:00,000 --> 00:00:01,000\nFirst line\nSecond line"
        assertEquals("First line\nSecond line", SrtParser.parse(s)[0].text)
    }
    @Test fun enforcesCueOverflowLimit() {
        val s = (1..2).joinToString("\n\n") { "$it\n00:00:00,000 --> 00:00:01,000\nCue $it" }
        try { SrtParser.parse(s, maxCues = 1); fail("Expected overflow") }
        catch (_: IllegalArgumentException) { }
    }
    @Test fun enforcesTextLengthLimit() {
        val s = "1\n00:00:00,000 --> 00:00:01,000\nToo long"
        assertTrue(SrtParser.parse(s, maxTextLength = 3).isEmpty())
    }
}

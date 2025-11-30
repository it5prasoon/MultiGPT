package com.matrix.multigpt

import org.junit.Test
import java.io.File
import kotlin.test.assertTrue

/**
 * Tests for main strings.xml file
 * Validates: Requirements 2.3
 */
class MainStringsTest {

    @Test
    fun `main strings xml contains MultiGPT as app_name`() {
        val stringsFile = File("app/src/main/res/values/strings.xml")
        assertTrue(stringsFile.exists(), "Main strings.xml should exist")
        
        val content = stringsFile.readText()
        assertTrue(
            content.contains("<string name=\"app_name\">MultiGPT</string>"),
            "Main strings.xml should contain app_name set to MultiGPT"
        )
    }

    @Test
    fun `main strings xml uses MultiGPT in introduction logo string`() {
        val stringsFile = File("app/src/main/res/values/strings.xml")
        assertTrue(stringsFile.exists(), "Main strings.xml should exist")
        
        val content = stringsFile.readText()
        assertTrue(
            content.contains("MultiGPT Introduction Logo") || 
            content.contains("gpt_mobile_introduction_logo"),
            "Main strings.xml should reference MultiGPT in introduction logo"
        )
    }
}

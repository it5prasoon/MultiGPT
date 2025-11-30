package com.matrix.multigpt

import org.junit.Test
import java.io.File
import kotlin.test.assertTrue

/**
 * Feature: app-rename-multigpt, Property 4: Localized string consistency
 * Validates: Requirements 2.4
 * 
 * For any localization directory (values-*), the app_name string resource 
 * should be set to "MultiGPT"
 */
class LocalizedStringsTest {

    @Test
    fun `all localization directories have MultiGPT as app_name`() {
        val resDir = File("app/src/main/res")
        if (!resDir.exists()) return
        
        val valuesDirs = resDir.listFiles()
            ?.filter { it.isDirectory && it.name.startsWith("values") }
            ?: emptyList()
        
        val violations = mutableListOf<String>()
        
        for (dir in valuesDirs) {
            val stringsFile = File(dir, "strings.xml")
            if (!stringsFile.exists()) continue
            
            val content = stringsFile.readText()
            
            // Check if app_name exists in this file
            if (content.contains("name=\"app_name\"")) {
                // Verify it's set to MultiGPT
                if (!content.contains("<string name=\"app_name\">MultiGPT</string>")) {
                    violations.add("${dir.name}/strings.xml does not have app_name set to MultiGPT")
                }
            }
        }
        
        assertTrue(
            violations.isEmpty(),
            "Found localization files with incorrect app_name:\n${violations.joinToString("\n")}"
        )
    }

    @Test
    fun `no strings files contain old app name references`() {
        val resDir = File("app/src/main/res")
        if (!resDir.exists()) return
        
        val valuesDirs = resDir.listFiles()
            ?.filter { it.isDirectory && it.name.startsWith("values") }
            ?: emptyList()
        
        val violations = mutableListOf<String>()
        
        for (dir in valuesDirs) {
            val stringsFile = File(dir, "strings.xml")
            if (!stringsFile.exists()) continue
            
            val content = stringsFile.readText()
            
            // Check for old app names (excluding translatable="false" strings and URLs)
            val lines = content.lines()
            for ((index, line) in lines.withIndex()) {
                if (line.contains("translatable=\"false\"")) continue
                if (line.contains("http")) continue
                
                if (line.contains("GPTMobile") || line.contains("GPTMovel") || 
                    line.contains("Gptmobile") || line.contains("gptmobile")) {
                    violations.add("${dir.name}/strings.xml line ${index + 1}: $line")
                }
            }
        }
        
        assertTrue(
            violations.isEmpty(),
            "Found old app name references in strings files:\n${violations.joinToString("\n")}"
        )
    }
}

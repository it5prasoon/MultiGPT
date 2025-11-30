package com.matrix.multigpt

import org.junit.Test
import java.io.File
import kotlin.test.assertTrue

/**
 * Feature: app-rename-multigpt, Property: Build configuration correctness
 * Tests that verify build configuration files contain correct package and application names
 */
class BuildConfigTest {

    @Test
    fun `build gradle contains correct applicationId`() {
        val buildGradleFile = File("app/build.gradle.kts")
        assertTrue(buildGradleFile.exists(), "build.gradle.kts should exist")
        
        val content = buildGradleFile.readText()
        assertTrue(
            content.contains("applicationId = \"com.matrix.multigpt\""),
            "build.gradle.kts should contain applicationId = \"com.matrix.multigpt\""
        )
        assertTrue(
            content.contains("namespace = \"com.matrix.multigpt\""),
            "build.gradle.kts should contain namespace = \"com.matrix.multigpt\""
        )
    }

    @Test
    fun `android manifest contains correct package`() {
        val manifestFile = File("app/src/main/AndroidManifest.xml")
        assertTrue(manifestFile.exists(), "AndroidManifest.xml should exist")
        
        val content = manifestFile.readText()
        assertTrue(
            content.contains("package=\"com.matrix.multigpt\""),
            "AndroidManifest.xml should contain package=\"com.matrix.multigpt\""
        )
    }

    @Test
    fun `settings gradle contains correct project name`() {
        val settingsGradleFile = File("settings.gradle.kts")
        assertTrue(settingsGradleFile.exists(), "settings.gradle.kts should exist")
        
        val content = settingsGradleFile.readText()
        assertTrue(
            content.contains("rootProject.name = \"MultiGPT\""),
            "settings.gradle.kts should contain rootProject.name = \"MultiGPT\""
        )
    }
}

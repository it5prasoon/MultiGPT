package com.matrix.multigpt

import org.junit.Test
import java.io.File
import kotlin.test.assertTrue

/**
 * Feature: app-rename-multigpt, Property 5: Resource file naming consistency
 * Validates: Requirements 3.1, 3.2, 3.5
 * 
 * For any resource file (drawable, mipmap, or other), the filename should not contain 
 * "gpt_mobile" and should use "multigpt" where applicable
 */
class ResourceFileNamingTest {

    @Test
    fun `no drawable files contain gpt_mobile in name`() {
        val drawableDir = File("app/src/main/res/drawable")
        if (!drawableDir.exists()) return
        
        val filesWithOldName = drawableDir.listFiles()
            ?.filter { it.name.contains("gpt_mobile", ignoreCase = true) }
            ?: emptyList()
        
        assertTrue(
            filesWithOldName.isEmpty(),
            "Found drawable files with 'gpt_mobile' in name: ${filesWithOldName.map { it.name }}"
        )
    }

    @Test
    fun `no mipmap files contain gpt_mobile in name`() {
        val resDir = File("app/src/main/res")
        if (!resDir.exists()) return
        
        val mipmapDirs = resDir.listFiles()
            ?.filter { it.isDirectory && it.name.startsWith("mipmap") }
            ?: emptyList()
        
        val filesWithOldName = mipmapDirs.flatMap { dir ->
            dir.listFiles()?.filter { it.name.contains("gpt_mobile", ignoreCase = true) } ?: emptyList()
        }
        
        assertTrue(
            filesWithOldName.isEmpty(),
            "Found mipmap files with 'gpt_mobile' in name: ${filesWithOldName.map { it.name }}"
        )
    }

    @Test
    fun `no values files contain gpt_mobile in name`() {
        val resDir = File("app/src/main/res")
        if (!resDir.exists()) return
        
        val valuesDirs = resDir.listFiles()
            ?.filter { it.isDirectory && it.name.startsWith("values") }
            ?: emptyList()
        
        val filesWithOldName = valuesDirs.flatMap { dir ->
            dir.listFiles()?.filter { it.name.contains("gpt_mobile", ignoreCase = true) } ?: emptyList()
        }
        
        assertTrue(
            filesWithOldName.isEmpty(),
            "Found values files with 'gpt_mobile' in name: ${filesWithOldName.map { it.name }}"
        )
    }

    @Test
    fun `playstore icon file uses multigpt naming`() {
        val oldIcon = File("app/src/main/ic_gpt_mobile-playstore.png")
        val newIcon = File("app/src/main/ic_multigpt-playstore.png")
        
        assertTrue(
            !oldIcon.exists(),
            "Old playstore icon ic_gpt_mobile-playstore.png should not exist"
        )
        assertTrue(
            newIcon.exists(),
            "New playstore icon ic_multigpt-playstore.png should exist"
        )
    }

    @Test
    fun `multigpt resource files exist`() {
        val foregroundDrawable = File("app/src/main/res/drawable/ic_multigpt_foreground.xml")
        val monochromeDrawable = File("app/src/main/res/drawable/ic_multigpt_monochrome_foreground.xml")
        val mipmapIcon = File("app/src/main/res/mipmap-anydpi/ic_multigpt.xml")
        val backgroundColor = File("app/src/main/res/values/ic_multigpt_background.xml")
        
        assertTrue(foregroundDrawable.exists(), "ic_multigpt_foreground.xml should exist")
        assertTrue(monochromeDrawable.exists(), "ic_multigpt_monochrome_foreground.xml should exist")
        assertTrue(mipmapIcon.exists(), "ic_multigpt.xml should exist")
        assertTrue(backgroundColor.exists(), "ic_multigpt_background.xml should exist")
    }
}

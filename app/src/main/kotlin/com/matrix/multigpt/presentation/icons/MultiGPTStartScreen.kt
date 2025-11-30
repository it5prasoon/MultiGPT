package com.matrix.multigpt.presentation.icons

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MultiGPTStartScreen: ImageVector
    @Composable
    get() {
        val primary = MaterialTheme.colorScheme.primary
        val primaryContainer = MaterialTheme.colorScheme.primaryContainer
        val secondary = MaterialTheme.colorScheme.secondary
        
        return ImageVector.Builder(
            name = "MultiGPTStartScreen",
            defaultWidth = 400.dp,
            defaultHeight = 300.dp,
            viewportWidth = 400f,
            viewportHeight = 300f
        ).apply {
            // Central hub circle
            path(
                fill = SolidColor(primary),
                stroke = null
            ) {
                moveTo(200f, 120f)
                moveToRelative(-30f, 0f)
                arcToRelative(30f, 30f, 0f, isMoreThanHalf = true, isPositiveArc = true, 60f, 0f)
                arcToRelative(30f, 30f, 0f, isMoreThanHalf = true, isPositiveArc = true, -60f, 0f)
            }
            
            // Top AI node (OpenAI - green)
            path(
                fill = SolidColor(Color(0xFF10B981)),
                stroke = null
            ) {
                moveTo(200f, 40f)
                moveToRelative(-20f, 0f)
                arcToRelative(20f, 20f, 0f, isMoreThanHalf = true, isPositiveArc = true, 40f, 0f)
                arcToRelative(20f, 20f, 0f, isMoreThanHalf = true, isPositiveArc = true, -40f, 0f)
            }
            
            // Connection line to top
            path(
                fill = null,
                stroke = SolidColor(primaryContainer),
                strokeLineWidth = 4f
            ) {
                moveTo(200f, 60f)
                lineTo(200f, 90f)
            }
            
            // Right AI node (Anthropic - orange)
            path(
                fill = SolidColor(Color(0xFFF59E0B)),
                stroke = null
            ) {
                moveTo(310f, 120f)
                moveToRelative(-20f, 0f)
                arcToRelative(20f, 20f, 0f, isMoreThanHalf = true, isPositiveArc = true, 40f, 0f)
                arcToRelative(20f, 20f, 0f, isMoreThanHalf = true, isPositiveArc = true, -40f, 0f)
            }
            
            // Connection line to right
            path(
                fill = null,
                stroke = SolidColor(primaryContainer),
                strokeLineWidth = 4f
            ) {
                moveTo(290f, 120f)
                lineTo(230f, 120f)
            }
            
            // Bottom AI node (Google - blue)
            path(
                fill = SolidColor(Color(0xFF3B82F6)),
                stroke = null
            ) {
                moveTo(200f, 200f)
                moveToRelative(-20f, 0f)
                arcToRelative(20f, 20f, 0f, isMoreThanHalf = true, isPositiveArc = true, 40f, 0f)
                arcToRelative(20f, 20f, 0f, isMoreThanHalf = true, isPositiveArc = true, -40f, 0f)
            }
            
            // Connection line to bottom
            path(
                fill = null,
                stroke = SolidColor(primaryContainer),
                strokeLineWidth = 4f
            ) {
                moveTo(200f, 180f)
                lineTo(200f, 150f)
            }
            
            // Left AI node (Others - purple)
            path(
                fill = SolidColor(secondary),
                stroke = null
            ) {
                moveTo(90f, 120f)
                moveToRelative(-20f, 0f)
                arcToRelative(20f, 20f, 0f, isMoreThanHalf = true, isPositiveArc = true, 40f, 0f)
                arcToRelative(20f, 20f, 0f, isMoreThanHalf = true, isPositiveArc = true, -40f, 0f)
            }
            
            // Connection line to left
            path(
                fill = null,
                stroke = SolidColor(primaryContainer),
                strokeLineWidth = 4f
            ) {
                moveTo(110f, 120f)
                lineTo(170f, 120f)
            }
            
            // Center text indicator dots
            path(
                fill = SolidColor(Color.White),
                stroke = null
            ) {
                moveTo(190f, 115f)
                moveToRelative(-4f, 0f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = true, 8f, 0f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = true, -8f, 0f)
            }
            path(
                fill = SolidColor(Color.White),
                stroke = null
            ) {
                moveTo(200f, 115f)
                moveToRelative(-4f, 0f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = true, 8f, 0f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = true, -8f, 0f)
            }
            path(
                fill = SolidColor(Color.White),
                stroke = null
            ) {
                moveTo(210f, 115f)
                moveToRelative(-4f, 0f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = true, 8f, 0f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = true, -8f, 0f)
            }
            
            // Decorative connecting arcs for visual interest
            path(
                fill = null,
                stroke = SolidColor(primaryContainer),
                strokeLineWidth = 2f
            ) {
                moveTo(220f, 100f)
                arcToRelative(40f, 40f, 0f, isMoreThanHalf = false, isPositiveArc = true, 70f, 20f)
            }
            path(
                fill = null,
                stroke = SolidColor(primaryContainer),
                strokeLineWidth = 2f
            ) {
                moveTo(180f, 100f)
                arcToRelative(40f, 40f, 0f, isMoreThanHalf = false, isPositiveArc = false, -70f, 20f)
            }
            path(
                fill = null,
                stroke = SolidColor(primaryContainer),
                strokeLineWidth = 2f
            ) {
                moveTo(220f, 140f)
                arcToRelative(40f, 40f, 0f, isMoreThanHalf = false, isPositiveArc = false, 70f, -20f)
            }
            path(
                fill = null,
                stroke = SolidColor(primaryContainer),
                strokeLineWidth = 2f
            ) {
                moveTo(180f, 140f)
                arcToRelative(40f, 40f, 0f, isMoreThanHalf = false, isPositiveArc = true, -70f, -20f)
            }
        }.build()
    }

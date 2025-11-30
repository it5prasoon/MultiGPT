package com.matrix.multigpt.data.dto

import com.matrix.multigpt.data.model.DynamicTheme
import com.matrix.multigpt.data.model.ThemeMode

data class ThemeSetting(
    val dynamicTheme: DynamicTheme = DynamicTheme.OFF,
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)

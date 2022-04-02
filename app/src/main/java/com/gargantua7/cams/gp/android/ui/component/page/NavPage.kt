package com.gargantua7.cams.gp.android.ui.component.page

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * @author Gargantua7
 */
interface NavPage : Page {

    val title: String
    val icon: ImageVector

    @Composable
    fun fab() {

    }
}

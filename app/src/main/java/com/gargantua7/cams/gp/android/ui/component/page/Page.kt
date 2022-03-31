package com.gargantua7.cams.gp.android.ui.component.page

import androidx.compose.runtime.Composable

/**
 * @author Gargantua7
 */
interface Page {

    val id: String

    @Composable
    fun draw()

}

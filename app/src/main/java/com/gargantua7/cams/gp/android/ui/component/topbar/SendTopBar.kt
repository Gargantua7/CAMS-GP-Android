package com.gargantua7.cams.gp.android.ui.component.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

/**
 * @author Gargantua7
 */
interface SendTopBar : TopBar {

    fun onSend()

    @Composable
    override fun RowScope.rightComponents() {

    }

}

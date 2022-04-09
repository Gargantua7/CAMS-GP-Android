package com.gargantua7.cams.gp.android.ui.component.page

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.fab.FAB

/**
 * @author Gargantua7
 */
interface NavPage : Page, FAB {

    val title: String
    val icon: ImageVector

    @Composable
    override fun fab() {

    }

    override fun fabOnClick(context: ComposeActivity) {

    }
}

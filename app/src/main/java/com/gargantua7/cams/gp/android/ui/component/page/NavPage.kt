package com.gargantua7.cams.gp.android.ui.component.page

import androidx.compose.ui.graphics.vector.ImageVector
import com.gargantua7.cams.gp.android.ui.component.fab.FAB

/**
 * @author Gargantua7
 */
interface NavPage : Page, FAB {

    val title: String
    val icon: ImageVector
}

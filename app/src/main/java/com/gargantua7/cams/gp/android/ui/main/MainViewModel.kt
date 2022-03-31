package com.gargantua7.cams.gp.android.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.gargantua7.cams.gp.android.ui.component.bottombar.NavBottomBar
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import com.gargantua7.cams.gp.android.ui.component.topbar.SearchTopBar

/**
 * @author Gargantua7
 */
class MainViewModel : ComposeViewModel(), SearchTopBar.SearchTopBarViewModel, NavBottomBar.NavBottomBarViewModel {

    override var select by mutableStateOf(0)

    override val bottomBarItems = arrayOf(Repairs, Events, Messages, Me)

    override var value by mutableStateOf("")
}

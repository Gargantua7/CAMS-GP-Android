package com.gargantua7.cams.gp.android.ui.component.bottombar

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gargantua7.cams.gp.android.ui.component.page.NavPage

/**
 * @author Gargantua7
 */
interface NavBottomBar : BottomBar {

    val viewModel: NavBottomBarViewModel

    var navController: NavHostController

    @Composable
    override fun bottomBar() {
        navController = rememberNavController()
        BottomAppBar(backgroundColor = MaterialTheme.colors.surface) {
            viewModel.bottomBarItems.forEachIndexed { i, page ->
                BottomNavigationItem(
                    selected = viewModel.select == i,
                    onClick = {
                        viewModel.select = i
                        bottomBarItemOnClick(page)
                    },
                    icon = {
                        Icon(
                            page.icon,
                            page.title,
                            tint = if (viewModel.select == i) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
                        )
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }

    fun bottomBarItemOnClick(page: NavPage) {
        navController.navigate(page.id) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    interface NavBottomBarViewModel {
        var select: Int

        val bottomBarItems: Array<NavPage>
    }

}

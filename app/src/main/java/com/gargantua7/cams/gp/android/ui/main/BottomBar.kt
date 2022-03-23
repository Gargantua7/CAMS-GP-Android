package com.gargantua7.cams.gp.android.ui.main

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * @author Gargantua7
 */
@Composable
fun bottomAppBar(navController: NavHostController) {
    val vm = viewModel<MainViewModel>()
    BottomAppBar(backgroundColor = MaterialTheme.colors.surface) {
        item.forEachIndexed { i, page ->
            BottomNavigationItem(
                selected = vm.select == i,
                onClick = {
                    vm.select = i
                    navController.navigate(page.title) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        page.icon,
                        page.title,
                        tint = if (vm.select == i) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}

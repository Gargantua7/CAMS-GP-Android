package com.gargantua7.cams.gp.android.ui.main

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.ui.theme.CAMSGPAndroidTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private val repairViewModel by lazy { ViewModelProvider(this).get(RepairViewModel::class.java) }

    private val eventViewModel by lazy { ViewModelProvider(this).get(EventViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        draw()
    }

    private fun draw() {
        setContent {
            val nav = rememberNavController()
            CAMSGPAndroidTheme {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()
                    rememberSystemUiController().setStatusBarColor(
                        Color.Transparent,
                        darkIcons = MaterialTheme.colors.isLight
                    )
                    Surface(color = MaterialTheme.colors.background) {
                        Scaffold(
                            scaffoldState = scaffoldState,
                            topBar = { topBar() },
                            bottomBar = { bottomAppBar(nav) },
                            floatingActionButton = { item[viewModel.select].fab() },
                            floatingActionButtonPosition = FabPosition.End
                        ) {
                            NavHost(
                                navController = nav,
                                startDestination = item[viewModel.select].title,
                                modifier = Modifier.padding(it)
                            ) {
                                item.forEach { page ->
                                    composable(page.title) {
                                        page.draw()
                                    }
                                }
                            }
                            CAMSApplication.errorMsg?.let {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




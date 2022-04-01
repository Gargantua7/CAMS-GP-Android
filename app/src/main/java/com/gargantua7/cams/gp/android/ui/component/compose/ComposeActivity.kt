package com.gargantua7.cams.gp.android.ui.component.compose

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gargantua7.cams.gp.android.ui.component.bottombar.BottomBar
import com.gargantua7.cams.gp.android.ui.component.bottombar.NavBottomBar
import com.gargantua7.cams.gp.android.ui.component.swipeable.Swipeable
import com.gargantua7.cams.gp.android.ui.component.topbar.TopBar
import com.gargantua7.cams.gp.android.ui.theme.CAMSGPAndroidTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author Gargantua7
 */
abstract class ComposeActivity : AppCompatActivity() {

    abstract val viewModel: ComposeViewModel

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
                            topBar = { if (this is TopBar) topBar() },
                            bottomBar = { if (this is BottomBar) bottomBar() },
                            floatingActionButton = {
                                if (viewModel is NavBottomBar.NavBottomBarViewModel) {
                                    val vm = viewModel as NavBottomBar.NavBottomBarViewModel
                                    if (this is NavBottomBar) {
                                        vm.bottomBarItems[vm.select].fab()
                                    }
                                }
                            },
                            floatingActionButtonPosition = FabPosition.End
                        ) {

                            Box(modifier = Modifier.padding(it)) {
                                contentComponents(scaffoldState, scope)
                            }
                            if (viewModel.loading) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        viewModel.errorMsg?.let {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(it)
                            }
                            viewModel.errorMsg = null
                        }
                    }
                }
            }
        }
    }

    /**
     * Compose Activity 正文，不需要考虑 TopBar 边距
     *
     * 如果此类同样实现了以下接口，将自动添加组件
     * - [NavBottomBar]
     * - [Swipeable]
     */
    @Composable
    protected open fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        if (viewModel is NavBottomBar.NavBottomBarViewModel) {
            val vm = viewModel as NavBottomBar.NavBottomBarViewModel
            if (this is NavBottomBar) {
                NavHost(
                    navController = navController,
                    startDestination = vm.bottomBarItems[vm.select].id
                ) {
                    vm.bottomBarItems.forEach { page ->
                        composable(page.id) {
                            page.draw()
                        }
                    }
                }
            }
        }
        if (this is Swipeable) {
            swipe()
        }
    }
}

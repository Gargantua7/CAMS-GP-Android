package com.gargantua7.cams.gp.android.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.gargantua7.cams.gp.android.ui.component.bottombar.NavBottomBar
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.fab.FAB
import com.gargantua7.cams.gp.android.ui.component.photo.PhotoPreview
import com.gargantua7.cams.gp.android.ui.component.swipeable.Swipeable
import com.gargantua7.cams.gp.android.ui.component.topbar.SearchTopBar
import com.gargantua7.cams.gp.android.ui.message.MessagePollingService
import com.gargantua7.cams.gp.android.ui.search.SearchActivity
import com.gargantua7.cams.gp.android.ui.util.startPollingService
import com.gargantua7.cams.gp.android.ui.util.stopPollingService

class MainActivity : ComposeActivity(), SearchTopBar, NavBottomBar, FAB, PhotoPreview {

    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, MessagePollingService::class.java))
        startPollingService(this, 5, MessagePollingService::class.java, "messagePollingService")
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPollingService(this, MessagePollingService::class.java, "messagePollingService")
    }

    @Composable
    override fun RowScope.leftComponents() {
        Text(
            text = viewModel.bottomBarItems[viewModel.select].title,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.width(50.dp)
        )
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.bottomBarItems[viewModel.select].let {
            if (it is Swipeable) {
                it.onRefresh()
            }
        }
    }

    override fun onSearch(key: String) {
        startActivity(Intent(this, SearchActivity::class.java).apply {
            putExtra("person", true)
            putExtra("repair", true)
            putExtra("key", key)
        })
        viewModel.value = ""
    }

    @Composable
    override fun fab() {
        viewModel.bottomBarItems[viewModel.select].fab()
    }

    override fun fabOnClick(context: ComposeActivity) {
        viewModel.bottomBarItems[viewModel.select].fabOnClick(context)
    }
}




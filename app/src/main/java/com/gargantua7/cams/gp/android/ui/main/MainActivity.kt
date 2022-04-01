package com.gargantua7.cams.gp.android.ui.main

import android.content.Intent
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
import com.gargantua7.cams.gp.android.ui.component.topbar.SearchTopBar
import com.gargantua7.cams.gp.android.ui.search.SearchActivity

class MainActivity : ComposeActivity(), SearchTopBar, NavBottomBar {

    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override lateinit var navController: NavHostController

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

    override fun onSearch(key: String) {
        startActivity(Intent(this, SearchActivity::class.java).apply {
            putExtra("person", true)
            putExtra("repair", true)
            putExtra("key", key)
        })
        viewModel.value = ""
    }

}




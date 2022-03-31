package com.gargantua7.cams.gp.android.ui.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gargantua7.cams.gp.android.ui.main.MainViewModel
import com.google.accompanist.insets.statusBarsHeight

/**
 * @author Gargantua7
 */
interface TopBar {

    @Composable
    fun RowScope.leftComponents() {
    }

    @Composable
    fun RowScope.coreComponents() {
        Spacer(modifier = Modifier.weight(1f))
    }

    @Composable
    fun RowScope.rightComponents() {
    }

    @Composable
    fun topBar() {
        Column(
            modifier = Modifier.background(MaterialTheme.colors.primary)
        ) {
            Spacer(
                modifier = Modifier
                    .statusBarsHeight()
                    .fillMaxWidth()
            )
            viewModel<MainViewModel>()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(55.dp)
                    .padding(10.dp)
            ) {
                leftComponents()
                Spacer(modifier = Modifier.padding(5.dp))
                coreComponents()
                Spacer(modifier = Modifier.padding(5.dp))
                rightComponents()
            }
        }

    }
}

package com.gargantua7.cams.android.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.runtime.Composable
import com.gargantua7.cams.android.CAMSApplication
import com.gargantua7.cams.android.R

/**
 * @author Gargantua7
 */
object Event: Page() {

    override val title = CAMSApplication.context.getString(R.string.event)
    override val icon = Icons.Filled.Celebration

    @Composable
    override fun draw() {
    }
}

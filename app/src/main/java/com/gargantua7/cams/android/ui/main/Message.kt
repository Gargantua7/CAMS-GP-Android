package com.gargantua7.cams.android.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.runtime.Composable
import com.gargantua7.cams.android.R
import com.gargantua7.cams.android.ui.util.stringResource

/**
 * @author Gargantua7
 */
object Message: Page() {
    override val title = stringResource(R.string.message)
    override val icon = Icons.Filled.Message

    @Composable
    override fun draw() {

    }
}

package com.gargantua7.cams.android.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import com.gargantua7.cams.android.R
import com.gargantua7.cams.android.ui.util.stringResource

/**
 * @author Gargantua7
 */
object Me: Page() {
    override val title = stringResource(R.string.me)
    override val icon = Icons.Filled.Person

    @Composable
    override fun draw() {

    }
}

package com.gargantua7.cams.gp.android.ui.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gargantua7.cams.android.R
import com.gargantua7.cams.gp.android.ui.util.stringResource

/**
 * @author Gargantua7
 */
object Me : Page() {
    override val title = stringResource(R.string.me)
    override val icon = Icons.Filled.Person

    @Composable
    override fun draw() {
        Log.d("Fragment View CREATE", "Me")
        val user = viewModel(MainViewModel::class.java).user
        Column(
            modifier = Modifier.padding(0.dp, 2.5.dp)
        ) {
            if (user != null) {
                card(onClick = { /*TODO*/ }) {
                    Text(
                        text = "NAME",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            } else {
                card(onClick = { /*TODO*/ }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "SIGN IN / UP",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }
        }
    }

    @Composable
    override fun fab() {

    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun card(onClick: () -> Unit, content: @Composable () -> Unit) {
        Card(
            modifier = Modifier
                .padding(5.dp, 2.5.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(20.dp),
                ), onClick = onClick, content = content
        )
    }
}

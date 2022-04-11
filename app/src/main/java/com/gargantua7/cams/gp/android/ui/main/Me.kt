package com.gargantua7.cams.gp.android.ui.main

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.logic.repository.PersonRepository
import com.gargantua7.cams.gp.android.logic.repository.SecretRepository
import com.gargantua7.cams.gp.android.ui.component.compose.basicDialog
import com.gargantua7.cams.gp.android.ui.component.page.NavPage
import com.gargantua7.cams.gp.android.ui.person.SignInActivity
import com.gargantua7.cams.gp.android.ui.util.stringResource
import kotlinx.coroutines.launch

/**
 * @author Gargantua7
 */
class Me : NavPage {

    override val id: String = "Me"
    override val title = stringResource(R.string.me)
    override val icon = Icons.Filled.Person

    @Composable
    override fun draw() {
        Log.d("Fragment View CREATE", "Me")
        val user by CAMSApplication.user.observeAsState()
        val session by CAMSApplication.session.observeAsState()
        val context = LocalContext.current as MainActivity
        val scope = rememberCoroutineScope()
        Column {
            if (user != null || session != null) {
                user?.let {
                    Column {
                        Row(
                            modifier = Modifier
                                .background(MaterialTheme.colors.surface)
                                .clickable {
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = it.name,
                                    fontSize = 36.sp,
                                    color = MaterialTheme.colors.onBackground
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Row(horizontalArrangement = Arrangement.Center) {
                                    Icon(
                                        Icons.Filled.Tag,
                                        contentDescription = "id",
                                        tint = MaterialTheme.colors.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = it.username,
                                        color = MaterialTheme.colors.onSurface
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Row(horizontalArrangement = Arrangement.Center) {
                                    Icon(
                                        Icons.Filled.Groups,
                                        contentDescription = "dep",
                                        tint = MaterialTheme.colors.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = it.dep,
                                        color = MaterialTheme.colors.onSurface
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Row(horizontalArrangement = Arrangement.Center) {
                                    Icon(
                                        Icons.Filled.Badge,
                                        contentDescription = "name",
                                        tint = MaterialTheme.colors.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = it.title,
                                        color = MaterialTheme.colors.onSurface
                                    )
                                }
                            }
                        }
                    }
                    if (CAMSApplication.loading) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .fillMaxWidth()
                            .clickable {
                                context.apply {
                                    viewModel.showDialog {
                                        basicDialog(
                                            title = "Sure Logout?",
                                            confirmOnClick = {
                                                CAMSApplication.session.value = null
                                                CAMSApplication.username = null
                                                scope.launch {
                                                    SecretRepository.signOut()
                                                }
                                                scope.launch {
                                                    SecretRepository.removeSession()
                                                    PersonRepository.removeUsername()
                                                    viewModel.showSnackBar("Logout Success")
                                                }
                                            },
                                            confirmTextColor = Color.Red
                                        )
                                    }
                                }
                            }
                    ) {
                        Spacer(modifier = Modifier.width(15.dp))
                        Icon(Icons.Filled.Logout, "Logout")
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Logout",
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.padding(0.dp, 15.dp)
                        )
                    }
                }
            } else {
                Column {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .clickable {
                                context.startActivityWithMsgResult(Intent(context, SignInActivity::class.java))
                            }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth()
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
    }
}


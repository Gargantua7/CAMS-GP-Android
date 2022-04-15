package com.gargantua7.cams.gp.android.ui.main

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.logic.repository.PersonRepository
import com.gargantua7.cams.gp.android.logic.repository.SecretRepository
import com.gargantua7.cams.gp.android.ui.component.compose.IconRow
import com.gargantua7.cams.gp.android.ui.component.compose.basicDialog
import com.gargantua7.cams.gp.android.ui.component.page.NavPage
import com.gargantua7.cams.gp.android.ui.person.PersonActivity
import com.gargantua7.cams.gp.android.ui.person.PersonEditActivity
import com.gargantua7.cams.gp.android.ui.secret.SecretUpdateActivity
import com.gargantua7.cams.gp.android.ui.secret.SignInActivity
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
                                    Intent(context, PersonActivity::class.java).apply {
                                        putExtra("id", it.username)
                                        context.startActivity(this)
                                    }
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.size(15.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Spacer(modifier = Modifier.width(15.dp))
                                    Text(text = it.name, fontSize = 36.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    if (it.permission > 0) {
                                        Icon(Icons.Filled.Verified, "Verified", tint = MaterialTheme.colors.primary)
                                    }
                                }
                                IconRow(icon = Icons.Filled.Tag, text = it.username)
                                IconRow(icon = Icons.Filled.Groups, text = it.dep)
                                IconRow(icon = Icons.Filled.Badge, text = it.title)
                                IconRow(icon = Icons.Filled.Apartment, text = "${it.collage} ${it.major}")
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
                    Spacer(Modifier.height(15.dp))
                    IconRow(
                        icon = Icons.Filled.Person,
                        text = "Update Profile",
                        onClick = {
                            Intent(context, PersonEditActivity::class.java).let { i ->
                                i.putExtra("id", it.username)
                                context.startActivity(i)
                            }
                        }
                    )
                    IconRow(
                        icon = Icons.Filled.Password,
                        text = "Change Password",
                        onClick = {
                            context.startActivityWithMsgResult(Intent(context, SecretUpdateActivity::class.java))
                        }
                    )
                    IconRow(
                        icon = Icons.Filled.Logout,
                        text = "Logout",
                        onClick = {
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
                    )
                }
            } else {
                Column {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.onSurface
                        ),
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier.padding(70.dp, 30.dp, 70.dp, 30.dp),
                        contentPadding = PaddingValues(10.dp),
                        onClick = {
                            context.startActivityWithMsgResult(Intent(context, SignInActivity::class.java))
                        }
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(imageVector = Icons.Filled.Login, contentDescription = "Login", tint = Color.White)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Login", color = Color.White)
                        Spacer(modifier = Modifier.weight(1f))
                    }

                }
            }
            Spacer(Modifier.height(15.dp))
            IconRow(icon = Icons.Filled.Settings, text = "Settings", onClick = {

            })
        }
    }
}


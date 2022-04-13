package com.gargantua7.cams.gp.android.ui.secret

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.ui.component.compose.BoxTextField
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import kotlinx.coroutines.CoroutineScope

class SignInActivity : ComposeActivity(), BackTopBar {

    override val viewModel by lazy { ViewModelProvider(this).get(SignInViewModel::class.java) }

    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = "Login",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        val focus = LocalFocusManager.current
        Column(
            modifier = Modifier.padding(10.dp, 5.dp)
        ) {
            BoxTextField(
                text = viewModel.username,
                placeholder = "Username",
                leadIcon = Icons.Filled.AccountBox,
                trailingIcon = {
                    if (viewModel.username.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.username = "" }
                        ) {
                            Icon(
                                Icons.Filled.Clear,
                                "Clear"
                            )
                        }
                    }
                },
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
                onValueChange = {
                    viewModel.username = it
                }
            )
            BoxTextField(
                text = viewModel.password,
                placeholder = "Password",
                leadIcon = Icons.Filled.Password,
                trailingIcon = {
                    if (viewModel.password.isNotEmpty()) {
                        if (viewModel.passwordVisibility) {
                            IconButton(onClick = { viewModel.passwordVisibility = false }) {
                                Icon(
                                    imageVector = Icons.Filled.VisibilityOff,
                                    contentDescription = "VisibilityOFF"
                                )
                            }
                        } else {
                            IconButton(onClick = { viewModel.passwordVisibility = true }) {
                                Icon(
                                    imageVector = Icons.Filled.Visibility,
                                    contentDescription = "VisibilityON"
                                )
                            }
                        }
                    }
                },
                keyboardType = KeyboardType.Password,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focus.clearFocus()
                        if (
                            !viewModel.loading &&
                            viewModel.username.isNotBlank() &&
                            viewModel.password.isNotBlank()
                        ) viewModel.login()
                    }
                ),
                imeAction = ImeAction.Done,
                visualTransformation =
                if (viewModel.passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation('*'),
                onValueChange = {
                    viewModel.password = it
                }
            )
            button()
            if (viewModel.success) {
                setResult(RESULT_OK, Intent().putExtra("msg", "Login Success"))
                finish()
            }
        }
    }


    @Composable
    fun button() {
        val focus = LocalFocusManager.current
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                disabledBackgroundColor = MaterialTheme.colors.secondary
            ),
            enabled = !viewModel.loading && viewModel.username.isNotBlank() && viewModel.password.isNotBlank(),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.padding(0.dp, 5.dp),
            contentPadding = PaddingValues(10.dp),
            onClick = {
                focus.clearFocus()
                viewModel.login()
            }
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Filled.Login, contentDescription = "Login")
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Login")
            Spacer(modifier = Modifier.weight(1f))
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface
            ),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.padding(0.dp, 5.dp),
            contentPadding = PaddingValues(10.dp),
            onClick = {
                startActivityWithMsgResult(Intent(this, SignUpActivity::class.java))
            }
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Filled.PersonAdd, contentDescription = "Register")
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Register")
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

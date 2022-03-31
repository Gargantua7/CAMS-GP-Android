package com.gargantua7.cams.gp.android.ui.person

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
            textField(
                text = viewModel.username,
                placeholder = "Username",
                leadIcon = {
                    Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "Username")
                },
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
            textField(text = viewModel.password,
                placeholder = "Password",
                leadIcon = {
                    Icon(imageVector = Icons.Filled.Password, contentDescription = "Password")
                },
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
                finish()
            }
        }
    }

    @Composable
    fun textField(
        text: String,
        placeholder: String,
        leadIcon: @Composable () -> Unit,
        trailingIcon: @Composable () -> Unit,
        keyboardType: KeyboardType,
        imeAction: ImeAction,
        keyboardActions: KeyboardActions = KeyboardActions(),
        visualTransformation: VisualTransformation = VisualTransformation.None,
        onValueChange: (String) -> Unit
    ) {
        TextField(
            value = text,
            placeholder = { Text(text = placeholder) },
            leadingIcon = { leadIcon() },
            trailingIcon = { trailingIcon() },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            shape = RoundedCornerShape(5.dp),
            visualTransformation = visualTransformation,
            onValueChange = { onValueChange(it) },
            readOnly = viewModel.loading,
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth()
        )
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
    }
}

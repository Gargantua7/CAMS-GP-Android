package com.gargantua7.cams.gp.android.ui.secret

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.ui.component.bottombar.BottomBar
import com.gargantua7.cams.gp.android.ui.component.compose.BoxTextField
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.resizable.Resizable
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import com.gargantua7.cams.gp.android.ui.person.PersonBaseInfoPage
import com.gargantua7.cams.gp.android.ui.util.matchPassword
import com.gargantua7.cams.gp.android.ui.util.matchUsername
import kotlinx.coroutines.CoroutineScope

class SignUpActivity : ComposeActivity(), BackTopBar, Resizable, BottomBar {

    override val viewModel by lazy { ViewModelProvider(this).get(SignUpViewModel::class.java) }

    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = stringResource(id = R.string.reg),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        if (viewModel.success) {
            setResult(
                RESULT_OK,
                Intent().putExtra(
                    "msg",
                    "${stringResource(id = R.string.reg)} ${stringResource(id = R.string.success)}"
                )
            )
            finish()
        }
        super.contentComponents(scaffoldState, scope)
        Column(
            Modifier
                .padding(10.dp, 0.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Username()
        }
    }

    @Composable
    fun Username() {
        BoxTextField(
            text = viewModel.username,
            onValueChange = { viewModel.username = it },
            placeholder = stringResource(id = R.string.username),
            leadIcon = Icons.Filled.AccountBox,
            keyboardType = KeyboardType.Number,
            isError = viewModel.username.isNotEmpty() && !matchUsername(viewModel.username) || viewModel.usernameAvailable == false,
        )
        if (matchUsername(viewModel.username)) {
            viewModel.checkUsernameAvailable()
            if (viewModel.usernameAvailable == null) {
                Text(
                    text = "确认用户名可用中...",
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.secondary
                )
            } else if (viewModel.usernameAvailable!!) {
                Password()
            } else {
                Text(
                    text = "用户名已被使用",
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }
        }
    }

    @Composable
    fun Password() {
        BoxTextField(
            text = viewModel.password,
            onValueChange = { viewModel.password = it },
            placeholder = "密码",
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
            isError = viewModel.password.isNotEmpty() && !matchPassword(viewModel.password),
            visualTransformation =
            if (viewModel.passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation('*')
        )
        if (matchPassword(viewModel.password)) ConfirmPassword()
        else {
            Text(
                text = stringResource(id = R.string.pwd_tip),
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSurface
            )
        }
    }

    @Composable
    fun ConfirmPassword() {
        BoxTextField(
            text = viewModel.confirmPassword,
            onValueChange = { viewModel.confirmPassword = it },
            placeholder = "确认密码",
            leadIcon = Icons.Filled.Password,
            trailingIcon = {
                if (viewModel.confirmPassword.isNotEmpty()) {
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
            isError = viewModel.password.isNotEmpty() && viewModel.password != viewModel.confirmPassword,
            visualTransformation =
            if (viewModel.passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation('*')
        )
        if (viewModel.password == viewModel.confirmPassword) {
            PersonBaseInfoPage(viewModel.baseInfoViewModel, this).draw()
        }
    }


    @Composable
    override fun bottomBar() {
        val baseVM = viewModel.baseInfoViewModel
        if (baseVM.name.isNotEmpty() && baseVM.sex != null && baseVM.major != null) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onSurface
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 5.dp),
                contentPadding = PaddingValues(10.dp),
                onClick = {
                    viewModel.signUp()
                }
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Filled.PersonAdd, contentDescription = "Register")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = stringResource(id = R.string.reg))
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

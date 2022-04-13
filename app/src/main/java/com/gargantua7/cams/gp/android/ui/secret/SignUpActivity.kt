package com.gargantua7.cams.gp.android.ui.secret

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.ui.component.bottombar.BottomBar
import com.gargantua7.cams.gp.android.ui.component.compose.BoxTextField
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.resizable.Resizable
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import com.gargantua7.cams.gp.android.ui.util.matchPassword
import com.gargantua7.cams.gp.android.ui.util.matchPhone
import com.gargantua7.cams.gp.android.ui.util.matchUsername
import com.gargantua7.cams.gp.android.ui.util.matchWechat
import kotlinx.coroutines.CoroutineScope

class SignUpActivity : ComposeActivity(), BackTopBar, Resizable, BottomBar {

    override val viewModel by lazy { ViewModelProvider(this).get(SignUpViewModel::class.java) }

    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = "Register",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        if (viewModel.success) {
            setResult(RESULT_OK, Intent().putExtra("msg", "Registered successfully"))
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
            placeholder = "Username",
            leadIcon = Icons.Filled.AccountBox,
            keyboardType = KeyboardType.Number,
            isError = viewModel.username.isNotEmpty() && !matchUsername(viewModel.username) || viewModel.usernameAvailable == false,
        )
        if (matchUsername(viewModel.username)) {
            viewModel.checkUsernameAvailable()
            if (viewModel.usernameAvailable == null) {
                Text(
                    text = "Checking Username availability...",
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.secondary
                )
            } else if (viewModel.usernameAvailable!!) {
                Password()
            } else {
                Text(
                    text = "Username is already taken",
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
            isError = viewModel.password.isNotEmpty() && !matchPassword(viewModel.password),
            visualTransformation =
            if (viewModel.passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation('*')
        )
        if (matchPassword(viewModel.password)) ConfirmPassword()
        else {
            Text(
                text = "Password length is 8 to 16 and must contain at least one uppercase letter, one lowercase letter amount one number",
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
            placeholder = "Confirm Password",
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
            Name()
            Sex()
            Collage()
            Tel()
        }
    }

    @Composable
    fun Name() {
        BoxTextField(
            text = viewModel.name,
            onValueChange = { viewModel.name = it },
            placeholder = "Name",
            leadIcon = Icons.Filled.Person
        )
    }

    @Composable
    fun Sex() {
        Spacer(modifier = Modifier.height(10.dp))
        Row(Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = viewModel.sex != null && !viewModel.sex!!,
                onClick = { viewModel.sex = false },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.primary
                )
            )
            Icon(Icons.Filled.Male, "Male", tint = Color.Blue)
            RadioButton(
                selected = viewModel.sex != null && viewModel.sex!!,
                onClick = { viewModel.sex = true },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.primary
                )
            )
            Icon(Icons.Filled.Female, "Female", tint = Color.Red)
        }
        Spacer(modifier = Modifier.height(10.dp))
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Collage() {
        viewModel.getCollageList()
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            BoxTextField(
                text = viewModel.collage?.name ?: "",
                placeholder = "Collage",
                leadIcon = Icons.Filled.Apartment,
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                }
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                viewModel.majorMap.keys.forEach {
                    DropdownMenuItem(onClick = {
                        viewModel.collage = it
                        viewModel.major = null
                        expanded = false
                    }) {
                        Text(text = it.name)
                    }
                }
            }
        }
        if (viewModel.collage != null) Major()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Major() {
        viewModel.getMajorList()
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            BoxTextField(
                text = viewModel.major?.name ?: "",
                placeholder = "Major",
                leadIcon = Icons.Filled.Workspaces,
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                }
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                viewModel.majorMap[viewModel.collage]?.forEach {
                    DropdownMenuItem(onClick = {
                        viewModel.major = it
                        expanded = false
                    }) {
                        Text(text = it.name)
                    }
                }
            }
        }
    }

    @Composable
    fun Tel() {
        BoxTextField(
            text = viewModel.phone,
            onValueChange = { viewModel.phone = it },
            placeholder = "Phone (Not Required)",
            leadIcon = Icons.Filled.Phone,
            isError = !matchPhone(viewModel.phone)
        )
        BoxTextField(
            text = viewModel.wechat,
            onValueChange = { viewModel.wechat = it },
            placeholder = "Wechat (Not Required)",
            leadIcon = Icons.Filled.Message,
            isError = !matchWechat(viewModel.wechat)
        )
    }

    @Composable
    override fun bottomBar() {
        if (viewModel.name.isNotEmpty() && viewModel.sex != null && viewModel.major != null) {
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
                Text(text = "Register")
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

package com.gargantua7.cams.gp.android.ui.secret

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PublishedWithChanges
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import com.gargantua7.cams.gp.android.ui.util.matchPassword
import kotlinx.coroutines.CoroutineScope

class SecretUpdateActivity : ComposeActivity(), BackTopBar, Resizable, BottomBar {

    override val viewModel by lazy { ViewModelProvider(this).get(SecretUpdateViewModel::class.java) }


    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = "更改密码",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        if (viewModel.success) {
            Intent().let {
                it.putExtra("msg", "密码更新成功")
                setResult(RESULT_OK, it)
            }
            finish()
        }
        Column(
            modifier = Modifier.padding(10.dp, 5.dp)
        ) {
            Password(
                text = viewModel.origin,
                onValueChange = { viewModel.origin = it },
                placeholder = "原密码",
            )
            Password(
                text = viewModel.new,
                onValueChange = { viewModel.new = it },
                placeholder = "新密码",
                isError = viewModel.new.isNotEmpty() && !matchPassword(viewModel.new),
                topic = true
            )
            Password(
                text = viewModel.confirm,
                onValueChange = { viewModel.confirm = it },
                placeholder = "确认密码",
                isError = viewModel.confirm.isNotEmpty() && viewModel.confirm != viewModel.new
            )
        }
    }

    @Composable
    override fun bottomBar() {
        if (matchPassword(viewModel.new) && viewModel.new == viewModel.confirm) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onSurface
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 5.dp),
                contentPadding = PaddingValues(10.dp),
                onClick = {
                    viewModel.submit()
                }
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Filled.PublishedWithChanges, contentDescription = "Publish")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "更改")
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

    @Composable
    fun Password(
        text: String,
        onValueChange: (String) -> Unit,
        placeholder: String,
        isError: Boolean = false,
        topic: Boolean = false
    ) {
        BoxTextField(
            text = text,
            onValueChange = onValueChange,
            placeholder = placeholder,
            leadIcon = Icons.Filled.Password,
            trailingIcon = {
                if (text.isNotEmpty()) {
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
            isError = isError,
            visualTransformation =
            if (viewModel.passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation('*')
        )
        if (topic && !matchPassword(text)) {
            Text(
                text = stringResource(id = R.string.pwd_tip),
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

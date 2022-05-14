package com.gargantua7.cams.gp.android.ui.repair

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.photo.MultipartPicker
import com.gargantua7.cams.gp.android.ui.component.photo.PhotoPreview
import com.gargantua7.cams.gp.android.ui.component.resizable.Resizable
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import com.gargantua7.cams.gp.android.ui.component.topbar.SendTopBar
import com.gargantua7.cams.gp.android.ui.secret.SignInActivity
import kotlinx.coroutines.CoroutineScope


class NewRepairActivity : ComposeActivity(), BackTopBar, SendTopBar, Resizable, MultipartPicker, PhotoPreview {

    override val viewModel by lazy { ViewModelProvider(this).get(NewRepairViewModel::class.java) }

    override fun onSend() {
        if (CAMSApplication.session.value == null) {
            startActivityWithMsgResult(Intent(this, SignInActivity::class.java))
        } else viewModel.send()
    }

    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = stringResource(R.string.Create_new_Repair),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            color = Color.White
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        if (viewModel.success) {
            setResult(RESULT_OK, Intent().putExtra("msg", "发布成功"))
            finish()
        }
        Column(modifier = Modifier.background(MaterialTheme.colors.surface)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = viewModel.title,
                    onValueChange = { viewModel.title = it },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.take_title),
                            color = MaterialTheme.colors.secondary,
                            fontSize = 24.sp
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
                if (viewModel.title.isNotEmpty()) {
                    Text(
                        text = "${viewModel.title.length}/20",
                        color = if (viewModel.title.length <= 20) MaterialTheme.colors.secondary else Color.Red
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            Divider()
            TextField(
                value = viewModel.content,
                onValueChange = { viewModel.content = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.take_content),
                        color = MaterialTheme.colors.secondary
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .height(100.dp)
            ) {
                viewModel.pics.forEachIndexed { i, pic ->
                    Image(
                        pic.asImageBitmap(),
                        "pic-$i",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                            .combinedClickable(
                                onClick = {
                                    viewModel.bitmap = pic
                                },
                                onLongClick = {
                                    viewModel.pics.removeAt(i)
                                }
                            )
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val multipartPicker = this@NewRepairActivity.multipartPicker
                IconButton(
                    onClick = {
                        multipartPicker.launch("image/*")
                    }
                ) {
                    Icon(Icons.Filled.Image, "Image")
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = stringResource(id = R.string.privete))
                Checkbox(
                    checked = viewModel.private,
                    onCheckedChange = { viewModel.private = it },
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                )
            }
        }
    }
}

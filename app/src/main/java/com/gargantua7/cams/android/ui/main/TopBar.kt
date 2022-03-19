package com.gargantua7.cams.android.ui.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gargantua7.cams.android.CAMSApplication
import com.google.accompanist.insets.statusBarsHeight

/**
 * @author Gargantua7
 */
@Composable
fun topBar() {
    Column(
        modifier = Modifier.background(MaterialTheme.colors.primary)
    ) {
        Spacer(
            modifier = Modifier
                .statusBarsHeight()
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .height(55.dp)
                .padding(10.dp)
        ) {
            val vm = viewModel<MainViewModel>()
            Row {
                Text(
                    text = item[vm.select].title,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.width(50.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0, 0, 0, 55),
                            shape = RoundedCornerShape(30.dp)
                        )
                        .padding(10.dp, 7.dp)
                ) {
                    Icon(Icons.Filled.Search, "Search", tint = Color.White)
                    val focus = LocalFocusManager.current
                    BasicTextField(
                        value = vm.value,
                        onValueChange = {
                            vm.value = it
                        },
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                        singleLine = true,
                        cursorBrush = SolidColor(MaterialTheme.colors.primaryVariant),
                        keyboardActions = KeyboardActions {
                            Toast.makeText(CAMSApplication.context, vm.value, Toast.LENGTH_SHORT).show()
                            focus.clearFocus()
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        modifier = Modifier.weight(1f)
                    ) {
                        if (vm.value.isEmpty()) {
                            Text(
                                "Search",
                                fontSize = 16.sp,
                                color = Color.LightGray,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else it()
                    }
                    if (vm.value.isNotEmpty()) {
                        IconButton(
                            onClick = { vm.value = "" },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                Icons.Filled.Clear,
                                "Clear",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

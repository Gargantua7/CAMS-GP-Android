package com.gargantua7.cams.gp.android.ui.component.topbar

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author Gargantua7
 */
interface SearchTopBar : TopBar {

    val viewModel: SearchTopBarViewModel

    fun onSearch(key: String)

    fun onClear() {
        viewModel.value = ""
    }

    @Composable
    override fun RowScope.coreComponents() {
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
                value = viewModel.value,
                onValueChange = {
                    viewModel.value = it
                },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.primaryVariant),
                keyboardActions = KeyboardActions {
                    onSearch(viewModel.value)
                    focus.clearFocus()
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                modifier = Modifier.weight(1f)
            ) {
                if (viewModel.value.isEmpty()) {
                    Text(
                        "Search",
                        fontSize = 16.sp,
                        color = Color.LightGray,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else it()
            }
            if (viewModel.value.isNotEmpty()) {
                IconButton(
                    onClick = { onClear() },
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

    interface SearchTopBarViewModel {
        var value: String
    }

}

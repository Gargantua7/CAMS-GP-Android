package com.gargantua7.cams.gp.android.ui.component.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gargantua7.cams.gp.android.ui.util.clearFocusOnKeyboardDismiss
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Gargantua7
 */
@Composable
fun IconRow(
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth().run {
                onClick?.let {
                    clickable { it() }
                } ?: this
            }
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Icon(icon, "Icon")
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(0.dp, 15.dp)
        )
    }
}

@Composable
fun IconRow(
    icon: ImageVector,
    hint: String,
    text: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth().run {
                onClick?.let {
                    clickable { it() }
                } ?: this
            }
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Icon(icon, "Icon")
        Spacer(modifier = Modifier.width(10.dp))
        Column(Modifier.padding(0.dp, 10.dp)) {
            Text(text = hint, color = MaterialTheme.colors.secondary, fontSize = 12.sp)
            Text(
                text = text,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun IconTextField(
    value: String,
    onValueChange: (String) -> Unit = {},
    label: String,
    icon: ImageVector,
    maxLines: Int = 1,
    maxWords: Int = -1,
    onClick: (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            if (maxWords < 0 || value.length <= maxWords)
                Text(text = label, color = MaterialTheme.colors.secondary)
        },
        leadingIcon = {
            Icon(
                icon, "Icon",
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp)
            )
        },
        trailingIcon = {
            if (maxWords >= 0 && value.isNotEmpty()) {
                Text(
                    text = "${value.length}/$maxWords",
                    color = if (value.length <= maxWords) MaterialTheme.colors.secondary else Color.Red,
                    modifier = Modifier.padding(0.dp, 0.dp, 5.dp, 0.dp)
                )
            }
        },
        maxLines = maxLines,
        singleLine = maxLines == 1,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledTextColor = MaterialTheme.colors.onSurface,
            disabledIndicatorColor = Color.Transparent
        ),
        readOnly = onClick != null,
        enabled = onClick == null,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss()
            .run {
                onClick?.let {
                    clickable { it() }
                } ?: this
            }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxTextField(
    text: String,
    placeholder: String,
    onValueChange: (String) -> Unit = {},
    leadIcon: ImageVector,
    trailingIcon: @Composable () -> Unit = {
        if (!readOnly && text.isNotEmpty()) {
            IconButton(
                onClick = { onValueChange("") }
            ) {
                Icon(
                    Icons.Filled.Clear,
                    "Clear"
                )
            }
        }
    },
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions(),
    readOnly: Boolean = false,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    TextField(
        value = text,
        placeholder = { Text(text = placeholder) },
        onValueChange = { onValueChange(it) },
        leadingIcon = { Icon(leadIcon, "icon") },
        trailingIcon = { trailingIcon() },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            errorIndicatorColor = Color.Red,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        shape = RoundedCornerShape(5.dp),
        visualTransformation = visualTransformation,
        readOnly = readOnly,
        isError = isError,
        modifier = Modifier
            .padding(0.dp, 5.dp)
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    coroutineScope.launch {
                        delay(200)
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            }
//            .focusTarget()
    )
}

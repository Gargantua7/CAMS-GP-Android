package com.gargantua7.cams.gp.android.ui.component.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gargantua7.cams.gp.android.ui.util.clearFocusOnKeyboardDismiss

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
    onClick: (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label, color = MaterialTheme.colors.secondary)
        },
        leadingIcon = {
            Icon(
                icon, "Icon",
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp)
            )
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

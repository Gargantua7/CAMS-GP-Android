package com.gargantua7.cams.gp.android.ui.component.compose

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * @author Gargantua7
 */
/**
 * 基础标题按钮 Dialog
 *
 * @sample basicDialogSimple()
 *
 * @param onDismissRequest 当用户尝试通过单击外部或按下后退按钮来关闭对话框时执行。 正常执行后 Dialog 会自动关闭
 * @param title Dialog 标题
 * @param confirmText 确定按钮提示文
 * @param confirmOnClick 用户点击确定按钮时执行。 正常执行后 Dialog 会自动关闭
 */
@Composable
fun ComposeViewModel.basicDialog(
    title: String,
    onDismissRequest: () -> Unit = {},
    confirmText: String = "Sure",
    confirmTextColor: Color = MaterialTheme.colors.primary,
    confirmOnClick: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
            dialog = null
        },
        title = { Text(text = title) },
        confirmButton = {
            TextButton(onClick = {
                confirmOnClick()
                dialog = null
            }) {
                Text(text = confirmText, color = confirmTextColor)
            }
        },
    )
}

@Composable
fun ComposeViewModel.basicDialogSimple() {
    showDialog {
        basicDialog(
            onDismissRequest = { /*TODO*/ },
            title = "Title",
            confirmText = "Sure"
        ) {
            /*TODO*/
        }
    }
}

package com.gargantua7.cams.gp.android.ui.component.page

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.LocalMsg
import com.gargantua7.cams.gp.android.ui.message.MessageActivity
import com.gargantua7.cams.gp.android.ui.util.format

/**
 * @author Gargantua7
 */
abstract class MsgPage(viewModel: ListPageViewModel<LocalMsg>) : ListPage<LocalMsg>(viewModel) {

    override fun itemOnClick(item: LocalMsg, context: Context) {
        context.apply {
            Intent(this, MessageActivity::class.java).let {
                it.putExtra("id", if (CAMSApplication.username == item.sender) item.recipient else item.sender)
                it.putExtra("op", item.opName)
                startActivity(it)
            }
        }
    }

    @Composable
    override fun listItem(item: LocalMsg) {
        val context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth()
                .clickable { itemOnClick(item, context) }
        ) {
            Column(Modifier.padding(15.dp, 10.dp)) {
                Row {
                    Text(
                        text = item.opName,
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = item.time.format(), color = MaterialTheme.colors.secondary, fontSize = 12.sp)
                }
                Text(text = item.content, color = MaterialTheme.colors.secondary, fontSize = 16.sp, maxLines = 1)
            }
        }
    }


}

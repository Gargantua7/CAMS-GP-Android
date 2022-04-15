package com.gargantua7.cams.gp.android.ui.message

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.LocalMsg
import com.gargantua7.cams.gp.android.ui.component.bottombar.BottomBar
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.resizable.Resizable
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import com.gargantua7.cams.gp.android.ui.person.PersonActivity
import com.gargantua7.cams.gp.android.ui.util.clearFocusOnKeyboardDismiss
import com.gargantua7.cams.gp.android.ui.util.format
import kotlinx.coroutines.CoroutineScope

class MessageActivity : ComposeActivity(), BackTopBar, BottomBar, Resizable {

    override val viewModel by lazy { ViewModelProvider(this).get(MessageViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val op = intent.getStringExtra("id")
        val title = intent.getStringExtra("op")
        if (op == null || title == null) finish()
        else {
            viewModel.op.value = op
            viewModel.title = title
        }
    }

    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = viewModel.title,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        val lazy by viewModel.msg.observeAsState()
        lazy?.let {
            val msg = it.collectAsLazyPagingItems()
            if (CAMSApplication.msgRefresh) {
                msg.refresh()
                CAMSApplication.msgRefresh = false
            }
            LazyColumn(reverseLayout = true) {
                item {
                    Spacer(Modifier.height(2.5.dp))
                }
                items(msg) { m ->
                    m?.let { ms -> MsgItem(ms) }
                }
                item {
                    Spacer(Modifier.height(2.5.dp))
                }
            }
        }
    }

    @Composable
    fun MsgItem(msg: LocalMsg) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 2.5.dp),
            horizontalAlignment = if (msg.sender == CAMSApplication.username) Alignment.End
            else Alignment.Start
        ) {
            Card(
                backgroundColor = if (msg.sender == CAMSApplication.username) MaterialTheme.colors.primary
                else MaterialTheme.colors.surface,
                shape = RoundedCornerShape(5.dp),
            ) {
                Column(
                    Modifier.padding(10.dp)
                ) {
                    Text(msg.content)
                }
            }
            Spacer(Modifier.height(5.dp))
            Text(msg.time.format(), fontSize = 8.sp)
        }
    }

    @Composable
    override fun RowScope.rightComponents() {
        val op by viewModel.op.observeAsState()
        IconButton(onClick = {
            Intent(this@MessageActivity, PersonActivity::class.java).let { i ->
                i.putExtra("id", op)
                startActivity(i)
            }
        }) {
            Icon(Icons.Filled.Person, "person", tint = Color.White)
        }
    }

    @Composable
    override fun bottomBar() {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.background(MaterialTheme.colors.surface)
        ) {
            TextField(
                value = viewModel.editor,
                onValueChange = { viewModel.editor = it },
                maxLines = 5,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .weight(1f)
                    .clearFocusOnKeyboardDismiss()
            )
            IconButton(
                onClick = { viewModel.send() },
                enabled = viewModel.editor.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send",
                    tint = if (viewModel.editor.isNotBlank()) MaterialTheme.colors.primary
                    else MaterialTheme.colors.secondary
                )
            }
        }
    }
}

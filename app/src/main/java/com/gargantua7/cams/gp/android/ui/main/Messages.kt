package com.gargantua7.cams.gp.android.ui.main

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Message
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.logic.model.LocalMsg
import com.gargantua7.cams.gp.android.ui.component.page.MsgPage
import com.gargantua7.cams.gp.android.ui.component.page.NavPage
import com.gargantua7.cams.gp.android.ui.message.MessagePollingService
import com.gargantua7.cams.gp.android.ui.search.SearchActivity
import com.gargantua7.cams.gp.android.ui.secret.SignInActivity
import com.gargantua7.cams.gp.android.ui.util.stringResource
import kotlinx.coroutines.flow.Flow

/**
 * @author Gargantua7
 */
class Messages(override val viewModel: MessageViewModel) : MsgPage(viewModel), NavPage {

    override val id: String = "Message"
    override val title = stringResource(R.string.message)
    override val icon = Icons.Filled.Message

    override lateinit var lazyItems: Flow<PagingData<LocalMsg>>

    @Composable
    override fun draw() {
        val user by CAMSApplication.user.observeAsState()
        val context = LocalContext.current as MainActivity
        if (user == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text("Please login first")
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onSurface
                    ),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.padding(70.dp, 30.dp, 70.dp, 30.dp),
                    contentPadding = PaddingValues(10.dp),
                    onClick = {
                        context.startActivityWithMsgResult(Intent(context, SignInActivity::class.java))
                    }
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Filled.Login, contentDescription = "Login", tint = Color.White)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Login", color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        } else {
            Log.d("Messages", "draw")
            lazyItems = viewModel.messages
            swipe()
            if (CAMSApplication.msgRefresh) {
                viewModel.refresh = true
                CAMSApplication.msgRefresh = false
            }
        }
    }


    @Composable
    override fun fab() {
        val context = LocalContext.current
        val user by CAMSApplication.user.observeAsState()
        if (user != null) {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                onClick = {
                    context.apply {
                        Intent(this, SearchActivity::class.java).let {
                            it.putExtra("person", true)
                            it.putExtra("key", "")
                            startActivity(it)
                        }
                    }
                }) {
                Icon(Icons.Filled.Add, "add")
            }
        }
    }

    override fun onRefresh() {
        CAMSApplication.context.apply {
            startService(Intent(this, MessagePollingService::class.java))
        }
        super.onRefresh()
    }
}

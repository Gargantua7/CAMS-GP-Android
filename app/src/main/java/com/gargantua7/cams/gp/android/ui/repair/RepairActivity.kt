package com.gargantua7.cams.gp.android.ui.repair

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.logic.model.*
import com.gargantua7.cams.gp.android.ui.component.bottombar.BottomBar
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import com.gargantua7.cams.gp.android.ui.search.SearchActivity
import com.gargantua7.cams.gp.android.ui.util.toIntuitive
import com.google.accompanist.insets.navigationBarsWithImePadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RepairActivity : ComposeActivity(), BackTopBar, BottomBar {

    override val viewModel by lazy { ViewModelProvider(this).get(RepairViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) finish()
        Log.d("RepairActivity onCreate", "id = $id")
        viewModel.id.value = id
    }

    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        val repair by viewModel.repair.observeAsState()
        val rs by viewModel.replies.observeAsState()
        val replies = rs?.collectAsLazyPagingItems()
        if (viewModel.fresh) {
            replies?.refresh()
            viewModel.fresh = false
        }
        repair?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    repairItem(repair = it)
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }
                replies?.let {
                    items(it) { reply ->
                        reply?.let { r -> replyItem(r) }
                    }
                    if (it.itemCount == 0) {
                        item {
                            Text(
                                text = "No Reply Yet",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.secondary,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                } ?: item {
                    Text(
                        text = "No Reply Yet",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } ?: run {

        }
    }

    @Composable
    override fun bottomBar() {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .navigationBarsWithImePadding()
                .background(MaterialTheme.colors.surface)
        ) {
            TextField(
                value = viewModel.editor,
                onValueChange = { viewModel.editor = it },
                maxLines = 5,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { viewModel.sendRepair() },
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

    @Composable
    fun repairItem(repair: Repair) {
        val user by CAMSApplication.user.observeAsState()
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp, 5.dp)
            ) {
                Text(
                    text = repair.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
                Row {
                    personInfo(person = repair.initiator, 12)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = repair.id.toString(),
                        color = MaterialTheme.colors.secondary,
                        fontSize = 10.sp,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = repair.initTime.toIntuitive(),
                        fontSize = 10.sp
                    )
                }
            }
            Divider()
            Text(
                text = repair.content,
                modifier = Modifier
                    .padding(15.dp, 5.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(15.dp, 0.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.run {
                        user?.let { u ->
                            if (u.permission > 1 && u.depId == 1) {
                                this.clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    context.run {
                                        Intent(this, SearchActivity::class.java).let {
                                            it.putExtra("person", true)
                                            it.putExtra("ps", PersonSearcher(depId = 1))
                                            startActivity(it)
                                        }
                                    }
                                }
                            } else this
                        } ?: this
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.PendingActions,
                        contentDescription = "principal",
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Principal:", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(5.dp))
                    repair.principal?.let { p -> personInfo(person = p) }
                    if (repair.principal == null) Text(text = "Unassigned", fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.run {
                        user?.let { u ->
                            if ((u.permission > 1 && u.depId == 1) ||
                                u.username == repair.initiator.username ||
                                u.username == repair.principal?.username
                            ) {
                                this.clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    viewModel.stateChangeDialog = true
                                }
                            } else this
                        } ?: this
                    }
                ) {
                    dialog()
                    Text(
                        text = if (repair.state) "OPEN" else "CLOSE",
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        Icons.Filled.Lens,
                        "Lens",
                        tint = if (repair.state) Color.Green else Color.Red,
                        modifier = Modifier.size(11.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    @Composable
    fun dialog() {
        if (viewModel.stateChangeDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.stateChangeDialog = false },
                title = { Text(text = "Change State to ${if (viewModel.repair.value?.state == true) "CLOSE" else "OPEN"}") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.changeState()
                        viewModel.stateChangeDialog = false
                    }) {
                        Text(text = "Sure")
                    }
                },
            )
        }
    }

    @Composable
    fun replyItem(reply: Reply) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp, 15.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    personInfo(person = reply.sender, nameSize = 16)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = reply.time.toIntuitive(),
                        fontSize = 10.sp,
                        color = MaterialTheme.colors.secondary
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                if (reply is NormalReply) {
                    when (reply.type) {
                        0 -> normalItem(text = reply.content)
                        1 -> stateItem(state = reply.content)
                    }
                } else if (reply is PersonReply) {
                    assignItem(principal = reply.content)
                }
            }
            Divider()

        }
    }

    @Composable
    fun normalItem(text: String) {
        Text(text = text)
    }

    @Composable
    fun stateItem(state: String) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Change State to ", fontSize = 12.sp)
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                Icons.Filled.Lens,
                "Lens",
                tint = if (state == "Open") Color.Green else Color.Red,
                modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = if (state == "Open") "OPEN" else "CLOSE",
                fontSize = 12.sp,
                color = MaterialTheme.colors.onBackground
            )
        }
    }

    @Composable
    fun assignItem(principal: Person) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Assigned Principal to", fontSize = 12.sp)
            Spacer(modifier = Modifier.width(5.dp))
            personInfo(person = principal)
        }
    }

    @Composable
    fun errorPage() {
        val vm = viewModel(RepairViewModel::class.java)
        val scope = rememberCoroutineScope()
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (vm.loading) {
                CircularProgressIndicator()
            } else if (vm.networkError) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = {
                                scope.launch {
                                    vm.id.value?.let { vm.getRepair(it) }
                                }
                            })
                ) {
                    Icon(
                        Icons.Filled.WifiOff,
                        "NetworkError",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = stringResource(R.string.network_error),
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = stringResource(R.string.press_retry),
                        color = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }
        }
    }

    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = "Repair",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            color = Color.White
        )
    }

    @Composable
    fun personInfo(person: Person, nameSize: Int = 12) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(interactionSource = MutableInteractionSource(), indication = null) {

            }
        ) {
            Text(
                text = person.name,
                fontSize = nameSize.sp,
                color = MaterialTheme.colors.onBackground
            )
            if (person.permission > 0) {
                Icon(Icons.Filled.Verified, "V", Modifier.size(nameSize.dp), MaterialTheme.colors.primary)
            }
        }
        Spacer(modifier = Modifier.size(5.dp))
        if (person.permission > 0) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                if (person.permission in 1..3) {
                    Text(
                        text = person.dep,
                        fontSize = 10.sp,
                        color = MaterialTheme.colors.secondary
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
                Text(
                    text = person.title,
                    fontSize = 10.sp,
                    color = MaterialTheme.colors.secondary
                )
            }
        } else {
            Text(
                text = person.username,
                fontSize = 10.sp,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

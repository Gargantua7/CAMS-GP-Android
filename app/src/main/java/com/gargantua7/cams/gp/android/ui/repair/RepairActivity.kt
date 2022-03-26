package com.gargantua7.cams.gp.android.ui.repair

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.logic.model.Person
import com.gargantua7.cams.gp.android.logic.model.Repair
import com.gargantua7.cams.gp.android.ui.theme.CAMSGPAndroidTheme
import com.gargantua7.cams.gp.android.ui.util.toIntuitive
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

class RepairActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(RepairViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) finish()
        Log.d("RepairActivity onCreate", "id = $id")
        viewModel.id.value = id
        draw()
    }

    private fun draw() {
        setContent {
            CAMSGPAndroidTheme {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    val vm = viewModel(RepairViewModel::class.java)
                    val repair by vm.repair.observeAsState()
                    rememberSystemUiController().setStatusBarColor(
                        Color.Transparent,
                        darkIcons = MaterialTheme.colors.isLight
                    )
                    Surface(color = MaterialTheme.colors.background) {
                        val scaffoldState = rememberScaffoldState()
                        val scope = rememberCoroutineScope()
                        val focus = LocalFocusManager.current
                        Scaffold(
                            scaffoldState = scaffoldState,
                            topBar = { topBar() }
                        ) {
                            vm.errorMsg?.let {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(it)
                                }
                            }
                            repair?.let {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    item {
                                        repairItem(repair = it)
                                    }
                                }
                            } ?: run {

                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun repairItem(repair: Repair) {
        val user by CAMSApplication.user.observeAsState()
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
                        text = repair.updateTime.toIntuitive(),
                        fontSize = 10.sp,
                        color = MaterialTheme.colors.onBackground
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

                                }
                            } else this
                        } ?: this
                    }
                ) {
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
                Row {
                    IconButton(onClick = { finish() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Repair",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        color = Color.White
                    )
                }
            }
        }
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

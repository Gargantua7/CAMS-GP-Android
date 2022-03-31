package com.gargantua7.cams.gp.android.ui.search

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.logic.model.PersonSearcher
import com.gargantua7.cams.gp.android.ui.theme.CAMSGPAndroidTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

class SearchActivity : ComponentActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    val personSearchViewModel by lazy { ViewModelProvider(this).get(PersonSearchViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        intent.let {
            viewModel.hasPerson = it.getBooleanExtra("person", false)
            it.getSerializableExtra("ps")?.let { ps ->
                Log.d("ps", ps.toString())
                personSearchViewModel.searcher.value = (ps as PersonSearcher)
                Log.d("ob", personSearchViewModel.searcher.value?.toString() ?: "")
            }
            viewModel.hasRepair = it.getBooleanExtra("repair", false)
            //it.getSerializableExtra("rs").let { rs -> viewModel.repairSearcher = rs as RepairSearcher }
            viewModel.picker = it.getBooleanExtra("picker", false)
        }
        draw()
    }

    fun draw() {
        setContent {
            CAMSGPAndroidTheme {
                ProvideWindowInsets(consumeWindowInsets = false, windowInsetsAnimationsEnabled = true) {
                    val vm = viewModel
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
                        ) { padding ->
                            vm.errorMsg?.let {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(it)
                                }
                                vm.errorMsg = null
                            }
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(padding)
                            ) {
                                PersonSearch(personSearchViewModel).draw()
                            }
                        }
                    }
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
                                if (viewModel.hasPerson) {
                                    personSearchViewModel.updatePersonSearch(viewModel.value)
                                }
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
                                onClick = {
                                    viewModel.value = ""
                                    personSearchViewModel.updatePersonSearch("")
                                },
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
}

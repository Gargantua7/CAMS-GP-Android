package com.gargantua7.cams.gp.android.ui.person

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PublishedWithChanges
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.ui.component.bottombar.BottomBar
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.resizable.Resizable
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import com.gargantua7.cams.gp.android.ui.util.matchPhone
import com.gargantua7.cams.gp.android.ui.util.matchWechat
import kotlinx.coroutines.CoroutineScope

class PersonEditActivity : ComposeActivity(), BackTopBar, Resizable, BottomBar {

    override val viewModel by lazy { ViewModelProvider(this).get(PersonEditViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("id")
        if (username == null) finish()
        viewModel.id.value = username
    }

    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = "Information Editor",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        val person by viewModel.item.observeAsState()
        person?.let { viewModel.fresh(it) }
        Column(
            Modifier
                .padding(10.dp, 0.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            PersonBaseInfoPage(viewModel.baseInfoViewModel, this@PersonEditActivity).apply {
                Collage()
                Tel()
            }
        }
    }

    @Composable
    override fun bottomBar() {
        val origin by viewModel.item.observeAsState()
        val bvm = viewModel.baseInfoViewModel
        val change = origin?.let {
            ((it.phone ?: "") != bvm.phone ||
                    (it.wechat ?: "") != bvm.wechat ||
                    it.majorId != bvm.major?.id)
                    && matchPhone(bvm.phone)
                    && matchWechat(bvm.wechat)
        } ?: false
        val focus = LocalFocusManager.current
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                disabledBackgroundColor = MaterialTheme.colors.secondary
            ),
            enabled = change,
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 5.dp),
            contentPadding = PaddingValues(10.dp),
            onClick = {
                focus.clearFocus()
                viewModel.submit()
            }
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Filled.PublishedWithChanges, contentDescription = "Publish")
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Submit")
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

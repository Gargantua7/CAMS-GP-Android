package com.gargantua7.cams.gp.android.ui.event

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import kotlinx.coroutines.CoroutineScope

class EventRegisteredActivity : ComposeActivity(), BackTopBar {

    companion object {
        private const val TAG = "EventRegisteredActivity"
    }

    override val viewModel by lazy { ViewModelProvider(this).get(EventRegisteredViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) finish()
        Log.d(TAG, "Ready to onCreate {id = $id}")
        viewModel.id.value = id
        Log.d(TAG, "onCreate Complete")
    }

    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        EventRegisteredPersonsPage(viewModel).draw()
    }

    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = "Registered",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

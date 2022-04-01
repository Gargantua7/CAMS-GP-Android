package com.gargantua7.cams.gp.android.ui.search

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.More
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.gargantua7.cams.gp.android.logic.model.Searcher
import kotlinx.coroutines.flow.Flow

/**
 * @author Gargantua7
 */
sealed interface SearchComponent<E : Any> {

    val viewModel: SearchComponentViewModel<E>
    val id: String
    val title: String
    val element: String
    val mode: Int

    @Composable
    fun preview() {
        Log.d("SearchComponent($id)", "Preview Fragment Ready to Draw")
        val svm = viewModel(SearchViewModel::class.java)
        val list by viewModel.preview.observeAsState()
        list?.let {
            Log.d("SearchComponent($element)", "Preview Items List Length: ${it.size}")
            if (it.isEmpty()) return@let
            LazyColumn {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        Text(text = element, fontSize = 24.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { svm.mode = mode }) {
                            Icon(imageVector = Icons.Filled.More, contentDescription = "More")
                        }
                    }
                }
                items(it) { item ->
                    listItem(item)
                }
            }
        }
        Log.d("SearchComponent($id)", "Preview Fragment Draw Completed")
    }

    @Composable
    fun list(items: LazyPagingItems<E>)

    @Composable
    fun listItem(item: E)

    @Composable
    fun draw()

    interface SearchComponentViewModel<E : Any> {
        val searcher: MutableLiveData<out Searcher<E>>

        var loading: Boolean

        val items: LiveData<Flow<PagingData<E>>?>

        val preview: LiveData<List<E>?>
    }

}

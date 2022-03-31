package com.gargantua7.cams.gp.android.ui.search

import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

/**
 * @author Gargantua7
 */
sealed interface SearchComponent<E : Any> {

    @Composable
    fun preview(items: List<E>)

    @Composable
    fun list(items: LazyPagingItems<E>)
}

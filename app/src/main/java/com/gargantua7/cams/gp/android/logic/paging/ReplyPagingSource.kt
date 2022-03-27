package com.gargantua7.cams.gp.android.logic.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gargantua7.cams.gp.android.logic.model.PersonReply
import com.gargantua7.cams.gp.android.logic.model.Reply
import com.gargantua7.cams.gp.android.logic.repository.PersonRepository
import com.gargantua7.cams.gp.android.logic.repository.ReplyRepository

/**
 * @author Gargantua7
 */
class ReplyPagingSource(private val repairId: Long) : PagingSource<Int, Reply>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Reply> {
        return try {
            val page = params.key ?: 0
            Log.d("reply paging pages", page.toString())
            val res = ReplyRepository.getReplyListByRepair(repairId, page)
            val replies = res.getOrThrow().map {
                if (it.type == 2) {
                    PersonReply(
                        id = it.id,
                        repairId = it.repairId,
                        repairTitle = it.repairTitle,
                        sender = it.sender,
                        content = PersonRepository.getPersonByUsername(it.content).getOrThrow(),
                        time = it.time
                    )
                } else it
            }
            Log.d("reply paging list size", replies.size.toString())
            val prev = if (page > 0) page - 1 else null
            val next = if (replies.size < 10) null else page + 1
            LoadResult.Page(replies, prev, next)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Reply>): Int? = null
}

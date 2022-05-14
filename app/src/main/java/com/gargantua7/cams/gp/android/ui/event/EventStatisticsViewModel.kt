package com.gargantua7.cams.gp.android.ui.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.gargantua7.cams.gp.android.logic.repository.EventRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import kotlinx.coroutines.Dispatchers

/**
 * @author Gargantua7
 */
class EventStatisticsViewModel : ComposeViewModel() {

    val id = MutableLiveData<Long>()

    val count = Transformations.switchMap(id) {
        liveData(Dispatchers.IO) {
            emit(EventRepository.count(it).getOrNull())
        }
    }

    val sex = Transformations.switchMap(id) {
        liveData(Dispatchers.IO) {
            emit(EventRepository.sexGroup(it).getOrNull())
        }
    }

    val time = Transformations.switchMap(id) {
        liveData(Dispatchers.IO) {
            emit(EventRepository.timeGroup(it).getOrNull())
        }
    }

    val collage = Transformations.switchMap(id) {
        liveData(Dispatchers.IO) {
            emit(EventRepository.collageGroup(it).getOrNull())
        }
    }

    val major = Transformations.switchMap(id) {
        liveData(Dispatchers.IO) {
            emit(EventRepository.majorGroup(it).getOrNull())
        }
    }

}

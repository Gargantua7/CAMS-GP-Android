package com.gargantua7.cams.gp.android.ui.event

import com.gargantua7.cams.gp.android.logic.model.Event
import com.gargantua7.cams.gp.android.logic.repository.EventRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ExhibitComposeViewModel

/**
 * @author Gargantua7
 */
class EventViewModel : ExhibitComposeViewModel<Event, Long>() {

    override suspend fun getItem(id: Long) = EventRepository.getEvent(id)

}

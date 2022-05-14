package com.gargantua7.cams.gp.android.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.gargantua7.cams.gp.android.logic.repository.RepairRepository
import com.gargantua7.cams.gp.android.ui.component.bottombar.NavBottomBar
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import com.gargantua7.cams.gp.android.ui.component.topbar.SearchTopBar
import kotlinx.coroutines.launch

/**
 * @author Gargantua7
 */
class MainViewModel : ComposeViewModel(), SearchTopBar.SearchTopBarViewModel, NavBottomBar.NavBottomBarViewModel {

    override var select by mutableStateOf(0)

    override val bottomBarItems =
        arrayOf(Repairs(RepairViewModel()), Events(EventViewModel()), Messages(MessageViewModel()), Me())

    override var value by mutableStateOf("")

    fun deleteRepair(id: Long) {
        viewModelScope.launch {
            val res = RepairRepository.deleteRepair(id)
            if (res.isSuccess) {
                showSnackBar("删除成功")
                (bottomBarItems[0] as Repairs).viewModel.refresh = true
            }
        }
    }
}

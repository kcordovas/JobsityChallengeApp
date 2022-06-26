package com.kevcordova.jobsitychallenge.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevcordova.jobsitychallenge.model.mappers.toShowRecyclerViewItem
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem
import com.kevcordova.jobsitychallenge.usescases.GetAllShowUseCase
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class ShowViewModel : ViewModelBase() {
    private var allShowUseCase : GetAllShowUseCase? = null

    private val _showListEvent = MutableLiveData<Event<ShowListNavigation>>()
    val showListEvent: LiveData<Event<ShowListNavigation>> get() = _showListEvent

    fun setUseCase(getAllShowUseCase: GetAllShowUseCase) {
        this.allShowUseCase = getAllShowUseCase
    }

    fun listShowAll() {
        _showListEvent.value = generateEvent(ShowListNavigation.ShowLoading)
        viewModelScope.launch {
            val list = allShowUseCase?.invoke()
            if (list.isNullOrEmpty()) {
                _showListEvent.value = generateEvent(ShowListNavigation.ShowListError(NullPointerException("List is Null or Empty")))
            } else {
                _showListEvent.value = generateEvent(ShowListNavigation.ShowListAsRecyclerViewItem(list.map { it.toShowRecyclerViewItem() }))
            }
            _showListEvent.value = generateEvent(ShowListNavigation.HideLoading)
        }
    }

    // Navigation
    sealed class ShowListNavigation {
        data class ShowListError(val throwable: Throwable) : ShowListNavigation()
        data class ShowListAsRecyclerViewItem(val showList: List<BaseRecyclerViewItem.ShowRecyclerViewItem>) : ShowListNavigation()
        object ShowLoading : ShowListNavigation()
        object HideLoading : ShowListNavigation()
    }
}
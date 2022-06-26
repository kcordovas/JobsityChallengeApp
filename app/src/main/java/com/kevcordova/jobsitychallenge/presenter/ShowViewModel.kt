package com.kevcordova.jobsitychallenge.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevcordova.jobsitychallenge.model.mappers.toShowRecyclerViewItem
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem
import com.kevcordova.jobsitychallenge.usescases.GetAllShowUseCase
import com.kevcordova.jobsitychallenge.usescases.SearchByNameShowUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class ShowViewModel : ViewModelBase() {
    private var allShowUseCase: GetAllShowUseCase? = null
    private var searchByNameShowUseCase: SearchByNameShowUseCase? = null

    private val _showListEvent = MutableLiveData<Event<ShowListNavigation>>()
    val showListEvent: LiveData<Event<ShowListNavigation>> get() = _showListEvent

    fun setUseCase(
        getAllShowUseCase: GetAllShowUseCase,
        searchByNameShowUseCase: SearchByNameShowUseCase
    ) {
        this.allShowUseCase = getAllShowUseCase
        this.searchByNameShowUseCase = searchByNameShowUseCase
    }

    fun search(titleShow: String) {
        _showListEvent.value = generateEvent(ShowListNavigation.ShowLoading)
        viewModelScope.launch {
            val searchedListDeferred =
                viewModelScope.async { searchByNameShowUseCase?.invoke(titleShow) }
            val searchedList = searchedListDeferred.await()
            if (searchedList != null) {
                if (searchedList.isEmpty()) {
                    _showListEvent.value =
                        generateEvent(ShowListNavigation.ShowListError(ArrayStoreException("Search List is empty")))
                } else {
                    _showListEvent.value =
                        generateEvent(ShowListNavigation.ShowListAsRecyclerViewItem(searchedList.map { it.toShowRecyclerViewItem() }))
                }
            } else {
                _showListEvent.value =
                    generateEvent(ShowListNavigation.ShowListError(NullPointerException("Search List is Null")))
            }
            _showListEvent.value = generateEvent(ShowListNavigation.HideLoading)
        }
    }

    fun listShowAll() {
        _showListEvent.value = generateEvent(ShowListNavigation.ShowLoading)
        viewModelScope.launch {
            val list = allShowUseCase?.invoke()
            if (list.isNullOrEmpty()) {
                _showListEvent.value =
                    generateEvent(ShowListNavigation.ShowListError(NullPointerException("List is Null or Empty")))
            } else {
                _showListEvent.value =
                    generateEvent(ShowListNavigation.ShowListAsRecyclerViewItem(list.map { it.toShowRecyclerViewItem() }))
            }
            _showListEvent.value = generateEvent(ShowListNavigation.HideLoading)
        }
    }

    // Navigation
    sealed class ShowListNavigation {
        data class ShowListError(val throwable: Throwable) : ShowListNavigation()
        data class ShowListAsRecyclerViewItem(val showList: List<BaseRecyclerViewItem.ShowRecyclerViewItem>) :
            ShowListNavigation()

        object ShowLoading : ShowListNavigation()
        object HideLoading : ShowListNavigation()
    }
}
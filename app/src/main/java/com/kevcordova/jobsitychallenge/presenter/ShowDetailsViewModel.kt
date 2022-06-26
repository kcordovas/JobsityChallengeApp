package com.kevcordova.jobsitychallenge.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevcordova.jobsitychallenge.domain.Episode
import com.kevcordova.jobsitychallenge.domain.Show
import com.kevcordova.jobsitychallenge.model.parcelables.ShowParcelable
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem
import com.kevcordova.jobsitychallenge.usescases.GetEpisodeByShowIdUseCase
import com.kevcordova.jobsitychallenge.usescases.GetShowByIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class ShowDetailsViewModel : ViewModelBase() {
    private var getShowByIdUseCase : GetShowByIdUseCase? = null
    private var getShowEpisodeListByIdUseCase : GetEpisodeByShowIdUseCase? = null

    private lateinit var _showParcelableReceived: ShowParcelable

    private val _events = MutableLiveData<Event<ShowDetailsNavigation>>()
    val showDetailEvents: LiveData<Event<ShowDetailsNavigation>> get() = _events

    fun passParcelable(showParcelable: ShowParcelable) {
        _showParcelableReceived = showParcelable
    }

    fun generateUseCase(getShowByIdUseCase: GetShowByIdUseCase, getEpisodeByShowIdUseCase: GetEpisodeByShowIdUseCase) {
        this.getShowByIdUseCase = getShowByIdUseCase
        this.getShowEpisodeListByIdUseCase = getEpisodeByShowIdUseCase
    }

    @ExperimentalCoroutinesApi
    fun buildShowDetail() {
        _events.value = generateEvent(ShowDetailsNavigation.ShowLoading)
        viewModelScope.launch {
            val showDeferred = viewModelScope.async { getShowByIdUseCase?.invoke(_showParcelableReceived.id) }
            val episodeListDeferred = viewModelScope.async { getShowEpisodeListByIdUseCase?.invoke(_showParcelableReceived.id) }

            val show = showDeferred.await()
            val episodeList = episodeListDeferred.await()

            if (show != null) {
                _events.value = generateEvent(ShowDetailsNavigation.ShowDetailHeader(show))
            } else {
                _events.value = generateEvent(ShowDetailsNavigation.ShowDetailError(
                    NullPointerException("Show Object is null")
                ))
            }

            if (episodeList.isNullOrEmpty()) {
                _events.value = generateEvent(ShowDetailsNavigation.ShowDetailError(
                    NullPointerException("Episode List is null or blank")
                ))
            } else {
                _events.value = generateEvent(
                    ShowDetailsNavigation.ShowEpisodeListOnRecyclerView(
                        generateEpisodeListRecyclerViewItems(episodeList)
                    )
                )
            }
            _events.value = generateEvent(ShowDetailsNavigation.HideLoading)
        }
    }

    private fun generateEpisodeListRecyclerViewItems(episodeList: List<Episode>) : List<BaseRecyclerViewItem> {
        val episodeListRecyclerView = mutableListOf<BaseRecyclerViewItem>()
        var seasonActual = -1
        var idTitle = 1
        for (episode in episodeList) {
            when {
                seasonActual != episode.season -> {
                    seasonActual = episode.season
                    episodeListRecyclerView.add(BaseRecyclerViewItem.Title(idTitle, seasonActual))
                    episodeListRecyclerView.add(BaseRecyclerViewItem.EpisodeRecyclerViewItem(
                        episode.id,
                        episode.name,
                        episode.numberChapter,
                        episode.season,
                        episode.imageUrl
                    ))
                    idTitle++
                }
                seasonActual == episode.season -> {
                    episodeListRecyclerView.add(BaseRecyclerViewItem.EpisodeRecyclerViewItem(
                        episode.id,
                        episode.name,
                        episode.numberChapter,
                        episode.season,
                        episode.imageUrl
                    ))
                }
            }
        }
        return episodeListRecyclerView
    }

    sealed class ShowDetailsNavigation {
        data class ShowDetailError(val throwable: Throwable) : ShowDetailsNavigation()
        data class ShowEpisodeListOnRecyclerView(val episodeRecyclerViewItem: List<BaseRecyclerViewItem>) : ShowDetailsNavigation()
        data class ShowDetailHeader(val show: Show) : ShowDetailsNavigation()
        object ShowLoading : ShowDetailsNavigation()
        object HideLoading : ShowDetailsNavigation()
    }

}
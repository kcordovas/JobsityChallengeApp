package com.kevcordova.jobsitychallenge.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevcordova.jobsitychallenge.domain.Episode
import com.kevcordova.jobsitychallenge.model.parcelables.EpisodeParcelable
import com.kevcordova.jobsitychallenge.usescases.GetEpisodeBySeasonAndChapter
import kotlinx.coroutines.launch

class EpisodeViewModel(
    private val getEpisodeBySeasonAndChapter: GetEpisodeBySeasonAndChapter
) : ViewModelBase() {

    private val _events = MutableLiveData<Event<EpisodeInfoNavigation>>()
    val episodeInfoEvents: LiveData<Event<EpisodeInfoNavigation>> get() = _events

    private var idShow: Int = -1
    private lateinit var _episodeParcelable: EpisodeParcelable

    fun addDataOfSearch(idShow: Int, episodeParcelable: EpisodeParcelable) {
        this.idShow = idShow
        this._episodeParcelable = episodeParcelable
    }

    fun searchEpisode() {
        _events.value = generateEvent(EpisodeInfoNavigation.ShowLoading)
        viewModelScope.launch {
            val episode = getEpisodeBySeasonAndChapter.invoke(
                idShow,
                _episodeParcelable.season,
                _episodeParcelable.numberChapter
            )
            _events.value = generateEvent(EpisodeInfoNavigation.ShowEpisodeDetail(episode))
            _events.value = generateEvent(EpisodeInfoNavigation.HideLoading)
        }
    }

    sealed class EpisodeInfoNavigation {
        data class ShowEpisodeDetail(val episode: Episode) : EpisodeInfoNavigation()
        object ShowLoading : EpisodeInfoNavigation()
        object HideLoading : EpisodeInfoNavigation()
    }
}
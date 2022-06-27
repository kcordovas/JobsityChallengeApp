package com.kevcordova.jobsitychallenge.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.baseandroidmodulekevcordova.extensions.bindGlideImage
import com.example.baseandroidmodulekevcordova.extensions.showShortToast
import com.kevcordova.jobsitychallenge.R
import com.kevcordova.jobsitychallenge.api.EpisodeRequest
import com.kevcordova.jobsitychallenge.api.EpisodeRetrofitDataSource
import com.kevcordova.jobsitychallenge.api.ShowRequest
import com.kevcordova.jobsitychallenge.api.ShowRetrofitDataSource
import com.kevcordova.jobsitychallenge.constants.JobsityChallengeConstants
import com.kevcordova.jobsitychallenge.data.EpisodeRepository
import com.kevcordova.jobsitychallenge.data.RemoteEpisodeDataSource
import com.kevcordova.jobsitychallenge.data.RemoteShowDataSource
import com.kevcordova.jobsitychallenge.data.ShowRepository
import com.kevcordova.jobsitychallenge.databinding.ActivityShowDetailsBinding
import com.kevcordova.jobsitychallenge.domain.Show
import com.kevcordova.jobsitychallenge.extensions.fromHtml
import com.kevcordova.jobsitychallenge.model.mappers.toParcelable
import com.kevcordova.jobsitychallenge.model.parcelables.ShowParcelable
import com.kevcordova.jobsitychallenge.presenter.Event
import com.kevcordova.jobsitychallenge.presenter.ShowDetailsViewModel
import com.kevcordova.jobsitychallenge.ui.adapters.ShowAndEpisodeHomeRecyclerViewAdapter
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem
import com.kevcordova.jobsitychallenge.ui.fragment.EpisodeInfoFullscreenFragment
import com.kevcordova.jobsitychallenge.ui.fragment.home.ShowListFragment
import com.kevcordova.jobsitychallenge.usescases.GetEpisodeByShowIdUseCase
import com.kevcordova.jobsitychallenge.usescases.GetShowByIdUseCase
import com.kevcordova.jobsitychallenge.utils.DateUtils
import com.kevcordova.jobsitychallenge.utils.FavoritePreferences
import java.util.*

class ShowDetailsActivity : AppCompatActivity() {

    companion object {
        const val PARAM_EPISODE_INFO = "PARAM_EPISODE_INFO"
        const val PARAM_SHOW_ID = "PARAM_SHOW_ID"
    }

    private val remoteShowDataSource: RemoteShowDataSource by lazy {
        ShowRetrofitDataSource(ShowRequest)
    }
    private val remoteEpisodeDataSource: RemoteEpisodeDataSource by lazy {
        EpisodeRetrofitDataSource(EpisodeRequest)
    }
    private val showRepository: ShowRepository by lazy {
        ShowRepository(remoteShowDataSource)
    }
    private val episodeRepository: EpisodeRepository by lazy {
        EpisodeRepository(remoteEpisodeDataSource)
    }
    private val getShowByIdUseCase: GetShowByIdUseCase by lazy {
        GetShowByIdUseCase(showRepository)
    }
    private val getShowEpisodeListByIdUseCase: GetEpisodeByShowIdUseCase by lazy {
        GetEpisodeByShowIdUseCase(episodeRepository)
    }

    private val viewModel by lazy {
        ShowDetailsViewModel()
    }

    private val recyclerViewAdapter = ShowAndEpisodeHomeRecyclerViewAdapter()
    private lateinit var binding: ActivityShowDetailsBinding

    private var _showParcelableReceived: ShowParcelable? = null

    // Use when identify that object is different to null
    private val showParcelableReceived: ShowParcelable get() = _showParcelableReceived!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_details)
        binding.activity = this
        _showParcelableReceived = intent.getParcelableExtra(ShowListFragment.PARAM_SHOW_ITEM)
        binding.episodesListRecyclerviewShowDetail.adapter = recyclerViewAdapter
        bindFavoriteStatus()
        manageEpisodeItemClickListener()
        if (_showParcelableReceived != null) {
            viewModel.run {
                showDetailEvents.observe(
                    this@ShowDetailsActivity,
                    Observer(this@ShowDetailsActivity::manageShowDetailsEvent)
                )
                passParcelable(showParcelableReceived)
                generateUseCase(getShowByIdUseCase, getShowEpisodeListByIdUseCase)
                buildShowDetail()
            }
        } else {
            finish()
        }
    }

    private fun buildHeaderShow(show: Show) {
        binding.run {
            imageCoverShowDetail.bindGlideImage(
                show.imageUrl,
                R.drawable.ic_round_image_placeholder,
                R.drawable.ic_round_broken_image
            )
            textTitleShowDetail.text = show.name
            textSummaryShowDetail.text = show.summary.fromHtml()
            textReleaseDateShowDetail.text =
                DateUtils.convertDateStringInDateFormat(show.premiered).uppercase()
            textGenresShowDetail.text =
                show.genders.joinToString(separator = JobsityChallengeConstants.DIVIDER_GENDER)
        }
    }

    private fun manageShowDetailsEvent(events: Event<ShowDetailsViewModel.ShowDetailsNavigation>) {
        events.getContentIfNotHandle()?.let { nav ->
            when (nav) {
                is ShowDetailsViewModel.ShowDetailsNavigation.ShowEpisodeListOnRecyclerView -> nav.run {
                    recyclerViewAdapter.items = episodeRecyclerViewItem
                }
                is ShowDetailsViewModel.ShowDetailsNavigation.ShowDetailHeader -> nav.run {
                    buildHeaderShow(
                        show
                    )
                }
                ShowDetailsViewModel.ShowDetailsNavigation.HideLoading -> binding.progressLinearShowDetail.visibility =
                    View.GONE
                ShowDetailsViewModel.ShowDetailsNavigation.ShowLoading -> binding.progressLinearShowDetail.visibility =
                    View.VISIBLE
                is ShowDetailsViewModel.ShowDetailsNavigation.ShowDetailError -> nav.run {
                    showShortToast(throwable.message ?: "Error on Show or Episode List")
                }
            }
        }
    }

    private fun manageEpisodeItemClickListener() {
        recyclerViewAdapter.itemClickListener = { _, item, _ ->
            val episodeItem: BaseRecyclerViewItem.EpisodeRecyclerViewItem? =
                if (item is BaseRecyclerViewItem.EpisodeRecyclerViewItem) item else null
            episodeItem?.run {
                val dialogEpisodeInfoFullscreenFragment =
                    EpisodeInfoFullscreenFragment.newInstanceWithParams(
                        showParcelableReceived.id,
                        episodeItem.toParcelable()
                    )
                dialogEpisodeInfoFullscreenFragment.show(
                    supportFragmentManager.beginTransaction(),
                    EpisodeInfoFullscreenFragment.TAG
                )
            }
        }
    }

    fun addToFavoriteShow() {
        if (FavoritePreferences.isShowSavedInFavorite(showParcelableReceived.id)) {
            binding.favoriteFabShowDetail.setImageResource(R.drawable.ic_round_star_border)
            FavoritePreferences.deleteFavorite(showParcelableReceived.id)
            showShortToast(getString(R.string.msg_delete_show_favorite))
        } else {
            binding.favoriteFabShowDetail.setImageResource(R.drawable.ic_round_star)
            FavoritePreferences.saveFavoriteShow(showParcelableReceived.id)
            showShortToast(getString(R.string.msg_add_show_favorite))
        }
    }

    private fun bindFavoriteStatus() {
        if (FavoritePreferences.isShowSavedInFavorite(showParcelableReceived.id)) {
            binding.favoriteFabShowDetail.setImageResource(R.drawable.ic_round_star)
        } else {
            binding.favoriteFabShowDetail.setImageResource(R.drawable.ic_round_star_border)
        }
    }

    fun backPage() {
        finish()
    }
}
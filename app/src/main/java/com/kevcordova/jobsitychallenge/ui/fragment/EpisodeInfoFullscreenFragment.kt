package com.kevcordova.jobsitychallenge.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.baseandroidmodulekevcordova.extensions.bindGlideImage
import com.kevcordova.jobsitychallenge.R
import com.kevcordova.jobsitychallenge.api.EpisodeRequest
import com.kevcordova.jobsitychallenge.api.EpisodeRetrofitDataSource
import com.kevcordova.jobsitychallenge.data.EpisodeRepository
import com.kevcordova.jobsitychallenge.data.RemoteEpisodeDataSource
import com.kevcordova.jobsitychallenge.databinding.FragmentEpisodeInfoFullscreenBinding
import com.kevcordova.jobsitychallenge.domain.Episode
import com.kevcordova.jobsitychallenge.extensions.fromHtml
import com.kevcordova.jobsitychallenge.model.parcelables.EpisodeParcelable
import com.kevcordova.jobsitychallenge.presenter.EpisodeViewModel
import com.kevcordova.jobsitychallenge.presenter.Event
import com.kevcordova.jobsitychallenge.ui.activity.ShowDetailsActivity
import com.kevcordova.jobsitychallenge.usescases.GetEpisodeBySeasonAndChapter

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class EpisodeInfoFullscreenFragment : DialogFragment() {

    companion object {
        const val TAG = "EpisodeInfoFullscreenFragment"
        fun newInstanceWithParams(idShow: Int, episodeParcelable: EpisodeParcelable) =
            EpisodeInfoFullscreenFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ShowDetailsActivity.PARAM_EPISODE_INFO, episodeParcelable)
                    putInt(ShowDetailsActivity.PARAM_SHOW_ID, idShow)
                }
            }
    }

    private val remoteEpisodeDataSource: RemoteEpisodeDataSource by lazy {
        EpisodeRetrofitDataSource(EpisodeRequest)
    }

    private val episodeRepository: EpisodeRepository by lazy {
        EpisodeRepository(remoteEpisodeDataSource)
    }

    private val getEpisodeBySeasonAndChapter: GetEpisodeBySeasonAndChapter by lazy {
        GetEpisodeBySeasonAndChapter(episodeRepository)
    }

    private val viewModel by lazy {
        EpisodeViewModel(getEpisodeBySeasonAndChapter)
    }

    private var _episodeParcelable: EpisodeParcelable? = null
    private val episodeParcelable: EpisodeParcelable get() = _episodeParcelable!!

    private var _idShow: Int? = null
    private val idShow: Int get() = _idShow!!

    private var _binding: FragmentEpisodeInfoFullscreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_JobsityCallengeApp_Light_FullScreenDialog)
        _episodeParcelable = arguments?.getParcelable(ShowDetailsActivity.PARAM_EPISODE_INFO)
        _idShow = arguments?.getInt(ShowDetailsActivity.PARAM_SHOW_ID)
        if (_episodeParcelable != null && _idShow != null) {
            viewModel.addDataOfSearch(idShow, episodeParcelable)
        } else {
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodeInfoFullscreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarEpisodeInfo.setNavigationOnClickListener { dismiss() }
        viewModel.episodeInfoEvents.observe(
            viewLifecycleOwner,
            Observer(this::manageEventsEpisodeInfo)
        )
        viewModel.searchEpisode()
    }

    private fun manageEventsEpisodeInfo(events: Event<EpisodeViewModel.EpisodeInfoNavigation>) {
        events.getContentIfNotHandle()?.let { nav ->
            when (nav) {
                is EpisodeViewModel.EpisodeInfoNavigation.ShowEpisodeDetail -> nav.run {
                    bindEpisodeOnUi(
                        episode
                    )
                }
                EpisodeViewModel.EpisodeInfoNavigation.ShowLoading -> binding.progressLinearEpisodeInfo.visibility =
                    View.VISIBLE
                EpisodeViewModel.EpisodeInfoNavigation.HideLoading -> binding.progressLinearEpisodeInfo.visibility =
                    View.GONE
            }
        }
    }

    private fun bindEpisodeOnUi(episode: Episode) {
        binding.run {
            imageCoverEpisodeInfo.bindGlideImage(
                episode.imageUrl,
                R.drawable.ic_round_image_placeholder,
                R.drawable.ic_round_broken_image
            )
            textSeasonAndChapterInfo.text = getString(
                R.string.season_and_chapter_complex,
                episode.season,
                episode.numberChapter
            )
            textTitleEpisodeInfo.text = episode.name
            textSummaryEpisodeInfo.text = episode.summary.fromHtml()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val widthHeightLayout = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(widthHeightLayout, widthHeightLayout)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
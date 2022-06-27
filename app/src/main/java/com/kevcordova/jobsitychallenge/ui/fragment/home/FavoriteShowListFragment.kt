package com.kevcordova.jobsitychallenge.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.baseandroidmodulekevcordova.extensions.showShortToast
import com.kevcordova.jobsitychallenge.R
import com.kevcordova.jobsitychallenge.api.ShowRequest
import com.kevcordova.jobsitychallenge.api.ShowRetrofitDataSource
import com.kevcordova.jobsitychallenge.data.RemoteShowDataSource
import com.kevcordova.jobsitychallenge.data.ShowRepository
import com.kevcordova.jobsitychallenge.databinding.FragmentFavoriteShowListBinding
import com.kevcordova.jobsitychallenge.model.mappers.toParcelable
import com.kevcordova.jobsitychallenge.presenter.Event
import com.kevcordova.jobsitychallenge.presenter.ShowViewModel
import com.kevcordova.jobsitychallenge.ui.activity.ShowDetailsActivity
import com.kevcordova.jobsitychallenge.ui.adapters.ShowAndEpisodeHomeRecyclerViewAdapter
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem
import com.kevcordova.jobsitychallenge.usescases.GetAllShowUseCase
import com.kevcordova.jobsitychallenge.usescases.SearchByNameShowUseCase
import com.kevcordova.jobsitychallenge.utils.FavoritePreferences

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteShowListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteShowListFragment : Fragment() {

    private val remoteShowDataSource: RemoteShowDataSource by lazy {
        ShowRetrofitDataSource(ShowRequest)
    }
    private val showRepository: ShowRepository by lazy {
        ShowRepository(remoteShowDataSource)
    }

    private val getAllShowUseCase: GetAllShowUseCase by lazy {
        GetAllShowUseCase(showRepository)
    }
    private val getSearchByNameShowUseCase: SearchByNameShowUseCase by lazy {
        SearchByNameShowUseCase(showRepository)
    }

    private val showListRecyclerViewAdapter = ShowAndEpisodeHomeRecyclerViewAdapter()
    private val showFavoriteList = mutableListOf<BaseRecyclerViewItem.ShowRecyclerViewItem>()

    private val showSharedViewModel: ShowViewModel by activityViewModels()
    private lateinit var binding: FragmentFavoriteShowListBinding
    private val favoritePreferences = FavoritePreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_favorite_show_list,
            container,
            false
        )
        binding.viewModel = showSharedViewModel
        binding.fragment = this
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.my_favorite_show)
        bindUiStatusOfFavorite()
        binding.favoriteShowListRecyclerview.adapter = showListRecyclerViewAdapter
        manageItemClickListener()

        showSharedViewModel.run {
            setUseCase(getAllShowUseCase, getSearchByNameShowUseCase)
            listShowFavorite()
        }
        showSharedViewModel.showFavoriteListEvent.observe(
            viewLifecycleOwner,
            Observer(this::manageNavigationEvents)
        )

    }

    private fun manageNavigationEvents(events: Event<ShowViewModel.ShowListNavigation>) {
        events.getContentIfNotHandle()?.let { nav ->
            when (nav) {
                is ShowViewModel.ShowListNavigation.ShowListAsRecyclerViewItem -> nav.run {
                    showFavoriteList.clear()
                    showFavoriteList.addAll(showList)
                    showListRecyclerViewAdapter.items = showFavoriteList
                }
                is ShowViewModel.ShowListNavigation.ShowListError -> nav.run {
                    activity?.showShortToast(throwable.message ?: "Error on list")
                }
                ShowViewModel.ShowListNavigation.HideLoading -> binding.progressLinearFavoriteList.visibility =
                    View.GONE
                ShowViewModel.ShowListNavigation.ShowLoading -> binding.progressLinearFavoriteList.visibility =
                    View.VISIBLE
            }
        }
    }

    fun orderByAscendName() {
        showListRecyclerViewAdapter.items = showFavoriteList.sortedBy {
            it.title
        }
    }

    private fun bindUiStatusOfFavorite() {
        if (favoritePreferences.getFavoriteListShow().favoriteList.isEmpty()) {
            binding.emptyFavoriteListGroup.visibility = View.VISIBLE
            binding.favoriteShowListRecyclerview.visibility = View.GONE
        }
    }

    private fun manageItemClickListener() {
        showListRecyclerViewAdapter.itemClickListener = { _, item, _ ->
            val showRecyclerViewItem: BaseRecyclerViewItem.ShowRecyclerViewItem? =
                if (item is BaseRecyclerViewItem.ShowRecyclerViewItem) item else null
            showRecyclerViewItem?.run {
                val intent = Intent(activity, ShowDetailsActivity::class.java).apply {
                    putExtra(ShowListFragment.PARAM_SHOW_ITEM, this@run.toParcelable())
                }
                startActivity(intent)
            }
        }
    }
}
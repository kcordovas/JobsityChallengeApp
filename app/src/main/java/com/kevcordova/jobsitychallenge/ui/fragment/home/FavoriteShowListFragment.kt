package com.kevcordova.jobsitychallenge.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.baseandroidmodulekevcordova.extensions.showShortToast
import com.kevcordova.jobsitychallenge.R
import com.kevcordova.jobsitychallenge.databinding.FragmentFavoriteShowListBinding
import com.kevcordova.jobsitychallenge.presenter.Event
import com.kevcordova.jobsitychallenge.presenter.ShowViewModel
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem
import com.kevcordova.jobsitychallenge.utils.FavoritePreferences

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteShowListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteShowListFragment : HomeBaseFragment() {

    private val showFavoriteList = mutableListOf<BaseRecyclerViewItem.ShowRecyclerViewItem>()

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

        showSharedViewModel.run {
            setUseCase(component.getAllShowUseCase, component.searchByNameUseCase)
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
}
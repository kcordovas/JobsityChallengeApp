package com.kevcordova.jobsitychallenge.ui.fragment.home

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
import com.kevcordova.jobsitychallenge.databinding.FragmentShowListBinding
import com.kevcordova.jobsitychallenge.extensions.doAfterTextChanged
import com.kevcordova.jobsitychallenge.presenter.Event
import com.kevcordova.jobsitychallenge.presenter.ShowViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ShowListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowListFragment : HomeBaseFragment() {
    companion object {
        const val PARAM_SHOW_ITEM = "PARAM_SHOW_ITEM"
    }

    private var isSearchingShow: Boolean = false

    private val showViewModel: ShowViewModel by activityViewModels()
    private lateinit var binding: FragmentShowListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_show_list, container, false)
        binding.fragment = this
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.show_list)
        binding.showListRecyclerview.adapter = showListRecyclerViewAdapter
        showViewModel.showListEvent.observe(
            viewLifecycleOwner,
            Observer(this::manageNavigationEvents)
        )
        showViewModel.run {
            setUseCase(component.getAllShowUseCase, component.searchByNameUseCase)
            listShowAll()
        }

        binding.searchInput.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                if (showListRecyclerViewAdapter.items.isEmpty()) {
                    showViewModel.listShowAll()
                } else {
                    showListRecyclerViewAdapter.items = emptyList()
                    showViewModel.listShowAll()
                }
            }
            if (!isSearchingShow) {
                showViewModel.search(it ?: "")
                isSearchingShow = true
            }
        }
    }

    private fun manageNavigationEvents(events: Event<ShowViewModel.ShowListNavigation>) {
        events.getContentIfNotHandle()?.let { nav ->
            when (nav) {
                is ShowViewModel.ShowListNavigation.ShowListAsRecyclerViewItem -> nav.run {
                    isSearchingShow = false
                    showListRecyclerViewAdapter.items = showList
                }
                is ShowViewModel.ShowListNavigation.ShowListError -> nav.run {
                    when (throwable) {
                        is ArrayStoreException -> {
                            isSearchingShow = false
                            showListRecyclerViewAdapter.items = emptyList()
                        }
                        else -> activity?.showShortToast(throwable.message ?: "Error on list")
                    }
                }
                ShowViewModel.ShowListNavigation.HideLoading -> binding.progressLinearShowList.visibility =
                    View.GONE
                ShowViewModel.ShowListNavigation.ShowLoading -> binding.progressLinearShowList.visibility =
                    View.VISIBLE
            }
        }
    }

    fun filter() {

    }
}
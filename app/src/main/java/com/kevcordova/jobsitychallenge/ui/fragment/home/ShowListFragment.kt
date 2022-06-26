package com.kevcordova.jobsitychallenge.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.baseandroidmodulekevcordova.extensions.showShortToast
import com.kevcordova.jobsitychallenge.R
import com.kevcordova.jobsitychallenge.api.ShowRequest
import com.kevcordova.jobsitychallenge.api.ShowRetrofitDataSource
import com.kevcordova.jobsitychallenge.data.RemoteShowDataSource
import com.kevcordova.jobsitychallenge.data.ShowRepository
import com.kevcordova.jobsitychallenge.databinding.FragmentShowListBinding
import com.kevcordova.jobsitychallenge.model.mappers.toParcelable
import com.kevcordova.jobsitychallenge.presenter.Event
import com.kevcordova.jobsitychallenge.presenter.ShowViewModel
import com.kevcordova.jobsitychallenge.ui.activity.ShowDetailsActivity
import com.kevcordova.jobsitychallenge.ui.adapters.ShowAndEpisodeHomeRecyclerViewAdapter
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem
import com.kevcordova.jobsitychallenge.usescases.GetAllShowUseCase

/**
 * A simple [Fragment] subclass.
 * Use the [ShowListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowListFragment : Fragment() {
    companion object {
        const val PARAM_SHOW_ITEM = "PARAM_SHOW_ITEM"
    }

    private val remoteShowDataSource: RemoteShowDataSource by lazy {
        ShowRetrofitDataSource(ShowRequest)
    }
    private val showRepository: ShowRepository by lazy {
        ShowRepository(remoteShowDataSource)
    }

    private val getAllShowUseCase: GetAllShowUseCase by lazy {
        GetAllShowUseCase(showRepository)
    }

    private val showListRecyclerViewAdapter = ShowAndEpisodeHomeRecyclerViewAdapter()

    private val showSharedViewModel: ShowViewModel by activityViewModels()
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
        manageItemClickListener()

        showSharedViewModel.showListEvent.observe(
            viewLifecycleOwner,
            Observer(this::manageNavigationEvents)
        )
        showSharedViewModel.run {
            setUseCase(getAllShowUseCase)
            listShowAll()
        }
    }

    private fun manageNavigationEvents(events: Event<ShowViewModel.ShowListNavigation>) {
        events.getContentIfNotHandle()?.let { nav ->
            when (nav) {
                is ShowViewModel.ShowListNavigation.ShowListAsRecyclerViewItem -> nav.run {
                    showListRecyclerViewAdapter.items = showList
                }
                is ShowViewModel.ShowListNavigation.ShowListError -> nav.run {
                    activity?.showShortToast(throwable.message ?: "Error on list")
                }
                ShowViewModel.ShowListNavigation.HideLoading -> binding.progressLinearShowList.visibility =
                    View.GONE
                ShowViewModel.ShowListNavigation.ShowLoading -> binding.progressLinearShowList.visibility =
                    View.VISIBLE
            }
        }
    }

    private fun manageItemClickListener() {
        showListRecyclerViewAdapter.itemClickListener = { _, item, _ ->
            val showRecyclerViewItem: BaseRecyclerViewItem.ShowRecyclerViewItem? = if (item is BaseRecyclerViewItem.ShowRecyclerViewItem) item else null
            showRecyclerViewItem?.run {
                val intent = Intent(activity, ShowDetailsActivity::class.java).apply {
                    putExtra(PARAM_SHOW_ITEM, this@run.toParcelable())
                }
                startActivity(intent)
            }
        }
    }

    fun filter() {

    }
}
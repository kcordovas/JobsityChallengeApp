package com.kevcordova.jobsitychallenge.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kevcordova.jobsitychallenge.JobsityChallengeApplication
import com.kevcordova.jobsitychallenge.dependencyinyection.UseCaseComponent
import com.kevcordova.jobsitychallenge.model.mappers.toParcelable
import com.kevcordova.jobsitychallenge.presenter.ShowViewModel
import com.kevcordova.jobsitychallenge.ui.activity.ShowDetailsActivity
import com.kevcordova.jobsitychallenge.ui.adapters.ShowAndEpisodeHomeRecyclerViewAdapter
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewItem

abstract class HomeBaseFragment: Fragment() {

    protected val component: UseCaseComponent by lazy {
        JobsityChallengeApplication.getApplication().useCaseComponent
    }
    protected val showListRecyclerViewAdapter = ShowAndEpisodeHomeRecyclerViewAdapter()
    protected val showSharedViewModel: ShowViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageItemClickListener()
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
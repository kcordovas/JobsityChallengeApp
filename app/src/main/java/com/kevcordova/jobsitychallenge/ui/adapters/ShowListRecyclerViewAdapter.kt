package com.kevcordova.jobsitychallenge.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevcordova.jobsitychallenge.databinding.ItemShowLayoutBinding
import com.kevcordova.jobsitychallenge.domain.Show
import com.kevcordova.jobsitychallenge.ui.adapters.base.BaseRecyclerViewHolder

class ShowListRecyclerViewAdapter() : RecyclerView.Adapter<BaseRecyclerViewHolder>() {
    var items = listOf<Show>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return BaseRecyclerViewHolder.ShowViewHolder(
            ItemShowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        (holder as BaseRecyclerViewHolder.ShowViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
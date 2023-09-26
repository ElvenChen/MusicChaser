package com.example.musicchaser.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.EventData
import com.example.musicchaser.databinding.ViewholderHomeHotListBinding

class HomeHotEventListAdapter() :
    ListAdapter<EventData, RecyclerView.ViewHolder>(HomeHotEventListDiffCallback()) {

    override fun getItemCount(): Int {
        return if (currentList.size == 0) 0 else Int.MAX_VALUE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeHotEventViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeHotEventViewHolder -> {
                val eventData = getItem(position % currentList.size) as EventData
                holder.bind(eventData)
            }
        }
    }

    class HomeHotEventViewHolder(private val binding: ViewholderHomeHotListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EventData) {

            // data binding
            binding.property = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HomeHotEventViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderHomeHotListBinding.inflate(layoutInflater, parent, false)
                return HomeHotEventViewHolder(binding)
            }
        }
    }

    class HomeHotEventListDiffCallback :
        DiffUtil.ItemCallback<EventData>() {
        override fun areItemsTheSame(oldItem: EventData, newItem: EventData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: EventData,
            newItem: EventData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
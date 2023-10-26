package com.example.musicchaser.eventdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.EventCommentData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.databinding.ViewholderCommentListBinding
import com.example.musicchaser.databinding.ViewholderEventListBinding
import com.example.musicchaser.event.EventListAdapter

class EventCommentListAdapter() :
    ListAdapter<EventCommentData, RecyclerView.ViewHolder>(EventCommentListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventCommentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EventCommentViewHolder -> {
                val evenCommentData = getItem(position) as EventCommentData
                holder.bind(evenCommentData)
            }
        }
    }

    class EventCommentViewHolder(private val binding: ViewholderCommentListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EventCommentData) {

            // data binding
            binding.property = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EventCommentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderCommentListBinding.inflate(layoutInflater, parent, false)
                return EventCommentViewHolder(binding)
            }
        }
    }

    class EventCommentListDiffCallback :
        DiffUtil.ItemCallback<EventCommentData>() {
        override fun areItemsTheSame(oldItem: EventCommentData, newItem: EventCommentData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: EventCommentData,
            newItem: EventCommentData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
package com.example.musicchaser.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.EventData
import com.example.musicchaser.databinding.ViewholderHomeFavoriteArtistRelativeEventListBinding
import com.example.musicchaser.databinding.ViewholderHomeHotListBinding

class HomeFavoriteArtistRelativeEventListAdapter(private val displayRelativeEventDetails: (EventData) -> Unit) :
    ListAdapter<EventData, RecyclerView.ViewHolder>(HomeFavoriteArtistRelativeEventListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeFavoriteArtistRelativeEventViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeFavoriteArtistRelativeEventViewHolder -> {
                val eventData = getItem(position) as EventData
                holder.bind(eventData)
                holder.itemView.setOnClickListener {
                    displayRelativeEventDetails(eventData)
                }
            }
        }
    }

    class HomeFavoriteArtistRelativeEventViewHolder(private val binding: ViewholderHomeFavoriteArtistRelativeEventListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EventData) {

            // data binding
            binding.property = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HomeFavoriteArtistRelativeEventViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderHomeFavoriteArtistRelativeEventListBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return HomeFavoriteArtistRelativeEventViewHolder(binding)
            }
        }
    }

    class HomeFavoriteArtistRelativeEventListDiffCallback :
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
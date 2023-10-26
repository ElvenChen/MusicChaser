package com.example.musicchaser.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.databinding.ViewholderProfileFavoriteEventListBinding

class FavoriteEventListAdapter(private val displayEventDetails: (EventData) -> Unit) :
    ListAdapter<EventData, RecyclerView.ViewHolder>(EventListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteEventViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteEventViewHolder -> {
                val evenData = getItem(position) as EventData
                holder.bind(evenData)
                holder.itemView.setOnClickListener {
                    displayEventDetails(evenData)
                }
            }
        }
    }

    class FavoriteEventViewHolder(private val binding: ViewholderProfileFavoriteEventListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EventData) {

            // data binding
            binding.property = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteEventViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderProfileFavoriteEventListBinding.inflate(layoutInflater, parent, false)
                return FavoriteEventViewHolder(binding)
            }
        }
    }

    class EventListDiffCallback :
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
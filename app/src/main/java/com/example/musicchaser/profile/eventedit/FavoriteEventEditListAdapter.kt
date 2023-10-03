package com.example.musicchaser.profile.eventedit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.MusicChaserRemoteDataSource.deleteUserFavoriteEvent
import com.example.musicchaser.databinding.ViewholderProfileFavoriteEventEditListBinding
import com.example.musicchaser.databinding.ViewholderProfileFavoriteEventListBinding
import com.example.musicchaser.profile.FavoriteEventListAdapter

class FavoriteEventEditListAdapter(
    private val deleteUserFavoriteEvent: (String) -> Unit,
    private val subtractEventAttendantAmounts: (String) -> Unit
) :
    ListAdapter<EventData, RecyclerView.ViewHolder>(EventEditListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteEventEditViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteEventEditViewHolder -> {
                val evenData = getItem(position) as EventData
                holder.bind(evenData, deleteUserFavoriteEvent, subtractEventAttendantAmounts)
            }
        }
    }

    class FavoriteEventEditViewHolder(private val binding: ViewholderProfileFavoriteEventEditListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: EventData,
            deleteUserFavoriteEvent: (String) -> Unit,
            subtractEventAttendantAmounts: (String) -> Unit
        ) {

            // data binding
            binding.property = item
            binding.favoriteEventDeleteButton.setOnClickListener {
                deleteUserFavoriteEvent(item.eventId)
                subtractEventAttendantAmounts(item.eventId)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteEventEditViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderProfileFavoriteEventEditListBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return FavoriteEventEditViewHolder(binding)
            }
        }
    }

    class EventEditListDiffCallback :
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
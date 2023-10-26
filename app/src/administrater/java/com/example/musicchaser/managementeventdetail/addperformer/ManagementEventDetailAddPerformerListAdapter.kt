package com.example.musicchaser.managementeventdetail.addperformer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.source.MusicChaserRemoteDataSource.deleteUserFavoriteArtist
import com.example.musicchaser.databinding.ViewholderManagementEventPerformerListBinding

class ManagementEventDetailAddPerformerListAdapter(private val displayPerformerDeleteDetails: (ArtistData) -> Unit) :
    ListAdapter<ArtistData, RecyclerView.ViewHolder>(EventPerformerListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventPerformerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EventPerformerViewHolder -> {
                val artistData = getItem(position) as ArtistData
                holder.bind(artistData, displayPerformerDeleteDetails)
            }
        }
    }

    class EventPerformerViewHolder(private val binding: ViewholderManagementEventPerformerListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArtistData, displayPerformerDeleteDetails: (ArtistData) -> Unit) {

            // data binding
            binding.property = item
            binding.managementListPerformerDeleteButton.setOnClickListener {
                displayPerformerDeleteDetails(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EventPerformerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderManagementEventPerformerListBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return EventPerformerViewHolder(binding)
            }
        }
    }

    class EventPerformerListDiffCallback :
        DiffUtil.ItemCallback<ArtistData>() {
        override fun areItemsTheSame(oldItem: ArtistData, newItem: ArtistData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ArtistData,
            newItem: ArtistData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
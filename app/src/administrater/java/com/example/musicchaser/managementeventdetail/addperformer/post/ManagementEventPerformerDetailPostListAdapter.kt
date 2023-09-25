package com.example.musicchaser.managementeventdetail.addperformer.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.databinding.ViewholderManagementEventPerformerPostListBinding

class ManagementEventPerformerDetailPostListAdapter(
    private val deleteItemFromFilteredDataListAfterClickPost: (ArtistData) -> Unit,
    private val postEventPerformer: (ArtistData) -> Unit,
    private val postArtistAttendEvent: (ArtistData) -> Unit
) :
    ListAdapter<ArtistData, RecyclerView.ViewHolder>(EventPerformerPostListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventPerformerPostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EventPerformerPostViewHolder -> {
                val artistData = getItem(position) as ArtistData
                holder.bind(
                    artistData,
                    deleteItemFromFilteredDataListAfterClickPost,
                    postEventPerformer,
                    postArtistAttendEvent
                )
            }
        }
    }

    class EventPerformerPostViewHolder(private val binding: ViewholderManagementEventPerformerPostListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ArtistData,
            deleteItemFromFilteredDataListAfterClickPost: (ArtistData) -> Unit,
            postEventPerformer: (ArtistData) -> Unit,
            postArtistAttendEvent: (ArtistData) -> Unit
        ) {

            // data binding
            binding.property = item
            binding.managementListPerformerPostButton.setOnClickListener {
                postEventPerformer(item)
                postArtistAttendEvent(item)
                deleteItemFromFilteredDataListAfterClickPost(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EventPerformerPostViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderManagementEventPerformerPostListBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return EventPerformerPostViewHolder(binding)
            }
        }
    }

    class EventPerformerPostListDiffCallback :
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
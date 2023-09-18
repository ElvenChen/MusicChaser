package com.example.musicchaser.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.databinding.ViewholderArtistListBinding

class ArtistListAdapter(private val displayArtistDetails: (ArtistData) -> Unit) :
    ListAdapter<ArtistData, RecyclerView.ViewHolder>(ArtistListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArtistViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArtistViewHolder -> {
                val artistData = getItem(position) as ArtistData
                holder.bind(artistData)
                holder.itemView.setOnClickListener {
                    displayArtistDetails(artistData)
                }
            }
        }
    }

    class ArtistViewHolder(private val binding: ViewholderArtistListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArtistData) {

            // data binding
            binding.property = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ArtistViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderArtistListBinding.inflate(layoutInflater, parent, false)
                return ArtistViewHolder(binding)
            }
        }
    }

    class ArtistListDiffCallback :
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
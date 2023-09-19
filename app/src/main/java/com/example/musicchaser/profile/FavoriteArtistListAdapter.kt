package com.example.musicchaser.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.databinding.ViewholderProfileFavoriteArtistListBinding
import com.example.musicchaser.databinding.ViewholderProfileFavoriteEventListBinding

class FavoriteArtistListAdapter(private val displayArtistDetails: (ArtistData) -> Unit) :
    ListAdapter<ArtistData, RecyclerView.ViewHolder>(ArtistListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteArtistViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteArtistViewHolder -> {
                val artistData = getItem(position) as ArtistData
                holder.bind(artistData)
                holder.itemView.setOnClickListener {
                    displayArtistDetails(artistData)
                }
            }
        }
    }

    class FavoriteArtistViewHolder(private val binding: ViewholderProfileFavoriteArtistListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArtistData) {

            // data binding
            binding.property = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteArtistViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderProfileFavoriteArtistListBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return FavoriteArtistViewHolder(binding)
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
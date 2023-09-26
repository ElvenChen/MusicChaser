package com.example.musicchaser.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.databinding.ViewholderArtistListBinding
import com.example.musicchaser.databinding.ViewholderHomeProtentialFavoriteArtistListBinding

class HomePotentialFavoriteArtistListAdapter(private val displayPotentialArtistDetails: (ArtistData) -> Unit) :
    ListAdapter<ArtistData, RecyclerView.ViewHolder>(PotentialFavoriteArtistListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomePotentialFavoriteArtistViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomePotentialFavoriteArtistViewHolder -> {
                val artistData = getItem(position) as ArtistData
                holder.bind(artistData)
                holder.itemView.setOnClickListener {
                    displayPotentialArtistDetails(artistData)
                }
            }
        }
    }

    class HomePotentialFavoriteArtistViewHolder(private val binding: ViewholderHomeProtentialFavoriteArtistListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArtistData) {

            // data binding
            binding.property = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HomePotentialFavoriteArtistViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderHomeProtentialFavoriteArtistListBinding.inflate(layoutInflater, parent, false)
                return HomePotentialFavoriteArtistViewHolder(binding)
            }
        }
    }

    class PotentialFavoriteArtistListDiffCallback :
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
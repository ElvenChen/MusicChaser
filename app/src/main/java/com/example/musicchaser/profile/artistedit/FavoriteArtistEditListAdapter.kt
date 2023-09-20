package com.example.musicchaser.profile.artistedit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.databinding.ViewholderProfileFavoriteArtistEditListBinding

class FavoriteArtistEditListAdapter(private val deleteUserFavoriteArtist: (String) -> Unit) :
    ListAdapter<ArtistData, RecyclerView.ViewHolder>(ArtistEditListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteArtistEditViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteArtistEditViewHolder -> {
                val artistData = getItem(position) as ArtistData
                holder.bind(artistData,deleteUserFavoriteArtist)
            }
        }
    }

    class FavoriteArtistEditViewHolder(private val binding: ViewholderProfileFavoriteArtistEditListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArtistData, deleteUserFavoriteArtist: (String) -> Unit) {

            // data binding
            binding.property = item
            binding.favoriteArtistDeleteButton.setOnClickListener {
                deleteUserFavoriteArtist(item.artistId)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteArtistEditViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderProfileFavoriteArtistEditListBinding.inflate(layoutInflater, parent, false)
                return FavoriteArtistEditViewHolder(binding)
            }
        }
    }

    class ArtistEditListDiffCallback :
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
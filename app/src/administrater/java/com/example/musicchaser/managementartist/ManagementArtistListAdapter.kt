package com.example.musicchaser.managementartist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.artist.ArtistListAdapter
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.databinding.ViewholderArtistListBinding
import com.example.musicchaser.databinding.ViewholderManagementArtistListBinding

class ManagementArtistListAdapter(
    private val displayArtistEditDetails: (ArtistData) -> Unit,
    private val displayArtistDeleteDetails: (ArtistData) -> Unit
) :
    ListAdapter<ArtistData, RecyclerView.ViewHolder>(ManagementArtistListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ManagementArtistViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ManagementArtistViewHolder -> {
                val artistData = getItem(position) as ArtistData
                holder.bind(artistData, displayArtistEditDetails, displayArtistDeleteDetails)
            }
        }
    }

    class ManagementArtistViewHolder(private val binding: ViewholderManagementArtistListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ArtistData,
            displayArtistEditDetails: (ArtistData) -> Unit,
            displayArtistDeleteDetails: (ArtistData) -> Unit
        ) {

            // data binding
            binding.property = item
            binding.managementListArtistEditButton.setOnClickListener {
                displayArtistEditDetails(item)
            }
            binding.managementListArtistDeleteButton.setOnClickListener {
                displayArtistDeleteDetails(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ManagementArtistViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ViewholderManagementArtistListBinding.inflate(layoutInflater, parent, false)
                return ManagementArtistViewHolder(binding)
            }
        }
    }

    class ManagementArtistListDiffCallback :
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
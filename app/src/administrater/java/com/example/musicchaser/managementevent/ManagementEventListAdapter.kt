package com.example.musicchaser.managementevent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.databinding.ViewholderManagementArtistListBinding
import com.example.musicchaser.databinding.ViewholderManagementEventListBinding
import com.example.musicchaser.managementartist.ManagementArtistListAdapter

class ManagementEventListAdapter(
    private val displayEventEditDetails: (EventData) -> Unit,
    private val displayEventDeleteDetails: (EventData) -> Unit,
    private val displayAddEventPerformerDetails: (EventData) -> Unit
) :
    ListAdapter<EventData, RecyclerView.ViewHolder>(ManagementEventListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ManagementEventViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ManagementEventViewHolder -> {
                val eventData = getItem(position) as EventData
                holder.bind(
                    eventData,
                    displayEventEditDetails,
                    displayEventDeleteDetails,
                    displayAddEventPerformerDetails
                )
            }
        }
    }

    class ManagementEventViewHolder(private val binding: ViewholderManagementEventListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: EventData,
            displayEventEditDetails: (EventData) -> Unit,
            displayEventDeleteDetails: (EventData) -> Unit,
            displayAddEventPerformerDetails: (EventData) -> Unit
        ) {

            // data binding
            binding.property = item
            binding.managementListEventEditButton.setOnClickListener {
                displayEventEditDetails(item)
            }
            binding.managementListEventDeleteButton.setOnClickListener {
                displayEventDeleteDetails(item)
            }
            binding.managementListEventPerformerEditButton.setOnClickListener {
                displayAddEventPerformerDetails(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ManagementEventViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ViewholderManagementEventListBinding.inflate(layoutInflater, parent, false)
                return ManagementEventViewHolder(binding)
            }
        }
    }

    class ManagementEventListDiffCallback :
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
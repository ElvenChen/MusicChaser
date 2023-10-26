package com.example.musicchaser.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.EventData
import com.example.musicchaser.databinding.ViewholderEventListBinding
import io.grpc.okhttp.internal.framed.Variant

class EventListAdapter(private val displayEventDetails: (EventData) -> Unit) :
    ListAdapter<EventData, RecyclerView.ViewHolder>(EventListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EventViewHolder -> {
                val eventData = getItem(position) as EventData
                holder.bind(eventData)
                holder.itemView.setOnClickListener {
                    displayEventDetails(eventData)
                }
            }
        }
    }

    class EventViewHolder(private val binding: ViewholderEventListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EventData) {

            // data binding
            binding.property = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EventViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderEventListBinding.inflate(layoutInflater, parent, false)
                return EventViewHolder(binding)
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
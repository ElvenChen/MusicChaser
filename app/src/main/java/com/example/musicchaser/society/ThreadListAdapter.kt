package com.example.musicchaser.society

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.ThreadData
import com.example.musicchaser.databinding.ViewholderThreadListBinding

class ThreadListAdapter(private val displayThreadDetails: (ThreadData) -> Unit) :
    ListAdapter<ThreadData, RecyclerView.ViewHolder>(ThreadListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ThreadViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ThreadViewHolder -> {
                val threadData = getItem(position) as ThreadData
                holder.bind(threadData)
                holder.itemView.setOnClickListener {
                    displayThreadDetails(threadData)
                }
            }
        }
    }

    class ThreadViewHolder(private val binding: ViewholderThreadListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ThreadData) {

            // data binding
            binding.property = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ThreadViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderThreadListBinding.inflate(layoutInflater, parent, false)
                return ThreadViewHolder(binding)
            }
        }
    }

    class ThreadListDiffCallback :
        DiffUtil.ItemCallback<ThreadData>() {
        override fun areItemsTheSame(oldItem: ThreadData, newItem: ThreadData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ThreadData,
            newItem: ThreadData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
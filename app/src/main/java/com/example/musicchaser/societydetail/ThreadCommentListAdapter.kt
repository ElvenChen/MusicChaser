package com.example.musicchaser.societydetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.ThreadCommentData
import com.example.musicchaser.databinding.ViewholderThreadCommentListBinding

class ThreadCommentListAdapter() :
    ListAdapter<ThreadCommentData, RecyclerView.ViewHolder>(ThreadCommentListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ThreadCommentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ThreadCommentViewHolder -> {
                val threadCommentData = getItem(position) as ThreadCommentData
                holder.bind(threadCommentData)
            }
        }
    }

    class ThreadCommentViewHolder(private val binding: ViewholderThreadCommentListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ThreadCommentData) {

            // data binding
            binding.property = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ThreadCommentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderThreadCommentListBinding.inflate(layoutInflater, parent, false)
                return ThreadCommentViewHolder(binding)
            }
        }
    }

    class ThreadCommentListDiffCallback :
        DiffUtil.ItemCallback<ThreadCommentData>() {
        override fun areItemsTheSame(oldItem: ThreadCommentData, newItem: ThreadCommentData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ThreadCommentData,
            newItem: ThreadCommentData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
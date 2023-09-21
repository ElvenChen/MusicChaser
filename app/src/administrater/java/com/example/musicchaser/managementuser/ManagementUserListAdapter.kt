package com.example.musicchaser.managementuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.UserData
import com.example.musicchaser.databinding.ViewholderManagementUserListBinding

class ManagementUserListAdapter(private val displayUserEditDetails: (UserData) -> Unit) :
    ListAdapter<UserData, RecyclerView.ViewHolder>(ManagementUserListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ManagementUserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ManagementUserViewHolder -> {
                val userData = getItem(position) as UserData
                holder.bind(userData,displayUserEditDetails)
            }
        }
    }

    class ManagementUserViewHolder(private val binding: ViewholderManagementUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserData, displayUserEditDetails: (UserData) -> Unit) {

            // data binding
            binding.property = item
            binding.managementListUserEditButton.setOnClickListener {
                displayUserEditDetails(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ManagementUserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ViewholderManagementUserListBinding.inflate(layoutInflater, parent, false)
                return ManagementUserViewHolder(binding)
            }
        }
    }

    class ManagementUserListDiffCallback :
        DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: UserData,
            newItem: UserData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
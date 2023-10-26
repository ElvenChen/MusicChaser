package com.example.musicchaser.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.databinding.ViewholderSolidDotBinding
import com.example.musicchaser.databinding.ViewholderStrokedDotBinding

private const val ITEM_VIEW_SOLID = 0x00
private const val ITEM_VIEW_STROKED = 0x01

class HomeHotEventDottedListAdapter() : ListAdapter<String, RecyclerView.ViewHolder>(HotEventDottedListDiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            "solid" -> ITEM_VIEW_SOLID
            else -> ITEM_VIEW_STROKED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_SOLID -> SolidViewHolder.from(parent)
            ITEM_VIEW_STROKED -> StrokedViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    class SolidViewHolder(private val binding: ViewholderSolidDotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): SolidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderSolidDotBinding.inflate(layoutInflater, parent, false)
                return SolidViewHolder(binding)
            }
        }
    }

    class StrokedViewHolder(private val binding: ViewholderStrokedDotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): StrokedViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderStrokedDotBinding.inflate(layoutInflater, parent, false)
                return StrokedViewHolder(binding)
            }
        }
    }

    class HotEventDottedListDiffCallback :
        DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
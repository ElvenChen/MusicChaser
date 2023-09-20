package com.example.musicchaser.profile.eventedit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogFavoriteEventEditBinding
import com.example.musicchaser.profile.FavoriteEventListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteEventEditDialog : AppCompatDialogFragment() {

    private val viewModel: FavoriteEventEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogFavoriteEventEditBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        // setting favorite event recyclerView adapter
        binding.profileFavoriteEventRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val favoriteEventEditAdapter = FavoriteEventEditListAdapter(viewModel.deleteUserFavoriteEvent)
        binding.profileFavoriteEventRecyclerView.adapter = favoriteEventEditAdapter



        //observing userFavoriteEventIdList for making next function call to transfer event ID into event data
        viewModel.userFavoriteEventIdList.observe(viewLifecycleOwner, Observer {
            Log.i("UserFavoriteEventEdit", "Observe userFavoriteEventIdList from Fragment = $it")
            viewModel.getCompletedEventList(it)
        })

        //observing eventDataList list for adapter
        viewModel.eventDataListForAdapter.observe(viewLifecycleOwner, Observer {
            Log.i("UserFavoriteEventEdit", "Observe eventDataList from Fragment = $it")
            favoriteEventEditAdapter.submitList(it)
            favoriteEventEditAdapter.notifyDataSetChanged()
        })




        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }


        return binding.root
    }
}
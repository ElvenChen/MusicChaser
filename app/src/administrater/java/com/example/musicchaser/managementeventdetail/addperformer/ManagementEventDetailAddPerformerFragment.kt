package com.example.musicchaser.managementeventdetail.addperformer

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentManagementEventDetailAddPerformerBinding
import com.example.musicchaser.databinding.FragmentManagementEventDetailEditBinding
import com.example.musicchaser.managementartist.ManagementArtistFragmentDirections
import com.example.musicchaser.managementevent.ManagementEventListAdapter
import com.example.musicchaser.managementeventdetail.edit.ManagementEventDetailEditFragmentArgs
import com.example.musicchaser.managementeventdetail.edit.ManagementEventDetailEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementEventDetailAddPerformerFragment : Fragment() {

    private val viewModel: ManagementEventDetailAddPerformerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementEventDetailAddPerformerBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val event =
            ManagementEventDetailAddPerformerFragmentArgs.fromBundle(requireArguments()).selectedEditEvent
        viewModel.event = event

        // getting event performer artist list
        viewModel.getEventPerformerArtist()

        // setting recyclerView adapter
        binding.managementEventAddPerformerDetailPerformerRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = ManagementEventDetailAddPerformerListAdapter(viewModel.displayPerformerDeleteDetails)
        binding.managementEventAddPerformerDetailPerformerRecyclerView.adapter = adapter



        //observing pendingEventPerformerArtistIdList for making next function call to transfer artist ID into artist data
        viewModel.pendingEventPerformerArtistIdList.observe(viewLifecycleOwner, Observer {
            Log.i(
                "EventPerformerArtist",
                "Observe pendingEventPerformerArtistIdList from Fragment = $it"
            )
            viewModel.getCompletedEventPerformerArtistList(it)
        })

        //observing eventPerformerArtistDataList list for adapter
        viewModel.eventPerformerArtistDataListForAdapter.observe(viewLifecycleOwner, Observer {
            Log.i(
                "EventPerformerArtist",
                "Observe eventPerformerArtistDataList from Fragment = $it"
            )
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })



        // setting navigation
        binding.managementEventAddPerformerDetailBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.navigateToDeletePerformer.observe(viewLifecycleOwner, Observer {
            Log.i("EventPerformerArtist", "PerformerArtist Data : $it")
            it?.let {
                findNavController().navigate(
                    ManagementEventDetailAddPerformerFragmentDirections.navigateToManagementEventPerformerDetailDeleteDialog(event, it)
                )
                viewModel.displayPerformerDeleteDetailsCompleted()
            }
        })



        return binding.root
    }
}
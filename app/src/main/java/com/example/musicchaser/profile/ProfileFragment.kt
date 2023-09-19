package com.example.musicchaser.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicchaser.databinding.FragmentProfileBinding
import com.example.musicchaser.eventdetail.EventCommentListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentProfileBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // getting setting favorite event recyclerView adapter
        binding.profileFavoriteEventRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val favoriteEventAdapter = FavoriteEventListAdapter()
        binding.profileFavoriteEventRecyclerView.adapter = favoriteEventAdapter





        //observing userFavoriteEventIdList for making next function call to transfer event ID into events name
        viewModel.userFavoriteEventIdList.observe(viewLifecycleOwner, Observer {
            Log.i("UserFavoriteEvent", "Observe userFavoriteEventIdList from Fragment = $it")
            viewModel.getCompletedEventList(it)
        })

        //observing eventDataList list for adapter
        viewModel.eventDataListForAdapter.observe(viewLifecycleOwner, Observer {
            Log.i("UserFavoriteEvent", "Observe eventDataList from Fragment = $it")
            favoriteEventAdapter.submitList(it)
            favoriteEventAdapter.notifyDataSetChanged()
        })





        // setting navigation
        binding.profileBasicInfoEditButton.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.navigateToBasicInfoDialog())
        }

        binding.profileFavoriteEventEditButton.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.navigateToFavoriteEventEditDialog())
        }

        binding.profileFavoriteArtistEditButton.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.navigateToFavoriteArtistEditDialog())
        }


        return binding.root
    }
}
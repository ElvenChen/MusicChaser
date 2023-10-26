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
import com.example.musicchaser.artist.ArtistFragmentDirections
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

        // setting favorite event recyclerView adapter
        binding.profileFavoriteEventRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val favoriteEventAdapter = FavoriteEventListAdapter(viewModel.displayEventDetails)
        binding.profileFavoriteEventRecyclerView.adapter = favoriteEventAdapter

        // setting favorite artist recyclerView adapter
        binding.profileFavoriteArtistRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val favoriteArtistAdapter = FavoriteArtistListAdapter(viewModel.displayArtistDetails)
        binding.profileFavoriteArtistRecyclerView.adapter = favoriteArtistAdapter





        //observing userFavoriteEventIdList for making next function call to transfer event ID into event data
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

        //observing userFavoriteArtistIdList for making next function call to transfer artist ID into artist data
        viewModel.userFavoriteArtistIdList.observe(viewLifecycleOwner, Observer {
            Log.i("UserFavoriteArtist", "Observe userFavoriteArtistIdList from Fragment = $it")
            viewModel.getCompletedArtistList(it)
        })

        //observing artistDataList list for adapter
        viewModel.artistDataListForAdapter.observe(viewLifecycleOwner, Observer {
            Log.i("UserFavoriteArtist", "Observe artistDataList from Fragment = $it")
            favoriteArtistAdapter.submitList(it)
            favoriteArtistAdapter.notifyDataSetChanged()
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

        viewModel.navigateToSelectedEvent.observe(viewLifecycleOwner, Observer {
            Log.i("UserFavoriteEvent", "Event Data : $it")
            it?.let {
                findNavController().navigate(
                    ProfileFragmentDirections.navigateToEventdetailFragment(it)
                )
                viewModel.displayEventDetailsCompleted()
            }
        })

        viewModel.navigateToSelectedArtist.observe(viewLifecycleOwner, Observer {
            Log.i("UserFavoriteArtist", "Artist Data : $it")
            it?.let {
                findNavController().navigate(
                    ProfileFragmentDirections.navigateToArtistdetailDialog(it)
                )
                viewModel.displayArtistDetailsCompleted()
            }
        })


        return binding.root
    }
}
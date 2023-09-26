package com.example.musicchaser.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentHomeBinding
import com.example.musicchaser.event.EventViewModel
import com.example.musicchaser.profile.FavoriteEventListAdapter
import com.example.musicchaser.profile.ProfileFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        // setting hot event recyclerView adapter
        binding.homeHotEventRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val hotEventAdapter = HomeHotEventListAdapter(viewModel.displayHotEventDetails)
        binding.homeHotEventRecyclerView.adapter = hotEventAdapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.homeHotEventRecyclerView)

        // setting hot event dotted recyclerView adapter
        binding.homeHotEventDottedRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val hotEventDottedAdapter = HomeHotEventDottedListAdapter()
        binding.homeHotEventDottedRecyclerView.adapter = hotEventDottedAdapter

        // setting favorite artist relative event recyclerView adapter
        binding.homeFavoriteArtistRelativeEventRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val favoriteArtistRelativeEventAdapter = HomeFavoriteArtistRelativeEventListAdapter(viewModel.displayRelativeEventDetails)
        binding.homeFavoriteArtistRelativeEventRecyclerView.adapter = favoriteArtistRelativeEventAdapter

        // setting potential favorite artist recyclerView adapter
        binding.homePotentialFavoriteArtistRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val potentialFavoriteArtistAdapter = HomePotentialFavoriteArtistListAdapter(viewModel.displayPotentialArtistDetails)
        binding.homePotentialFavoriteArtistRecyclerView.adapter = potentialFavoriteArtistAdapter





        // observing hot event list for hotEventAdapter
        viewModel.hotEventDataListForAdapter.observe(viewLifecycleOwner, Observer { hotEventList ->
            hotEventAdapter.submitList(hotEventList)
            hotEventAdapter.notifyDataSetChanged()
            binding.homeHotEventRecyclerView.scrollToPosition(hotEventList.size * 1000)

            viewModel.galleryDotAmount = hotEventList.size
        })

        // setting dotted recycler view to move with infinite hot event recycler view
        var currentItemPlace = 0
        binding.homeHotEventRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val currentPosition = layoutManager.findLastVisibleItemPosition() -1

                if (currentItemPlace != currentPosition) {
                    Log.i("detailDot", "currentPosition = $currentPosition")
                    viewModel.getListForGalleryDotAdapter(currentPosition)
                    currentItemPlace = currentPosition
                    Log.i("detailDot", "currentItemPlace = $currentItemPlace")
                }
            }
        })

        // observing dotted list for adapter
        viewModel.listForGalleryDotAdapter.observe(viewLifecycleOwner, Observer {
            Log.i("detailDot", "listForGalleryDotAdapter : $it")
            hotEventDottedAdapter.submitList(it)
            hotEventDottedAdapter.notifyDataSetChanged()
        })



        // observing Relative event list for favoriteArtistRelativeEventAdapter
        viewModel.relativeEventDataListForAdapter.observe(viewLifecycleOwner, Observer {
            favoriteArtistRelativeEventAdapter.submitList(it)
            favoriteArtistRelativeEventAdapter.notifyDataSetChanged()
        })



        // observing Potential favorite artist list for potentialArtistAdapter
        viewModel.potentialArtistDataListForAdapter.observe(viewLifecycleOwner, Observer {
            potentialFavoriteArtistAdapter.submitList(it)
            potentialFavoriteArtistAdapter.notifyDataSetChanged()
        })







        // setting navigation
        viewModel.navigateToSelectedHotEvent.observe(viewLifecycleOwner, Observer {
            Log.i("HotEventTest", "HotEvent Data : $it")
            it?.let {
                findNavController().navigate(
                    HomeFragmentDirections.navigateToEventdetailFragment(it)
                )
                viewModel.displayHotEventDetailsCompleted()
            }
        })

        viewModel.navigateToSelectedRelativeEvent.observe(viewLifecycleOwner, Observer {
            Log.i("RelativeEventTest", "RelativeEvent Data : $it")
            it?.let {
                findNavController().navigate(
                    HomeFragmentDirections.navigateToEventdetailFragment(it)
                )
                viewModel.displayRelativeEventDetailsCompleted()
            }
        })

        viewModel.navigateToSelectedPotentialArtist.observe(viewLifecycleOwner, Observer {
            Log.i("PotentialArtistTest", "PotentialArtist Data : $it")
            it?.let {
                findNavController().navigate(
                    HomeFragmentDirections.navigateToArtistdetailDialog(it)
                )
                viewModel.displayPotentialArtistDetailsCompleted()
            }
        })




        return binding.root
    }
}
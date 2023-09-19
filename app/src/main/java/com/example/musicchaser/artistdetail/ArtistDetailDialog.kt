package com.example.musicchaser.artistdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogArtistDetailBinding
import com.example.musicchaser.databinding.FragmentEventDetailBinding
import com.example.musicchaser.eventdetail.EventDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistDetailDialog : AppCompatDialogFragment() {

    private val viewModel: ArtistDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialogForArtistDetailDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogArtistDetailBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val artist = ArtistDetailDialogArgs.fromBundle(requireArguments()).selectedArtist
        viewModel.artist = artist

        // check this artist isFavorite = ture or false, to adjust favorite button show/hide
        viewModel.getIfArtistIsFavorite()

        // getting artist recent events
        viewModel.getArtistRecentEventList()





        //observing dataListWithOnlyEventId for making next function call to transfer event ID into event full-detail
        viewModel.dataListForGetEventDetailCall.observe(viewLifecycleOwner, Observer {
            Log.i("ArtistRecentEventTest", "Observe dataListWithOnlyEventId from Fragment = $it")
            viewModel.getCompletedArtistRecentEventList(it)
            Log.i("ArtistRecentEventTest", "----------------------------------------------------")
        })






        // setting add to favorite artist function
        binding.artistDetailAddFavoriteButton.setOnClickListener {
            viewModel.addFavoriteArtist()
        }

        // setting delete favorite artist function
        binding.artistDetailAddFavoriteButtonDone.setOnClickListener {
            viewModel.deleteFavoriteArtist()
        }

        // observing artist isFavorite
        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Log.i("ArtistTest", " isFavorite = $it ")
                binding.artistDetailAddFavoriteButton.visibility = View.GONE
                binding.artistDetailAddFavoriteButtonDone.visibility = View.VISIBLE
            } else {
                Log.i("ArtistTest", " isFavorite = $it ")
                binding.artistDetailAddFavoriteButton.visibility = View.VISIBLE
                binding.artistDetailAddFavoriteButtonDone.visibility = View.GONE
            }
        })

        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }

        binding.recentEventButtonLayer.setOnClickListener {
            if (viewModel.recentEventDataForView.value != null){
                findNavController().navigate(ArtistDetailDialogDirections.navigateToEventdetailFragment(viewModel.recentEventDataForView.value!!))
            }
        }

        return binding.root
    }
}
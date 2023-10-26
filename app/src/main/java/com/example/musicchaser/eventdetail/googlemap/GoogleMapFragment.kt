package com.example.musicchaser.eventdetail.googlemap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentEventDetailBinding
import com.example.musicchaser.databinding.FragmentGoogleMapBinding
import com.example.musicchaser.eventdetail.EventDetailFragmentArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap : GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentGoogleMapBinding.inflate(inflater)


        // setting google map fragment
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        // setting navigation
        binding.googleMapBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onMapReady(p0: GoogleMap) {
        val event = GoogleMapFragmentArgs.fromBundle(requireArguments()).selectedEvent
        mMap = p0
        mMap.addMarker(MarkerOptions().position(LatLng(event.eventLatitude.toDouble(),event.eventLongitude.toDouble())).title(event.eventPlace))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(event.eventLatitude.toDouble(),event.eventLongitude.toDouble()),18F))
    }
}
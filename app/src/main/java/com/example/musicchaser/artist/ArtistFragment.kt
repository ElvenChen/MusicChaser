package com.example.musicchaser.artist

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentArtistBinding
import com.example.musicchaser.databinding.FragmentEventBinding
import com.example.musicchaser.event.EventFragmentDirections
import com.example.musicchaser.event.EventListAdapter
import com.example.musicchaser.event.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistFragment : Fragment() {

    private val viewModel: ArtistViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentArtistBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // setting recyclerView adapter
        binding.artistRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        val adapter = ArtistListAdapter(viewModel.displayArtistDetails)
        binding.artistRecyclerView.adapter = adapter

        //observing list for adapter
        viewModel.dataListForAdapter.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            binding.artistProgressBar.visibility = View.GONE
            binding.layoutSwipeRefreshArtist.isRefreshing = false
        })

        // setting search bar logic
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getSearchedArtistListResult(query)
                    binding.artistProgressBar.visibility = View.VISIBLE

                    val inputMethodManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.getArtistListResult()
                    binding.artistProgressBar.visibility = View.VISIBLE
                }
                return true
            }
        })



        // setting drop down refresh all artist
        val listener = SwipeRefreshLayout.OnRefreshListener {
            viewModel.getArtistListResult()
            binding.artistProgressBar.visibility = View.VISIBLE
            binding.layoutSwipeRefreshArtist.isRefreshing = true
        }
        binding.layoutSwipeRefreshArtist.setOnRefreshListener(listener)



        // setting navigation
        viewModel.navigateToSelectedArtist.observe(viewLifecycleOwner, Observer {
            Log.i("ArtistTest", "Artist Data : $it")
            it?.let {
                findNavController().navigate(
                    ArtistFragmentDirections.navigateToArtistdetailDialog(it)
                )
                viewModel.displayArtistDetailsCompleted()
            }
        })


        return binding.root
    }
}
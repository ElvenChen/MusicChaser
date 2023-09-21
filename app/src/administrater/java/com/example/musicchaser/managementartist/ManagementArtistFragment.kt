package com.example.musicchaser.managementartist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.databinding.adapters.SearchViewBindingAdapter.setOnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicchaser.R
import com.example.musicchaser.artist.ArtistFragmentDirections
import com.example.musicchaser.artist.ArtistListAdapter
import com.example.musicchaser.databinding.FragmentManagementArtistBinding
import com.example.musicchaser.databinding.FragmentManagementEventBinding
import com.example.musicchaser.home.HomeFragmentDirections
import com.example.musicchaser.managementevent.ManagementEventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementArtistFragment : Fragment() {

    private val viewModel: ManagementArtistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementArtistBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        // setting recyclerView adapter
        binding.managementArtistRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = ManagementArtistListAdapter(
            viewModel.displayArtistEditDetails,
            viewModel.displayArtistDeleteDetails
        )
        binding.managementArtistRecyclerView.adapter = adapter


        //observing list for adapter
        viewModel.dataListForAdapter.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            binding.layoutSwipeRefreshArtist.isRefreshing = false
        })


        // setting search bar logic
        binding.managementArtistSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getSearchedArtistListResult(query)

                    val inputMethodManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


        // setting drop down refresh artist list
        val listener = SwipeRefreshLayout.OnRefreshListener {
            Log.i("ManagementArtistTest", "Call drop down refresh API")
            viewModel.getArtistListResult()
            binding.layoutSwipeRefreshArtist.isRefreshing = true
            Log.i("ManagementArtistTest", "drop down refresh API Call Done")
        }
        binding.layoutSwipeRefreshArtist.setOnRefreshListener(listener)


        // setting navigation
        binding.managementArtistBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.navigateToEditArtist.observe(viewLifecycleOwner, Observer {
            Log.i("ManagementArtistTest", "Artist Data : $it")
            it?.let {
                findNavController().navigate(
                    ManagementArtistFragmentDirections.navigateToManagementArtistDetailEditFragment(
                        it
                    )
                )
                viewModel.displayArtistEditDetailsCompleted()
            }
        })

        viewModel.navigateToDeleteArtist.observe(viewLifecycleOwner, Observer {
            Log.i("ManagementArtistTest", "Artist Data : $it")
            it?.let {
                findNavController().navigate(
                    ManagementArtistFragmentDirections.navigateToManagementArtistDetailDeleteDialog(
                        it
                    )
                )
                viewModel.displayArtistDeleteDetailsCompleted()
            }
        })

        binding.managementArtistPostButton.setOnClickListener {
            findNavController().navigate(ManagementArtistFragmentDirections.navigateToManagementArtistDetailPostFragment())
        }



        return binding.root
    }
}
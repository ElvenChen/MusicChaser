package com.example.musicchaser.managementevent

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicchaser.databinding.FragmentManagementEventBinding
import com.example.musicchaser.managementartist.ManagementArtistFragmentDirections
import com.example.musicchaser.managementartist.ManagementArtistListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementEventFragment : Fragment() {

    private val viewModel: ManagementEventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementEventBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        // setting recyclerView adapter
        binding.managementEventRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = ManagementEventListAdapter(
            viewModel.displayEventEditDetails,
            viewModel.displayEventDeleteDetails,
            viewModel.displayAddEventPerformerDetails
        )
        binding.managementEventRecyclerView.adapter = adapter


        // observing list for adapter
        viewModel.dataListForAdapter.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            binding.layoutSwipeRefreshEvent.isRefreshing = false
        })


        // setting search bar logic
        binding.managementEventSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getSearchedEventListResult(query)

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


        // setting drop down refresh event list
        val listener = SwipeRefreshLayout.OnRefreshListener {
            Log.i("ManagementEventTest", "Call drop down refresh API")
            viewModel.getEventListResult()
            binding.layoutSwipeRefreshEvent.isRefreshing = true
            Log.i("ManagementEventTest", "drop down refresh API Call Done")
        }
        binding.layoutSwipeRefreshEvent.setOnRefreshListener(listener)


        // setting navigation
        binding.managementEventBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.navigateToEditEvent.observe(viewLifecycleOwner, Observer {
            Log.i("ManagementEventTest", "Event Data : $it")
            it?.let {
                findNavController().navigate(
                    ManagementEventFragmentDirections.navigateToManagementEventDetailEditFragment(
                        it
                    )
                )
                viewModel.displayEventEditDetailsCompleted()
            }
        })

        viewModel.navigateToDeleteEvent.observe(viewLifecycleOwner, Observer {
            Log.i("ManagementEventTest", "Event Data : $it")
            it?.let {
                findNavController().navigate(
                    ManagementEventFragmentDirections.navigateToManagementEventDetailDeleteDialog(
                        it
                    )
                )
                viewModel.displayEventDeleteDetailsCompleted()
            }
        })

        binding.managementEventPostButton.setOnClickListener {
            findNavController().navigate(ManagementEventFragmentDirections.navigateToManagementEventDetailPostFragment())
        }

        viewModel.navigateToAddEventPerformer.observe(viewLifecycleOwner, Observer {
            Log.i("ManagementEventTest", "Event Data : $it")
            it?.let {
                findNavController().navigate(
                    ManagementEventFragmentDirections.navigateToManagementEventDetailAddPerformerFragment(
                        it
                    )
                )
                viewModel.displayAddEventPerformerDetailsCompleted()
            }
        })



        return binding.root
    }
}
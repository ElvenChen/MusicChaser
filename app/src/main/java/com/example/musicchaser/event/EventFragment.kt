package com.example.musicchaser.event

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventFragment : Fragment() {

    private val viewModel: EventViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentEventBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        // setting recyclerView adapter
        binding.eventRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = EventListAdapter(viewModel.displayEventDetails)
        binding.eventRecyclerView.adapter = adapter

        //observing list for adapter
        viewModel.dataListForAdapter.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            binding.eventProgressBar.visibility = View.GONE
            if (it.isNotEmpty()){
                binding.eventSearchNoResultMessage.visibility = View.GONE
            } else {
                binding.eventSearchNoResultMessage.visibility = View.VISIBLE
            }
            binding.layoutSwipeRefreshEvent.isRefreshing = false
        })


        // setting search bar logic
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("EventTest", "Search triggered")

                if (query != null) {
                    viewModel.getSearchedEventListResult(query)
                    binding.eventProgressBar.visibility = View.VISIBLE

                    val inputMethodManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.getEventListResult()
                    binding.eventProgressBar.visibility = View.VISIBLE
                }
                return true
            }
        })



        // setting drop down refresh all event
        val listener = SwipeRefreshLayout.OnRefreshListener {
            viewModel.getEventListResult()
            binding.eventProgressBar.visibility = View.VISIBLE
            binding.layoutSwipeRefreshEvent.isRefreshing = true
        }
        binding.layoutSwipeRefreshEvent.setOnRefreshListener(listener)


        // setting navigation
        viewModel.navigateToSelectedEvent.observe(viewLifecycleOwner, Observer {
            Log.i("EventTest", "Event Data : $it")
            it?.let {
                findNavController().navigate(
                    EventFragmentDirections.navigateToEventdetailFragment(
                        it
                    )
                )
                viewModel.displayEventDetailsCompleted()
            }
        })


        return binding.root
    }

}
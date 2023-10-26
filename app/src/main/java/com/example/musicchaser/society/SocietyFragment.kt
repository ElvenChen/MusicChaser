package com.example.musicchaser.society

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
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentArtistBinding
import com.example.musicchaser.databinding.FragmentSocietyBinding
import com.example.musicchaser.event.EventFragmentDirections
import com.example.musicchaser.event.EventListAdapter
import com.example.musicchaser.event.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocietyFragment : Fragment() {

    private val viewModel: SocietyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentSocietyBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        // setting recyclerView adapter
        binding.societyThreadRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = ThreadListAdapter(viewModel.displayThreadDetails)
        binding.societyThreadRecyclerView.adapter = adapter



        //observing dataListWithNoAuthorName for making next function call to transfer author ID into author's nickname
        viewModel.dataListForGetAuthorNameCall.observe(viewLifecycleOwner, Observer {
            Log.i("ThreadTest", "Observe dataListForGetAuthorNameCall from Fragment = $it")
            viewModel.getCompletedThreadList(it)
        })

        //observing completed thread list for adapter
        viewModel.dataListForAdapter.observe(viewLifecycleOwner, Observer {
            Log.i("ThreadTest", "Observe Completed thread list from Fragment = $it")
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            binding.societyProgressBar.visibility = View.GONE
            if (it.isNotEmpty()){
                binding.societySearchNoResultMessage.visibility = View.GONE
            } else {
                binding.societySearchNoResultMessage.visibility = View.VISIBLE
            }
            binding.layoutSwipeRefreshThread.isRefreshing = false
        })



        // setting search bar logic
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getSearchedThreadListResult(query)
                    binding.societyProgressBar.visibility = View.VISIBLE

                    val inputMethodManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.getThreadListWithNoAuthorName()
                    binding.societyProgressBar.visibility = View.VISIBLE
                }
                return true
            }
        })



        // setting drop down refresh all thread
        val listener = SwipeRefreshLayout.OnRefreshListener {
            viewModel.getThreadListWithNoAuthorName()
            binding.societyProgressBar.visibility = View.VISIBLE
            binding.layoutSwipeRefreshThread.isRefreshing = true
        }
        binding.layoutSwipeRefreshThread.setOnRefreshListener(listener)



        // setting navigation
        viewModel.navigateToSelectedThread.observe(viewLifecycleOwner, Observer {
            Log.i("ThreadTest", "Thread Data : $it")
            it?.let{
                findNavController().navigate(SocietyFragmentDirections.navigateToSocietydetailFragment(it))
                viewModel.displayThreadDetailsCompleted()
            }
        })

        binding.societyPostButton.setOnClickListener {
            findNavController().navigate(SocietyFragmentDirections.navigateToSocietyPostDialog())
        }

        binding.societySubmissionButtonLayer.setOnClickListener {
            findNavController().navigate(SocietyFragmentDirections.navigateToSocietySubmissionDialog())
        }



        return binding.root
    }
}
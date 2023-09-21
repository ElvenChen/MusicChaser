package com.example.musicchaser.managementuser

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
import com.example.musicchaser.databinding.FragmentManagementEventBinding
import com.example.musicchaser.databinding.FragmentManagementUserBinding
import com.example.musicchaser.managementevent.ManagementEventFragmentDirections
import com.example.musicchaser.managementevent.ManagementEventListAdapter
import com.example.musicchaser.managementevent.ManagementEventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementUserFragment : Fragment() {

    private val viewModel: ManagementUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementUserBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        // setting recyclerView adapter
        binding.managementUserRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = ManagementUserListAdapter(viewModel.displayUserEditDetails)
        binding.managementUserRecyclerView.adapter = adapter


        // observing list for adapter
        viewModel.dataListForAdapter.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            binding.layoutSwipeRefreshUser.isRefreshing = false
        })


        // setting search bar logic
        binding.managementUserSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getSearchedUserListResult(query)

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


        // setting drop down refresh user list
        val listener = SwipeRefreshLayout.OnRefreshListener {
            Log.i("ManagementUserTest", "Call drop down refresh API")
            viewModel.getUserListResult()
            binding.layoutSwipeRefreshUser.isRefreshing = true
            Log.i("ManagementUserTest", "drop down refresh API Call Done")
        }
        binding.layoutSwipeRefreshUser.setOnRefreshListener(listener)



        // setting navigation
        binding.managementUserBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.navigateToEditUser.observe(viewLifecycleOwner, Observer {
            Log.i("ManagementUserTest", "User Data : $it")
            it?.let {
                findNavController().navigate(
                    ManagementUserFragmentDirections.navigateToManagementUserDetailEditFragment(
                        it
                    )
                )
                viewModel.displayUserEditDetailsCompleted()
            }
        })




        return binding.root
    }
}
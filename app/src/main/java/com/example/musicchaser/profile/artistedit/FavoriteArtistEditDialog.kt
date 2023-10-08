package com.example.musicchaser.profile.artistedit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogFavoriteArtistEditBinding
import com.example.musicchaser.databinding.DialogFavoriteEventEditBinding
import com.example.musicchaser.profile.eventedit.FavoriteEventEditListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteArtistEditDialog : AppCompatDialogFragment() {

    private val viewModel: FavoriteArtistEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogFavoriteArtistEditBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // setting loading fade in animation
        binding.root.alpha = 0f
        binding.root.animate().alpha(1f).setDuration(500).start()



        // setting favorite artist recyclerView adapter
        binding.profileFavoriteArtistRecyclerView.layoutManager =
            GridLayoutManager(this.context, 2)
        val favoriteArtistEditAdapter = FavoriteArtistEditListAdapter(viewModel.deleteUserFavoriteArtist)
        binding.profileFavoriteArtistRecyclerView.adapter = favoriteArtistEditAdapter



        //observing userFavoriteArtistIdList for making next function call to transfer artist ID into artist data
        viewModel.userFavoriteArtistIdList.observe(viewLifecycleOwner, Observer {
            Log.i("UserFavoriteArtistEdit", "Observe userFavoriteArtistIdList from Fragment = $it")
            viewModel.getCompletedArtistList(it)
        })

        //observing artistDataList list for adapter
        viewModel.artistDataListForAdapter.observe(viewLifecycleOwner, Observer {
            Log.i("UserFavoriteArtistEdit", "Observe artistDataList from Fragment = $it")
            favoriteArtistEditAdapter.submitList(it)
            favoriteArtistEditAdapter.notifyDataSetChanged()
        })



        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}
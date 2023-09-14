package com.example.musicchaser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        // handle binding
//        viewModel
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // handle navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController


        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {

                    findNavController(R.id.NavHostFragment).navigate(NavigationDirections.navigateToHomeFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_event -> {

                    findNavController(R.id.NavHostFragment).navigate(NavigationDirections.navigateToEventFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_artist -> {

                    findNavController(R.id.NavHostFragment).navigate(NavigationDirections.navigateToArtistFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_society -> {

                    findNavController(R.id.NavHostFragment).navigate(NavigationDirections.navigateToSocietyFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_profile -> {
                    findNavController(R.id.NavHostFragment).navigate(NavigationDirections.navigateToProfileFragment())
//                    when (viewModel.isLoggedIn) {
//                        true -> {
//                            findNavController(R.id.myNavHostFragment).navigate(
//                                NavigationDirections.navigateToProfileFragment(
//                                    viewModel.user.value
//                                )
//                            )
//                        }
//
//                        false -> {
//                            findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToLoginDialog())
//                            return@setOnItemSelectedListener false
//                        }
//                    }
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}
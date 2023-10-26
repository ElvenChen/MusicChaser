package com.example.musicchaser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.musicchaser.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // handle binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel



        // handle navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController



        // handle bottomNavBar visibility
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.eventDetailFragment -> {
                    binding.bottomNavView.visibility = View.GONE
                }
                R.id.eventDetailCommentDialog -> {
                    binding.bottomNavView.visibility = View.GONE
                }
                R.id.popUpMessageDialog -> {
                    binding.bottomNavView.visibility = View.GONE
                }
                R.id.googleMapFragment -> {
                    binding.bottomNavView.visibility = View.GONE
                }

                else -> {
                    binding.bottomNavView.visibility = View.VISIBLE
                }
            }
        }


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
package com.nandaiqbalh.foodapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nandaiqbalh.foodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding!!

	private lateinit var navController: NavController
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		supportActionBar?.hide()
		setContentView(binding.root)

		setUpNavBottom()
	}

	private fun setUpNavBottom(){
		val navHostFragment = supportFragmentManager
			.findFragmentById(R.id.hostFragment) as NavHostFragment

		navController = navHostFragment.navController

		binding.btmNav.setupWithNavController(navController)
	}

	override fun onDestroy() {
		super.onDestroy()

		_binding = null
	}
}
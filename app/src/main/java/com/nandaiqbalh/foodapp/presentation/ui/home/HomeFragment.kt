package com.nandaiqbalh.foodapp.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.nandaiqbalh.foodapp.data.network.models.Meal
import com.nandaiqbalh.foodapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private lateinit var viewModel: HomeViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment

		_binding = FragmentHomeBinding.inflate(inflater, container, false)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.getRandomMeal()
		observeRandomMeal()

	}

	private fun observeRandomMeal(){
		viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, object : Observer<Meal>{
			override fun onChanged(t: Meal?) {
				Glide.with(this@HomeFragment)
					.load(t!!.strMealThumb)
					.into(binding.imgRandomMeal)
			}
		})
	}

	override fun onDestroy() {
		super.onDestroy()

		_binding = null
	}
}
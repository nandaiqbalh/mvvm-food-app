package com.nandaiqbalh.foodapp.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.nandaiqbalh.foodapp.data.network.models.Meal
import com.nandaiqbalh.foodapp.databinding.FragmentHomeBinding
import com.nandaiqbalh.foodapp.presentation.ui.detail.DetailMealActivity

class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private lateinit var viewModel: HomeViewModel

	private lateinit var randomMeal:Meal
	// for key intent to detail
	companion object{
		const val MEAL_ID = "idMealToDetail"
		const val MEAL_NAME = "nameMealToDetail"
		const val MEAL_THUMB = "thumbMealToDetail"
	}

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

		// on click
		onRandomMealClick()
	}

	private fun onRandomMealClick(){
		binding.imgRandomMeal.setOnClickListener{
			val intent = Intent(activity, DetailMealActivity::class.java)
			intent.putExtra(MEAL_ID, randomMeal.idMeal)
			intent.putExtra(MEAL_NAME, randomMeal.strMeal)
			intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
			startActivity(intent)
		}
	}

	private fun observeRandomMeal(){
		viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
		) { t ->
			Glide.with(this@HomeFragment)
				.load(t!!.strMealThumb)
				.into(binding.imgRandomMeal)

			randomMeal = t
		}
	}

	override fun onDestroy() {
		super.onDestroy()

		_binding = null
	}
}
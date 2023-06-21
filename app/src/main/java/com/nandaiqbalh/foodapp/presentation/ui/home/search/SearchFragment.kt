package com.nandaiqbalh.foodapp.presentation.ui.home.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandaiqbalh.foodapp.R
import com.nandaiqbalh.foodapp.databinding.FragmentSearchBinding
import com.nandaiqbalh.foodapp.presentation.ui.favorites.adapter.FavoritesMealAdapter
import com.nandaiqbalh.foodapp.presentation.ui.home.HomeFragment
import com.nandaiqbalh.foodapp.presentation.ui.home.HomeViewModel
import com.nandaiqbalh.foodapp.presentation.ui.home.detail.DetailMealActivity

class SearchFragment : Fragment() {

	private var _binding : FragmentSearchBinding? = null
	private val binding get() = _binding!!

	// view model
	private val viewModel:HomeViewModel = HomeViewModel()
	private val searchMealsAdapter:FavoritesMealAdapter = FavoritesMealAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

		return binding.root

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// rv search
		prepareRVSearch()

		// search functionality
		binding.ivArrorSearch.setOnClickListener {
			searchMeals()
		}

		// observe
		observeSearchMealsLiveData()

		// onclick
		onItemClick()
	}

	private fun observeSearchMealsLiveData(){
		viewModel.observeSearchMealsLiveData().observe(viewLifecycleOwner) {mealsList ->
			searchMealsAdapter.differ.submitList(mealsList)
		}
	}

	private fun searchMeals() {
		val searchQuery = binding.etSearchBox.text.toString()

		if (searchQuery.isNotEmpty()){
			viewModel.searchMeal(searchQuery)
		}
	}

	private fun prepareRVSearch(){
		binding.rvSearchMeals.apply {
			layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

			adapter = searchMealsAdapter
		}
	}

	private fun onItemClick() {
		searchMealsAdapter.onItemClick = {meal ->
			val intent = Intent(requireContext(), DetailMealActivity::class.java)
			intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
			intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
			intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
			startActivity(intent)

		}
	}
	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}
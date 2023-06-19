package com.nandaiqbalh.foodapp.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nandaiqbalh.foodapp.data.network.models.category.CategoryMeals
import com.nandaiqbalh.foodapp.data.network.models.meal.Meal
import com.nandaiqbalh.foodapp.databinding.FragmentHomeBinding
import com.nandaiqbalh.foodapp.presentation.ui.detail.DetailMealActivity
import com.nandaiqbalh.foodapp.presentation.ui.home.adapter.PopularAdapter

class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private lateinit var viewModel: HomeViewModel

	// popular items
	private lateinit var popularAdapter: PopularAdapter

	private lateinit var randomMeal: Meal
	// for key intent to detail
	companion object{
		const val MEAL_ID = "idMealToDetail"
		const val MEAL_NAME = "nameMealToDetail"
		const val MEAL_THUMB = "thumbMealToDetail"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]

		popularAdapter = PopularAdapter()
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

		// recommendations
		viewModel.getRandomMeal()
		observeRandomMeal()

		// popular items
		preparePopularItemsRV()
		viewModel.getPopularItems()
		observePopularItems()


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

	private fun observePopularItems(){
		viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner) { mealList ->

			popularAdapter.setMealList(mealsList = mealList as ArrayList<CategoryMeals>)
		}
	}

	private fun preparePopularItemsRV(){
		binding.recViewMealsPopular.apply {
			layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

			adapter = popularAdapter
		}
	}

	override fun onDestroy() {
		super.onDestroy()

		_binding = null
	}
}
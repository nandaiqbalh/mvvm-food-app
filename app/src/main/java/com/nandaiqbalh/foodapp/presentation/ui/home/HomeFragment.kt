package com.nandaiqbalh.foodapp.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nandaiqbalh.foodapp.data.network.models.mealbycategory.MealByCategory
import com.nandaiqbalh.foodapp.data.network.models.meal.Meal
import com.nandaiqbalh.foodapp.databinding.FragmentHomeBinding
import com.nandaiqbalh.foodapp.presentation.ui.home.detail.DetailMealActivity
import com.nandaiqbalh.foodapp.presentation.ui.home.adapter.CategoryAdapter
import com.nandaiqbalh.foodapp.presentation.ui.home.adapter.PopularAdapter
import com.nandaiqbalh.foodapp.presentation.ui.home.bottomsheet.MealBottomSheetFragment
import com.nandaiqbalh.foodapp.presentation.ui.home.categorypage.CategoriesActivity

class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private lateinit var viewModel: HomeViewModel

	// popular items
	private lateinit var popularAdapter: PopularAdapter

	// category items
	private lateinit var categoryAdapter: CategoryAdapter

	private lateinit var randomMeal: Meal

	// for key intent to detail
	companion object {
		const val MEAL_ID = "idMealToDetail"
		const val MEAL_NAME = "nameMealToDetail"
		const val MEAL_THUMB = "thumbMealToDetail"

		// categories onclick
		const val CATEGORY_NAME = "categoryNameToPage"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]

		popularAdapter = PopularAdapter()
		categoryAdapter = CategoryAdapter()
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
		// on click
		onRandomMealClick()

		// popular items
		preparePopularItemsRV()
		viewModel.getPopularItems()
		observePopularItems()
		// onclick
		onPopularLongClick()

		// categories
		prepareCategoryItemsRV()
		viewModel.getCategories()
		observeCategories()

		// categories onclick
		onCategoryClick()

	}

	private fun onPopularLongClick(){
		popularAdapter.onLongItemClick = {meal ->
			val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
			mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
		}
	}

	private fun onCategoryClick() {
		categoryAdapter.onItemClick = {category ->
			val intent = Intent(activity, CategoriesActivity::class.java)
			intent.putExtra(CATEGORY_NAME, category.strCategory)
			startActivity(intent)

		}
	}

	private fun observeCategories() {
		viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
			categoryAdapter.setCategoryList(categories)
		}
	}

	private fun onRandomMealClick() {
		binding.imgRandomMeal.setOnClickListener {
			val intent = Intent(activity, DetailMealActivity::class.java)
			intent.putExtra(MEAL_ID, randomMeal.idMeal.toString())
			intent.putExtra(MEAL_NAME, randomMeal.strMeal)
			intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
			startActivity(intent)
		}
	}

	private fun observeRandomMeal() {
		viewModel.observeRandomMealLiveData().observe(
			viewLifecycleOwner
		) { t ->
			Glide.with(this@HomeFragment)
				.load(t!!.strMealThumb)
				.into(binding.imgRandomMeal)

			randomMeal = t
		}
	}

	private fun observePopularItems() {
		viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner) { mealList ->

			popularAdapter.setMealList(mealsList = mealList as ArrayList<MealByCategory>)
		}
	}

	private fun preparePopularItemsRV() {
		binding.recViewMealsPopular.apply {
			layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

			adapter = popularAdapter
		}
	}

	private fun prepareCategoryItemsRV() {
		binding.rvCategories.apply {
			layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)

			adapter = categoryAdapter
		}
	}

	override fun onDestroy() {
		super.onDestroy()

		_binding = null
	}
}
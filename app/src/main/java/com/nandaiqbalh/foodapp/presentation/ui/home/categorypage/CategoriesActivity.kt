package com.nandaiqbalh.foodapp.presentation.ui.home.categorypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.nandaiqbalh.foodapp.databinding.ActivityCategoriesBinding
import com.nandaiqbalh.foodapp.presentation.ui.home.HomeFragment
import com.nandaiqbalh.foodapp.presentation.ui.home.adapter.MealsByCategoryAdapater
import com.nandaiqbalh.foodapp.presentation.ui.home.detail.DetailMealActivity

class CategoriesActivity : AppCompatActivity() {

	private var _binding: ActivityCategoriesBinding? = null
	private val binding get() = _binding!!

	private lateinit var viewModel: CategoryViewModel

	private lateinit var mealByCategoryAdapter:MealsByCategoryAdapater

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityCategoriesBinding.inflate(layoutInflater)

		// init
		viewModel = ViewModelProviders.of(this)[CategoryViewModel::class.java]
		mealByCategoryAdapter = MealsByCategoryAdapater()

		// meals by category
		prepareRVMealsByCategory()
		viewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
		observeMealsByCategory()
		// onclick
		onItemClick()

		setContentView(binding.root)
	}

	private fun onItemClick() {
		mealByCategoryAdapter.onItemClick = {mealByCategory ->
			val intent = Intent(this, DetailMealActivity::class.java)
			intent.putExtra(HomeFragment.MEAL_ID, mealByCategory.idMeal)
			intent.putExtra(HomeFragment.MEAL_NAME, mealByCategory.strMeal)
			intent.putExtra(HomeFragment.MEAL_THUMB, mealByCategory.strMealThumb)
			startActivity(intent)

		}
	}


	private fun prepareRVMealsByCategory() {

		binding.rvMealsByCategory.apply {
			layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

			adapter = mealByCategoryAdapter
		}

	}

	private fun observeMealsByCategory() {
		viewModel.observeMealsLiveData().observe(this, Observer { mealList ->
			mealByCategoryAdapter.setMealList(mealList)
			binding.tvCategoryName.text = intent.getStringExtra(HomeFragment.CATEGORY_NAME)
			binding.tvNumOfCategoryMeal.text = "${mealList.size} meal items."
		})
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}
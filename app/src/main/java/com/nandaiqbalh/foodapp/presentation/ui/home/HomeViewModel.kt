package com.nandaiqbalh.foodapp.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nandaiqbalh.foodapp.data.network.api.RetrofitInstance
import com.nandaiqbalh.foodapp.data.network.models.category.Category
import com.nandaiqbalh.foodapp.data.network.models.category.CategoryList
import com.nandaiqbalh.foodapp.data.network.models.mealbycategory.MealByCategoryList
import com.nandaiqbalh.foodapp.data.network.models.mealbycategory.MealByCategory
import com.nandaiqbalh.foodapp.data.network.models.meal.Meal
import com.nandaiqbalh.foodapp.data.network.models.meal.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {

	private var randomMealLiveData = MutableLiveData<Meal>()
	private var popularItemsLiveData = MutableLiveData<List<MealByCategory>>()
	private var categoriesLiveData = MutableLiveData<List<Category>>()

	// bottom sheet
	private var bottomSheetMealLiveData = MutableLiveData<Meal>()

	// search meal
	private var searchMealsLiveData = MutableLiveData<List<Meal>>()

	fun getRandomMeal(){
		RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
			override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
				if (response.body() != null){
					val randomMeal: Meal = response.body()!!.meals[0]

					randomMealLiveData.value = randomMeal

				} else {
					return
				}
			}

			override fun onFailure(call: Call<MealList>, t: Throwable) {
				Log.d("ERROR HOME", "ERROR: ${t.message.toString()}")
			}
		})
	}

	fun getPopularItems(){
		RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealByCategoryList>{
			override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {

				if (response.body() != null){
					popularItemsLiveData.value = response.body()!!.meals
				}
			}

			override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
				Log.d("Error Popular", "Error: ${t.message.toString() }")
			}
		})
	}

	fun getCategories(){
		RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
			override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
				response.body()?.let {
					categoryList ->
					categoriesLiveData.postValue(categoryList.categories)
				}
			}

			override fun onFailure(call: Call<CategoryList>, t: Throwable) {
				Log.d("HomeViewModel", "Error: ${t.message.toString()}")
			}
		})
	}

	fun getMealByID(id: String){
		RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
			override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
				val meal = response.body()!!.meals.first()

				meal?.let {meal
					bottomSheetMealLiveData.postValue(meal)
				}
			}

			override fun onFailure(call: Call<MealList>, t: Throwable) {
				Log.d("HomeViewModel", "Error: ${t.message.toString()}")
			}
		})
	}

	fun searchMeal(searchQuery: String){
		RetrofitInstance.api.searchMeals(searchQuery).enqueue(object : Callback<MealList>{
			override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
				val mealsList = response.body()?.meals

				mealsList?.let {
					searchMealsLiveData.postValue(mealsList)
				}
			}

			override fun onFailure(call: Call<MealList>, t: Throwable) {
				Log.d("HomeViewModel", "Error: ${t.message.toString()}")

			}
		})
	}

	fun observeSearchMealsLiveData(): LiveData<List<Meal>>{
		return searchMealsLiveData
	}

	fun observeMealBottomSheet():LiveData<Meal>{
		return bottomSheetMealLiveData
	}

	fun observeRandomMealLiveData(): LiveData<Meal>{
		return randomMealLiveData
	}

	fun observePopularItemsLiveData(): LiveData<List<MealByCategory>>{
		return popularItemsLiveData
	}

	fun observeCategoriesLiveData(): LiveData<List<Category>>{
		return categoriesLiveData
	}
}
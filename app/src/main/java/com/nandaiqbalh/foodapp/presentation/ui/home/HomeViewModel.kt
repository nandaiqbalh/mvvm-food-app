package com.nandaiqbalh.foodapp.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nandaiqbalh.foodapp.data.network.api.RetrofitInstance
import com.nandaiqbalh.foodapp.data.network.models.category.CategoryList
import com.nandaiqbalh.foodapp.data.network.models.category.CategoryMeals
import com.nandaiqbalh.foodapp.data.network.models.meal.Meal
import com.nandaiqbalh.foodapp.data.network.models.meal.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {

	private var randomMealLiveData = MutableLiveData<Meal>()
	private var popularItemsLiveData = MutableLiveData<List<CategoryMeals>>()

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
		RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<CategoryList>{
			override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

				if (response.body() != null){
					popularItemsLiveData.value = response.body()!!.meals
				}
			}

			override fun onFailure(call: Call<CategoryList>, t: Throwable) {
				Log.d("Error Popular", "Error: ${t.message.toString() }")
			}
		})
	}

	fun observeRandomMealLiveData(): LiveData<Meal>{
		return randomMealLiveData
	}

	fun observePopularItemsLiveData(): LiveData<List<CategoryMeals>>{
		return popularItemsLiveData
	}
}
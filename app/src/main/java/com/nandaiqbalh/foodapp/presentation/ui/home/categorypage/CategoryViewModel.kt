package com.nandaiqbalh.foodapp.presentation.ui.home.categorypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nandaiqbalh.foodapp.data.network.api.RetrofitInstance
import com.nandaiqbalh.foodapp.data.network.models.mealbycategory.MealByCategory
import com.nandaiqbalh.foodapp.data.network.models.mealbycategory.MealByCategoryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(): ViewModel() {

	var mealsLiveData = MutableLiveData<List<MealByCategory>>()

	fun getMealsByCategory(categoryName: String){
		RetrofitInstance.api.getMealByCategory(categoryName).enqueue(object : Callback<MealByCategoryList>{
			override fun onResponse(
				call: Call<MealByCategoryList>,
				response: Response<MealByCategoryList>
			) {
				response.body()?.let {mealList ->
					mealsLiveData.postValue(mealList.meals)
				}
			}

			override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
				Log.d("CategoryViewModel", "Error: ${t.message.toString()}")
			}
		})
	}

	fun observeMealsLiveData(): LiveData<List<MealByCategory>>{
		return mealsLiveData
	}
}
package com.nandaiqbalh.foodapp.presentation.ui.home.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nandaiqbalh.foodapp.data.network.api.RetrofitInstance
import com.nandaiqbalh.foodapp.data.network.models.meal.Meal
import com.nandaiqbalh.foodapp.data.network.models.meal.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMealViewModel:ViewModel() {

	private var mealDetailsLiveData = MutableLiveData<Meal>()

	fun getMealDetails(id:String){
		RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
			override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
				if (response.body() != null){
					mealDetailsLiveData.value = response.body()!!.meals[0]
				} else {
					return
				}
			}

			override fun onFailure(call: Call<MealList>, t: Throwable) {
				Log.d("MealDetailLog", "Error: ${t.message.toString() }")
			}
		})
	}

	fun observeMealDetailLiveData():LiveData<Meal>{
		return mealDetailsLiveData
	}
}
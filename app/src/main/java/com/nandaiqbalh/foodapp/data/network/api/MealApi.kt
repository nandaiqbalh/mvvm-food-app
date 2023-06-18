package com.nandaiqbalh.foodapp.data.network.api

import com.nandaiqbalh.foodapp.data.network.models.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

	@GET("random.php")
	fun getRandomMeal() : Call<MealList>
}
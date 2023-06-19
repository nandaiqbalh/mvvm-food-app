package com.nandaiqbalh.foodapp.data.network.api

import com.nandaiqbalh.foodapp.data.network.models.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

	@GET("random.php")
	fun getRandomMeal() : Call<MealList>

	@GET("lookup.php?")
	fun getMealDetails(
		@Query("i") id:String
	) : Call<MealList>
}
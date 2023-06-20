package com.nandaiqbalh.foodapp.data.network.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nandaiqbalh.foodapp.data.network.models.meal.Meal

@Dao
interface MealDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun upsertMeal(meal: Meal)

	@Delete
	suspend fun deleteMeal(meal: Meal)

	@Query("SELECT * FROM mealdatabase")
	fun getAllMeals(): LiveData<List<Meal>>

}
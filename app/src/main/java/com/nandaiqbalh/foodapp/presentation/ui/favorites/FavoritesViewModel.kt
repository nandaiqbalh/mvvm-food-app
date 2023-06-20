package com.nandaiqbalh.foodapp.presentation.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nandaiqbalh.foodapp.data.network.local.database.MealDatabase
import com.nandaiqbalh.foodapp.data.network.models.meal.Meal

class FavoritesViewModel(
	private val mealDatabase: MealDatabase
) : ViewModel(){

	private var favoritesMealLiveData = mealDatabase.mealDao().getAllMeals()


	fun observeFavoritesMealLiveData(): LiveData<List<Meal>>{
		return favoritesMealLiveData
	}
}
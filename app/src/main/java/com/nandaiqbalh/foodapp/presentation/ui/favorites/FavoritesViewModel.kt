package com.nandaiqbalh.foodapp.presentation.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.foodapp.data.network.local.database.MealDatabase
import com.nandaiqbalh.foodapp.data.network.models.meal.Meal
import kotlinx.coroutines.launch

class FavoritesViewModel(
	private val mealDatabase: MealDatabase
) : ViewModel(){

	private var favoritesMealLiveData = mealDatabase.mealDao().getAllMeals()


	fun observeFavoritesMealLiveData(): LiveData<List<Meal>>{
		return favoritesMealLiveData
	}

	fun insertMeal(meal: Meal){
		viewModelScope.launch {
			mealDatabase.mealDao().upsertMeal(meal)
		}
	}
	fun deleteMeal(meal: Meal){
		viewModelScope.launch {
			mealDatabase.mealDao().deleteMeal(meal)
		}
	}
}
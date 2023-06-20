package com.nandaiqbalh.foodapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nandaiqbalh.foodapp.data.network.local.database.MealDatabase
import com.nandaiqbalh.foodapp.presentation.ui.favorites.FavoritesViewModel
import com.nandaiqbalh.foodapp.presentation.ui.home.detail.DetailMealViewModel

class FavoritesViewModelFactory(
	private val mealDatabase: MealDatabase
) : ViewModelProvider.NewInstanceFactory() {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return FavoritesViewModel(mealDatabase) as T
	}
}
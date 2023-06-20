package com.nandaiqbalh.foodapp.data.network.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nandaiqbalh.foodapp.data.network.local.converter.MealTypeConvertor
import com.nandaiqbalh.foodapp.data.network.local.dao.MealDao
import com.nandaiqbalh.foodapp.data.network.models.meal.Meal

@Database(
	entities =[Meal::class],
	version = 1,
	exportSchema = false
)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDatabase: RoomDatabase() {

	abstract fun mealDao(): MealDao

	companion object{

		@Volatile
		var  INSTANCE:MealDatabase? = null

		@Synchronized
		fun getInstance(context:Context): MealDatabase{

			if (INSTANCE == null){
				INSTANCE = Room.databaseBuilder(
					context,
					MealDatabase::class.java,
					"meal.db"
				).fallbackToDestructiveMigration()
					.build()
			}

			return INSTANCE as MealDatabase
		}
	}

}
package com.nandaiqbalh.foodapp.data.network.local.converter

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConvertor {

	// it used when we want to insert data from json (or whatever) to DATABASE -> because room only know primitive type data
	@TypeConverter
	fun fromAnyToString(attribute: Any?): String{

		if (attribute == null){
			return ""
		}

		return attribute as String
	}

	@TypeConverter
	fun fromStringToAny(attribute: String?) : Any{

		if (attribute == null){
			return ""
		}

		return attribute
	}
}
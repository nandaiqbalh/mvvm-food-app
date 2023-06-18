package com.nandaiqbalh.foodapp.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

import com.nandaiqbalh.foodapp.R
import com.nandaiqbalh.foodapp.data.network.api.RetrofitInstance
import com.nandaiqbalh.foodapp.data.network.models.Meal
import com.nandaiqbalh.foodapp.data.network.models.MealList
import com.nandaiqbalh.foodapp.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment

		_binding = FragmentHomeBinding.inflate(inflater, container, false)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList>{
			override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
				if (response.body() != null){
					val randomMeal: Meal = response.body()!!.meals[0]

					Log.d("MEAL", "ID ${randomMeal.idMeal} name ${randomMeal.strMeal}")

					Glide.with(this@HomeFragment)
						.load(randomMeal.strMealThumb)
						.into(binding.imgRandomMeal)

				} else {
					return
				}
			}

			override fun onFailure(call: Call<MealList>, t: Throwable) {
				Log.d("ERROR HOME", "ERROR: ${t.message.toString()}")
			}
		})
	}

	override fun onDestroy() {
		super.onDestroy()

		_binding = null
	}
}
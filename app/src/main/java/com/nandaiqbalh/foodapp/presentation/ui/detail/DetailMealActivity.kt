package com.nandaiqbalh.foodapp.presentation.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.nandaiqbalh.foodapp.R
import com.nandaiqbalh.foodapp.databinding.ActivityDetailMealBinding
import com.nandaiqbalh.foodapp.presentation.ui.home.HomeFragment

class DetailMealActivity : AppCompatActivity() {

	private var _binding: ActivityDetailMealBinding? = null
	private val binding get() = _binding!!

	private lateinit var mealId: String
	private lateinit var mealName:String
	private lateinit var mealThumb: String



	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		_binding = ActivityDetailMealBinding.inflate(layoutInflater)

		getInfoFromIntent()

		setInfoInViews()

		setContentView(binding.root)
	}

	private fun setInfoInViews(){
		Glide.with(this)
			.load(mealThumb)
			.into(binding.imgMealDetail)

		binding.collapsingToolbar.title = mealName
		binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
		binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

	}

	private fun getInfoFromIntent(){
		val intent = intent

		mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
		mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
		mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}
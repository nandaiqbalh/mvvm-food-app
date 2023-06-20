package com.nandaiqbalh.foodapp.presentation.ui.home.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
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

	private lateinit var youtubeLink: String

	private lateinit var viewModel: DetailMealViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		_binding = ActivityDetailMealBinding.inflate(layoutInflater)

		getInfoFromIntent()

		setInfoInViews()

		viewModel = ViewModelProviders.of(this)[DetailMealViewModel::class.java]
		viewModel.getMealDetails(mealId)
		observeMealDetailLiveData()

		onYoutubeImgClick()

		onLoadingCase()

		setContentView(binding.root)
	}

	private fun onYoutubeImgClick(){
		binding.imgYoutube.setOnClickListener {
			val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
			startActivity(intent)
		}
	}
	private fun observeMealDetailLiveData(){
		viewModel.observeMealDetailLiveData().observe(this
		) { meal ->
			onResponse()
			youtubeLink = meal.strYoutube

			binding.tvCategoryDetail.text = "Category: ${meal.strCategory}"
			binding.tvLocationDetail.text = "Location: ${meal.strArea}"

			if (meal.strDrinkAlternate == null){
				binding.tvDrinkDetail.visibility = View.INVISIBLE
			} else {
				binding.tvDrinkDetail.text = "Alternate Drink: ${meal.strDrinkAlternate}"
			}
			binding.tvInstructionDetail.text = meal.strInstructions
		}
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

	private fun onLoadingCase(){
		binding.pbDetail.visibility = View.VISIBLE

		binding.fabAddToFavorites.visibility = View.INVISIBLE
		binding.tvInstructionTitle.visibility = View.INVISIBLE
		binding.tvCategoryDetail.visibility = View.INVISIBLE
		binding.tvLocationDetail.visibility = View.INVISIBLE
		binding.tvDrinkDetail.visibility = View.INVISIBLE
		binding.imgYoutube.visibility = View.INVISIBLE
	}

	private fun onResponse(){
		binding.pbDetail.visibility = View.INVISIBLE

		binding.fabAddToFavorites.visibility = View.VISIBLE
		binding.tvInstructionTitle.visibility = View.VISIBLE
		binding.tvCategoryDetail.visibility = View.VISIBLE
		binding.tvLocationDetail.visibility = View.VISIBLE
		binding.tvDrinkDetail.visibility = View.VISIBLE
		binding.imgYoutube.visibility = View.VISIBLE
	}
	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}
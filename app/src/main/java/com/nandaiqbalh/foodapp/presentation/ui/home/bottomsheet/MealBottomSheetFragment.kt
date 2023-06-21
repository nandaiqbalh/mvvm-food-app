package com.nandaiqbalh.foodapp.presentation.ui.home.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nandaiqbalh.foodapp.data.network.models.meal.Meal
import com.nandaiqbalh.foodapp.databinding.FragmentMealBottomSheetBinding
import com.nandaiqbalh.foodapp.presentation.ui.home.HomeFragment
import com.nandaiqbalh.foodapp.presentation.ui.home.HomeViewModel
import com.nandaiqbalh.foodapp.presentation.ui.home.detail.DetailMealActivity

private const val MEAL_ID = "mealId"

class MealBottomSheetFragment : BottomSheetDialogFragment() {
	private var mealId: String? = null

	private var _binding: FragmentMealBottomSheetBinding? = null
	private val binding get() = _binding!!

	private var viewModel: HomeViewModel = HomeViewModel()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			mealId = it.getString(MEAL_ID)
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		_binding = FragmentMealBottomSheetBinding.inflate(layoutInflater, container, false)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		mealId?.let { viewModel.getMealByID(it) }

		// observe
		observeMealBottomSheet()

		// bottom sheet onclick
		onBottomSheetDialogClick()

	}

	private fun onBottomSheetDialogClick() {
		binding.bottomSheetMeal.setOnClickListener {

			val intent = Intent(context, DetailMealActivity::class.java)
			intent.putExtra(HomeFragment.MEAL_ID, mealToDetail.idMeal)
			intent.putExtra(HomeFragment.MEAL_NAME, mealToDetail.strMeal)
			intent.putExtra(HomeFragment.MEAL_THUMB, mealToDetail.strMealThumb)
			startActivity(intent)

		}
	}


	private lateinit var mealToDetail: Meal

	private fun observeMealBottomSheet() {
		viewModel.observeMealBottomSheet().observe(viewLifecycleOwner) { meal ->
			Glide.with(this@MealBottomSheetFragment)
				.load(meal.strMealThumb)
				.into(binding.ivMealBottomSheet)

			binding.tvCategoryBottomSheet.text = meal.strCategory
			binding.tvLocationBottomSheet.text = meal.strArea
			binding.tvNameMealBottomSheet.text = meal.strMeal

			mealToDetail = meal
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

	companion object {
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String) =
			MealBottomSheetFragment().apply {
				arguments = Bundle().apply {
					putString(MEAL_ID, param1)
				}
			}
	}
}

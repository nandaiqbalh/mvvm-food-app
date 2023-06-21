package com.nandaiqbalh.foodapp.presentation.ui.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nandaiqbalh.foodapp.R
import com.nandaiqbalh.foodapp.databinding.FragmentCategoriesBinding
import com.nandaiqbalh.foodapp.presentation.ui.home.HomeFragment
import com.nandaiqbalh.foodapp.presentation.ui.home.HomeViewModel
import com.nandaiqbalh.foodapp.presentation.ui.home.adapter.CategoryAdapter
import com.nandaiqbalh.foodapp.presentation.ui.home.categorypage.CategoriesActivity


class CategoriesFragment : Fragment() {

	private var _binding: FragmentCategoriesBinding? = null
	private val binding get() = _binding!!

	private lateinit var categoryAdapter: CategoryAdapter
	private lateinit var viewModel: HomeViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// init
		viewModel = HomeViewModel()

	}
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		_binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// category grid list
		prepareRVCategory()
		viewModel.getCategories()
		observeCategory()

		// onclick
		onCategoryClick()

	}

	private fun onCategoryClick() {
		categoryAdapter.onItemClick = {category ->
			val intent = Intent(activity, CategoriesActivity::class.java)
			intent.putExtra(HomeFragment.CATEGORY_NAME, category.strCategory)
			startActivity(intent)

		}
	}

	private fun observeCategory(){
		viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner){categories ->
			categoryAdapter.setCategoryList(categories)
		}
	}

	private fun prepareRVCategory(){
		categoryAdapter = CategoryAdapter()

		binding.rvCategoriesMeal.apply {
			layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)

			adapter = categoryAdapter
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}
package com.nandaiqbalh.foodapp.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandaiqbalh.foodapp.data.network.local.database.MealDatabase
import com.nandaiqbalh.foodapp.databinding.FragmentFavoritesBinding
import com.nandaiqbalh.foodapp.presentation.ui.favorites.adapter.FavoritesMealAdapter
import com.nandaiqbalh.foodapp.util.FavoritesViewModelFactory

class FavoritesFragment : Fragment() {

	private var _binding: FragmentFavoritesBinding? = null
	private val binding get() = _binding!!

	private lateinit var viewModel: FavoritesViewModel

	private lateinit var favoritesMealAdapter: FavoritesMealAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// init
		val mealDatabase = MealDatabase.getInstance(requireContext())
		val viewModelFactory = FavoritesViewModelFactory(mealDatabase)
		viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)

	}
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// rv
		prepareRVFavoritesMeal()

		// observe
		observeFavoritesMeal()
	}

	private fun prepareRVFavoritesMeal(){
		favoritesMealAdapter = FavoritesMealAdapter()

		binding.rvFavoritesMeal.apply {
			layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

			adapter = favoritesMealAdapter
		}
	}

	private fun observeFavoritesMeal(){
		viewModel.observeFavoritesMealLiveData().observe(viewLifecycleOwner){meals ->
			favoritesMealAdapter.differ.submitList(meals)
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}
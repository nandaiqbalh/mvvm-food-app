package com.nandaiqbalh.foodapp.presentation.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nandaiqbalh.foodapp.data.network.local.database.MealDatabase
import com.nandaiqbalh.foodapp.databinding.FragmentFavoritesBinding
import com.nandaiqbalh.foodapp.presentation.ui.favorites.adapter.FavoritesMealAdapter
import com.nandaiqbalh.foodapp.presentation.ui.home.HomeFragment
import com.nandaiqbalh.foodapp.presentation.ui.home.detail.DetailMealActivity
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

		// onclick
		onItemClick()

		// move action
		setMoveAction()
	}

	private fun setMoveAction(){

		val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
			ItemTouchHelper.UP or ItemTouchHelper.DOWN,
			ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
		){
			override fun onMove(
				recyclerView: RecyclerView,
				viewHolder: RecyclerView.ViewHolder,
				target: RecyclerView.ViewHolder
			): Boolean {
				// set action when drag action
				return true
			}

			override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
				// set action when swipe action
				val position = viewHolder.adapterPosition

				// delete meal
				val deletedMeal = favoritesMealAdapter.differ.currentList[position]
				viewModel.deleteMeal(deletedMeal)

				// show snackbar
				Snackbar.make(requireView(), "Meal deleted successfully!", Snackbar.LENGTH_LONG).setAction(
					"Undo",
					View.OnClickListener {
						viewModel.insertMeal(deletedMeal)
						Log.d("Favorites Fragment", "Undo button was pressed!")
					}
				).show()
			}
		}

		// attach to recycler view
		ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavoritesMeal)
	}

	private fun prepareRVFavoritesMeal(){
		favoritesMealAdapter = FavoritesMealAdapter()

		binding.rvFavoritesMeal.apply {
			layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

			adapter = favoritesMealAdapter
		}
	}
	private fun onItemClick() {
		favoritesMealAdapter.onItemClick = {meal ->
			val intent = Intent(requireContext(), DetailMealActivity::class.java)
			intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
			intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
			intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
			startActivity(intent)

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
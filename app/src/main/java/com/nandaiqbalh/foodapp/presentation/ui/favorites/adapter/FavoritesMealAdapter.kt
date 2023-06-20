package com.nandaiqbalh.foodapp.presentation.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nandaiqbalh.foodapp.data.network.models.meal.Meal
import com.nandaiqbalh.foodapp.databinding.FavoritesMealItemBinding

class FavoritesMealAdapter(): RecyclerView.Adapter<FavoritesMealAdapter.FavoritesMealViewHolder>() {

	private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
		override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
			return oldItem.idMeal == newItem.idMeal
		}

		override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
			return oldItem == newItem
		}
	}

	val differ = AsyncListDiffer(this, diffUtil)

	class FavoritesMealViewHolder(val binding:FavoritesMealItemBinding): RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealViewHolder {
		return FavoritesMealViewHolder(FavoritesMealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
	}

	override fun onBindViewHolder(holder: FavoritesMealViewHolder, position: Int) {

		val meal = differ.currentList[position]

		Glide.with(holder.itemView)
			.load(meal.strMealThumb)
			.into(holder.binding.ivMealItem)

		holder.binding.tvNameMealItem.text = meal.strMeal
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}
}
package com.nandaiqbalh.foodapp.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nandaiqbalh.foodapp.data.network.models.mealbycategory.MealByCategory
import com.nandaiqbalh.foodapp.databinding.MealItemBinding

class MealsByCategoryAdapater(): RecyclerView.Adapter<MealsByCategoryAdapater.MealsByCategoryViewHolder>() {

	private var mealList = ArrayList<MealByCategory>()

	fun setMealList(mealList: List<MealByCategory>){
		this.mealList = mealList as ArrayList<MealByCategory>
		notifyDataSetChanged()
	}

	class MealsByCategoryViewHolder(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsByCategoryViewHolder {
		return MealsByCategoryViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
	}

	override fun onBindViewHolder(holder: MealsByCategoryViewHolder, position: Int) {

		Glide.with(holder.itemView)
			.load(mealList[position].strMealThumb)
			.into(holder.binding.ivMealItem)

		holder.binding.tvNameMealItem.text = mealList[position].strMeal
	}

	override fun getItemCount(): Int {
		return mealList.size
	}
}
package com.nandaiqbalh.foodapp.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nandaiqbalh.foodapp.data.network.models.mealbycategory.MealByCategory
import com.nandaiqbalh.foodapp.databinding.PopularItemsBinding

class PopularAdapter :RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

	private var mealsList= ArrayList<MealByCategory>()

	var onLongItemClick: ((MealByCategory) -> Unit)? = null

	fun setMealList(mealsList: ArrayList<MealByCategory>) {
		this.mealsList = mealsList
		notifyDataSetChanged()
	}

	class PopularViewHolder(val binding: PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
		return PopularViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
	}

	override fun onBindViewHolder(holder: PopularViewHolder, @SuppressLint("RecyclerView") position: Int) {
		holder.binding.apply {
			Glide.with(holder.itemView)
				.load(mealsList[position].strMealThumb)
				.into(ivPopularMealItems)
		}

		holder.itemView.setOnLongClickListener {
			onLongItemClick?.invoke(mealsList[position])

			true
		}
	}

	override fun getItemCount(): Int {
		return mealsList.size
	}
}


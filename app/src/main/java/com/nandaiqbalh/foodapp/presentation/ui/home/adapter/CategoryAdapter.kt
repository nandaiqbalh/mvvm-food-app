package com.nandaiqbalh.foodapp.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nandaiqbalh.foodapp.data.network.models.category.Category
import com.nandaiqbalh.foodapp.databinding.CategoryItemsBinding

class CategoryAdapter():RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

	private var categoryList = ArrayList<Category>()

	// category onclick
	var onItemClick: ((Category) ->Unit)? = null

	fun setCategoryList(categoryList: List<Category>){
		this.categoryList = categoryList as ArrayList<Category>
		notifyDataSetChanged()
	}


	class CategoryViewHolder(val binding: CategoryItemsBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
		return CategoryViewHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context)))
	}

	override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
		Glide.with(holder.itemView)
			.load(categoryList[position].strCategoryThumb)
			.into(holder.binding.ivCategoryItem)

		holder.binding.tvCategoryItems.text = categoryList[position].strCategory

		// category onclick
		holder.itemView.setOnClickListener {
			onItemClick!!.invoke(categoryList[position])
		}
	}

	override fun getItemCount(): Int {
		return categoryList.size
	}
}
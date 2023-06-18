package com.nandaiqbalh.foodapp.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nandaiqbalh.foodapp.R

class FavoritesFragment : Fragment() {


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_favorites, container, false)
	}


}
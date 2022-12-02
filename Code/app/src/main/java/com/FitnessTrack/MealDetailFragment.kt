package com.fitnesstrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageButton
import androidx.navigation.findNavController

class MealDetailFragment : Fragment(R.layout.fragment_detail_meal) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val addMeal = view.findViewById<ImageButton>(R.id.addMeal)

        addMeal.setOnClickListener{
            view.findNavController().
            navigate(R.id.action_mealDetailFragment_to_mealEditFragment)
        }
    }
}
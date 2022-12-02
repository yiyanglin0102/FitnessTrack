package com.fitnesstrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class MealEditFragment : Fragment() {

    private lateinit var submit_meal:Button
    private lateinit var cancel_meal:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        submit_meal = view.findViewById<Button>(R.id.submit_meal)
        cancel_meal = view.findViewById<Button>(R.id.cancel_meal)

        submit_meal.setOnClickListener {

            view.findNavController().
            navigate(R.id.action_mealEditFragment_pop)
        }
        cancel_meal.setOnClickListener {
            view.findNavController().
            navigate(R.id.action_mealEditFragment_pop)
        }
    }
}
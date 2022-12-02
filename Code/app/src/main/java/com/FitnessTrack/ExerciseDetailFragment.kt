package com.fitnesstrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageButton
import androidx.navigation.findNavController

class ExerciseDetailFragment : Fragment(R.layout.fragment_detail_exercise) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val addExercise = view.findViewById<ImageButton>(R.id.addExercise)

        addExercise.setOnClickListener{
            view.findNavController().
            navigate(R.id.action_exerciseDetailFragment_to_exerciseEditFragment)
        }
    }
}
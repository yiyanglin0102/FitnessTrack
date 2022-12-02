package com.fitnesstrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class ExerciseEditFragment : Fragment() {

    private lateinit var submit_exercise:Button
    private lateinit var cancel_exercise:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        submit_exercise = view.findViewById<Button>(R.id.submit_exercise)
        cancel_exercise = view.findViewById<Button>(R.id.cancel_exercise)

        submit_exercise.setOnClickListener {

            view.findNavController().
            navigate(R.id.action_exerciseEditFragment_pop)
        }
        cancel_exercise.setOnClickListener {
            view.findNavController().
            navigate(R.id.action_exerciseEditFragment_pop)
        }
    }
}
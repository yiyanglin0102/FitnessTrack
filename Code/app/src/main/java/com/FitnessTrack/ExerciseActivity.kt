package com.fitnesstrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitnesstrack.firebase.models.User


class ExerciseActivity : AppCompatActivity() {
    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
    }

    fun setUserDataUI(user: User) {
        mUserDetails = user
    }

}
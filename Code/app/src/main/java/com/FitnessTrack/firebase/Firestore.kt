package com.fitnesstrack.firebase

import android.app.Activity
import android.util.Log
import com.fitnesstrack.MainActivity
import com.fitnesstrack.MyProfileActivity
import com.fitnesstrack.firebase.models.User
import com.fitnesstrack.SignInActivity
import com.fitnesstrack.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class Firestore {
    private val mFirestore = FirebaseFirestore.getInstance()


    fun registerUser(activity: SignUpActivity, userInfo: User) {
        mFirestore.collection("Users").document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error")
            }
    }

    fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun loadUserData(activity: Activity) {
        mFirestore.collection("Users").document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->

                val loggedInUser = document.toObject(User::class.java)!!
                when (activity) {
                    is SignInActivity -> {
                        activity.signInSuccess(loggedInUser)
                    }
                    is MainActivity -> {
                        activity.updateNavigationUserDetails(loggedInUser)
                    }
                    is MyProfileActivity -> {
                        activity.setUserDataUI(loggedInUser)
                    }
                }

            }.addOnFailureListener { e ->
                when (activity) {
                    is SignInActivity -> {
//                        activity.hideProgressDialog()
                    }
                    is MainActivity -> {
//                        activity.hideProgressDialog()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting loggedIn user details",
                    e
                )
            }
    }
}

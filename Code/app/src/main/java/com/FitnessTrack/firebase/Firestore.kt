package com.fitnesstrack.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.fitnesstrack.*
import com.fitnesstrack.firebase.models.User
import com.fitnesstrack.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class Firestore {
    private val mFirestore = FirebaseFirestore.getInstance()


    fun registerUser(activity: SignUpActivity, userInfo: User) {
        mFirestore.collection(Constants.USERS).document(getCurrentUserID())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error")
            }
    }

    fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun updateUserGoalData(activity: GoalActivity, userHashMap: HashMap<String, Any>) {
        mFirestore.collection(Constants.USERS) // Collection Name
            .document(getCurrentUserID()) // Document ID
            .update(userHashMap) // A hashmap of fields which are to be updated.
            .addOnSuccessListener {
                // Profile data is updated successfully.
                Log.e(activity.javaClass.simpleName, "Goal Data updated successfully!")

                Toast.makeText(activity, "Goal updated successfully!", Toast.LENGTH_SHORT).show()

                // Notify the success result.
                activity.goalUpdateSuccess()
            }
            .addOnFailureListener { e ->
//                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    e
                )
                Toast.makeText(activity, "Error update goal!", Toast.LENGTH_SHORT).show()

            }
    }


    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String, Any>) {
        mFirestore.collection(Constants.USERS) // Collection Name
            .document(getCurrentUserID()) // Document ID
            .update(userHashMap) // A hashmap of fields which are to be updated.
            .addOnSuccessListener {
                // Profile data is updated successfully.
                Log.e(activity.javaClass.simpleName, "Profile Data updated successfully!")

                Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()

                // Notify the success result.
                activity.profileUpdateSuccess()
            }
            .addOnFailureListener { e ->
//                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    e
                )
                Toast.makeText(activity, "Error update profile!", Toast.LENGTH_SHORT).show()

            }
    }

    fun loadUserData(activity: Activity) {
        mFirestore.collection(Constants.USERS).document(getCurrentUserID())
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
                    is GoalActivity -> {
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

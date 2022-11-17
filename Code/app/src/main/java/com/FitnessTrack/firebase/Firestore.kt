package com.fitnesstrack.firebase

import android.util.Log
import com.fitnesstrack.firebase.models.User
import com.fitnesstrack.SignInActivity
import com.fitnesstrack.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class Firestore {
    private val mFirestore = FirebaseFirestore.getInstance()


    fun registerUser(activity: SignUpActivity, userInfo : User){
        mFirestore.collection("Users").
        document(getCurrentUserId()).set(userInfo, SetOptions.merge())
            .addOnSuccessListener{
                activity.userRegisteredSuccess()
            }.addOnFailureListener {
                e -> Log.e(activity.javaClass.simpleName, "Error")
            }
    }

    fun getCurrentUserId() : String {

        return FirebaseAuth.getInstance().currentUser!!.uid
    }






}
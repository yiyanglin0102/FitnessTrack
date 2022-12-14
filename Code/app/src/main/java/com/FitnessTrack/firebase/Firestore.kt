package com.fitnesstrack.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.fitnesstrack.activities.*
import com.fitnesstrack.firebase.models.Board
import com.fitnesstrack.firebase.models.User
import com.fitnesstrack.utilities.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class Firestore {

    private val firestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: User) {
        firestore.collection(Constants.USERS).document(getCurrentUserID())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error")
            }
    }

    fun getBoardDetails(activity: TaskListActivity, documentId: String) {
        firestore.collection(Constants.BOARDS)
            .document(documentId)
            .get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.toString())
                val board = document.toObject(Board::class.java)!!
                board.documentId = document.id
                activity.boardDetails(board)
            }
            .addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error while creating a board.", e)
            }
    }

    fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun createBoard(activity: CreateBoardActivity, board: Board) {
        firestore.collection(Constants.BOARDS)
            .document()
            .set(board, SetOptions.merge())
            .addOnSuccessListener {
                Log.e(activity.javaClass.simpleName, "Board created successfully.")
                Toast.makeText(activity, "Board created successfully.", Toast.LENGTH_SHORT).show()
                activity.boardCreatedSuccessfully()
            }
            .addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    e
                )
            }
    }

    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String, Any>) {
        firestore.collection(Constants.USERS) // Collection Name
            .document(getCurrentUserID()) // Document ID
            .update(userHashMap) // A hashmap of fields which are to be updated.
            .addOnSuccessListener {
                Log.e(activity.javaClass.simpleName, "Profile Data updated successfully!")
                Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                activity.profileUpdateSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    e
                )
                Toast.makeText(activity, "Error update profile!", Toast.LENGTH_SHORT).show()
            }
    }

    fun addUpdateTaskList(activity: Activity, board: Board) {
        val taskListHashMap = HashMap<String, Any>()
        taskListHashMap[Constants.TASK_LIST] = board.taskList

        firestore.collection(Constants.BOARDS)
            .document(board.documentId)
            .update(taskListHashMap)
            .addOnSuccessListener {

                if (activity is TaskListActivity) {
                    activity.addUpdateTaskListSuccess()
                } else if (activity is CardDetailsActivity) {
                    activity.addUpdateCardDetailsSuccess()
                }
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error while creating a board.", e)
            }
    }

    fun loadUserData(activity: Activity, isToReadBoardsList: Boolean = false) {
        firestore.collection(Constants.USERS).document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                val loggedInUser = document.toObject(User::class.java)!!
                when (activity) {
                    is SignInActivity -> {
                        activity.signInSuccess(loggedInUser)
                    }
                    is MainActivity -> {
                        activity.updateNavigationUserDetails(loggedInUser, isToReadBoardsList)
                    }
                    is MyProfileActivity -> {
                        activity.setUserDataUI(loggedInUser)
                    }
                }

            }.addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting loggedIn user details",
                    e
                )
            }
    }

    fun getBoardsList(activity: MainActivity) {
        firestore.collection(Constants.BOARDS)
            .whereArrayContains(Constants.ASSIGNED_TO, getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.documents.toString())
                val boardsList: ArrayList<Board> = ArrayList()

                for (i in document.documents) {
                    val board = i.toObject(Board::class.java)!!
                    board.documentId = i.id
                    boardsList.add(board)
                }

                activity.populateBoardsListToUI(boardsList)
            }
            .addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error while creating a board.", e)
            }
    }
}
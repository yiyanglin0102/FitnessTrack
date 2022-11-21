package com.fitnesstrack

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.fitnesstrack.firebase.Firestore
import com.fitnesstrack.firebase.models.User
import kotlinx.android.synthetic.main.activity_exercise.*
import com.fitnesstrack.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ExerciseActivity : AppCompatActivity() {

    companion object {
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }

    private var mSelectedImageFileUri: Uri? = null
    private var mExerciseImageURL: String = ""
    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        setupActionBar()

        fab_create_board1.setOnClickListener{
            startActivity(Intent(this, CreateExerciseActivity::class.java))
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageChooser()
            } else {
                Toast.makeText(
                    this,
                    "Oops, you just denied the permission for storage. You can also allow it from settings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_exercise_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = resources.getString(R.string.exercise_title)
        }

        toolbar_exercise_activity.setNavigationOnClickListener { onBackPressed() }
    }



    private fun showImageChooser() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {
            mSelectedImageFileUri = data.data


        }
    }

    fun setUserDataUI(user: User) {

        mUserDetails = user

    }

    private fun updateUserExerciseData() {

        val userHashMap = HashMap<String, Any>()

        if (mExerciseImageURL.isNotEmpty() && mExerciseImageURL != mUserDetails.image) {
            userHashMap[Constants.IMAGE] = mExerciseImageURL
        }


        // Update the data in the database.
        Firestore().updateUserExerciseData(this@ExerciseActivity, userHashMap)
    }






//
//
//
//    fun populateBoardsListToUI(boardsList: ArrayList<Board>) {
//
//        if (boardsList.size > 0) {
//
//            rv_boards_list.visibility = View.VISIBLE
//            tv_no_boards_available.visibility = View.GONE
//
//            rv_boards_list.layoutManager = LinearLayoutManager(this@MainActivity)
//            rv_boards_list.setHasFixedSize(true)
//
//            // Create an instance of BoardItemsAdapter and pass the boardList to it.
//            val adapter = BoardItemsAdapter(this@MainActivity, boardsList)
//            rv_boards_list.adapter = adapter // Attach the adapter to the recyclerView.
//        } else {
//            rv_boards_list.visibility = View.GONE
//            tv_no_boards_available.visibility = View.VISIBLE
//        }
//    }
//
//














    private fun uploadUserImage() {

        if (mSelectedImageFileUri != null) {

            //getting the storage reference
            val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                "USER_IMAGE" + System.currentTimeMillis() + "." + getFileExtension(
                    mSelectedImageFileUri
                )
            )

            //adding the file to reference
            sRef.putFile(mSelectedImageFileUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    // The image upload is success
                    Log.e(
                        "Firebase Image URL",
                        taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                    )

                    // Get the downloadable url from the task snapshot
                    taskSnapshot.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { uri ->
                            Log.i("Downloadable Image URL", uri.toString())
                            mExerciseImageURL = uri.toString()
                            updateUserExerciseData()
                        }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this@ExerciseActivity,
                        exception.message,
                        Toast.LENGTH_LONG
                    ).show()

                }
        }
    }


    private fun getFileExtension(uri: Uri?): String? {

        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

    fun exerciseUpdateSuccess()
    {
        finish()
    }
}
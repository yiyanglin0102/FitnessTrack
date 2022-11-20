package com.fitnesstrack

import android.Manifest
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
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.fitnesstrack.firebase.Firestore
import com.fitnesstrack.firebase.models.User
import kotlinx.android.synthetic.main.activity_goal.*
import androidx.core.content.ContextCompat
import com.fitnesstrack.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException

class GoalActivity : AppCompatActivity() {

    companion object {
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }

    private var mSelectedImageFileUri: Uri? = null
    private var mGoalImageURL: String = ""
    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)
        setupActionBar()

        Firestore().loadUserData(this)

        iv_goal_user_image.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                showImageChooser()
            } else {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }

        btn_update_goal.setOnClickListener {
            if (mSelectedImageFileUri != null) {
                uploadUserImage()
            }
            else
            {
                updateUserGoalData()
            }
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

        setSupportActionBar(toolbar_goal_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = resources.getString(R.string.goal_title)
        }

        toolbar_goal_activity.setNavigationOnClickListener { onBackPressed() }
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

            try {
                Glide
                    .with(this@GoalActivity)
                    .load(Uri.parse(mSelectedImageFileUri.toString()))
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_place_holder)
                    .into(iv_goal_user_image)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun setUserDataUI(user: User) {

        mUserDetails = user


        Glide
            .with(this@GoalActivity)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(iv_goal_user_image)

        et_name_goal.setText(user.name)
        et_email_goal.setText(user.email)
        if (user.mobile != 0L) {
            et_mobile_goal.setText(user.mobile.toString())
        }
    }

    private fun updateUserGoalData() {

        val userHashMap = HashMap<String, Any>()

        if (mGoalImageURL.isNotEmpty() && mGoalImageURL != mUserDetails.image) {
            userHashMap[Constants.IMAGE] = mGoalImageURL
        }

        if (et_name_goal.text.toString() != mUserDetails.name) {
            userHashMap[Constants.NAME] = et_name_goal.text.toString()
        }

        if (et_mobile_goal.text.toString() != mUserDetails.mobile.toString()) {
            userHashMap[Constants.MOBILE] = et_mobile_goal.text.toString().toLong()
        }

        // Update the data in the database.
        Firestore().updateUserGoalData(this@GoalActivity, userHashMap)
    }

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
                            mGoalImageURL = uri.toString()
                            updateUserGoalData()
                        }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this@GoalActivity,
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

    fun goalUpdateSuccess()
    {
        finish()
    }
}
package com.fitnesstrack

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fitnesstrack.firebase.Firestore
import com.fitnesstrack.firebase.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth

        btn_sign_up.setOnClickListener {
            registerUser()
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload();
        }
    }

    private fun createAccount(email: String, name: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val user = User(
                        firebaseUser.uid, name, registeredEmail
                    )

                    // call the registerUser function of FirestoreClass to make an entry in the database.
                    Firestore().registerUser(this@SignUpActivity, user)
                    updateUI(firebaseUser)
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Registration Failed", Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    fun userRegisteredSuccess() {
        Toast.makeText(
            this@SignUpActivity,
            "you have successfully registered", Toast.LENGTH_LONG
        ).show()

    }

    private fun registerUser() {
        val name: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }
        createAccount(email, name, password)
    }


    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun reload() {

    }

    companion object {
        const val TAG = "EmailPassword"
    }
}

package com.fitnesstrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fitnesstrack.firebase.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_sign_in)

        btn_sign_in.setOnClickListener {
            signInRegisteredUser()
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload();
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(SignUpActivity.TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Log.w(SignUpActivity.TAG, "signhInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signInRegisteredUser() {
        val email: String = et_email_signin.text.toString().trim { it <= ' ' }
        val password: String = et_password_signin.text.toString().trim { it <= ' ' }
        signInWithEmail(email, password)
    }

    fun signInSuccess(user: User) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun reload() {

    }

    private fun updateUI(user: FirebaseUser?) {

    }

}

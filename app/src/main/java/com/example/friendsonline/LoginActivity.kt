package com.example.friendsonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import android.content.ContentValues.TAG

class LoginActivity : AppCompatActivity() {
    private lateinit var userName : EditText
    private lateinit var password : EditText
    private lateinit var logIn : Button
    private lateinit var signUp : Button
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        userName = findViewById(R.id.UserName)
        password = findViewById(R.id.Password)
        logIn = findViewById(R.id.LoginButton)
        signUp = findViewById(R.id.SignUpButton)
        signUp.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        logIn.setOnClickListener{
            val email = userName.text.toString()
            val pass = password.text.toString()
            login(email,pass)
        }

    }
    private fun login(email: String,pass: String){
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Code for logging in user
                   val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // Code for logging again
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this@LoginActivity,"Wrong credentials! Please try again", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
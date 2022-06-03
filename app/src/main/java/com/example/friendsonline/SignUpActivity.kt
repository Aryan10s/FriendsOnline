package com.example.friendsonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import android.content.ContentValues.TAG
import android.content.Intent
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpActivity : AppCompatActivity() {
    private lateinit var Name : EditText
    private lateinit var userName : EditText
    private lateinit var password : EditText
    private lateinit var signUp : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        Name = findViewById(R.id.Name)
        userName = findViewById(R.id.UserName)
        password = findViewById(R.id.Password)
        signUp = findViewById(R.id.SignUpButton)
        signUp.setOnClickListener{
            val name = Name.text.toString()
            val email = userName.text.toString()
            val pass = password.text.toString()
            signUp(name,email,pass)
        }
    }
    private fun signUp(name: String,email: String,pass: String){
        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Code for moving to main activity
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUpActivity,LoginActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // Code for asking the credentials again
                    Toast.makeText(this@SignUpActivity,"Some error occured!",Toast.LENGTH_SHORT).show()

                }
            }
    }
    private fun addUserToDatabase(name: String,email: String,uid:String){
     mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}
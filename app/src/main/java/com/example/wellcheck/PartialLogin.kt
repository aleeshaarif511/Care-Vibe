package com.example.wellcheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wellcheck.Activity.DoctorSignupActivity
import com.example.wellcheck.Activity.Fpass
import com.example.wellcheck.Activity.WarningActivity
import com.example.wellcheck.Domain.Doctors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class PartialLogin : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var btnForgetPassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partial_login)

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPass = findViewById(R.id.edt_pass)
        btnLogin = findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_SignUp)
        btnForgetPassword = findViewById(R.id.btn_Forget_password)

        btnSignUp.setOnClickListener {
            val intent = Intent(this, DoctorSignupActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPass.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login(email, password)
        }
        btnForgetPassword.setOnClickListener {
            val intent = Intent(this, WarningActivity::class.java)
            startActivity(intent)
            val email = edtEmail.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email to reset the password", Toast.LENGTH_SHORT).show()
            } else {
                resetPassword(email)
            }
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful
                    val uid = mAuth.currentUser?.uid
                    if (uid != null) {
                        fetchDoctorData(uid)
                    } else {
                        Toast.makeText(this, "Failed to retrieve user ID", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMessage = task.exception?.localizedMessage ?: "Login failed"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun resetPassword(email: String) {
        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent to $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Fpass::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Error in sending reset email", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun fetchDoctorData(userId: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("doctors").child(userId)
        databaseRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val doctor = snapshot.getValue(Doctors::class.java)
                if (doctor != null) {
                     val intent = Intent(this, west::class.java)
                    intent.putExtra("doctor", doctor) // Make sure you are passing a Doctors object
                    startActivity(intent)


                    finish()
                } else {
                    Toast.makeText(this, "Doctor data is empty", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Doctor not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error fetching doctor data: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

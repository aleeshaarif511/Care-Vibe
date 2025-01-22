package com.example.wellcheck.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wellcheck.R

class WarningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warning)


        val promiseButton: Button = findViewById(R.id.btn_Promise)

        promiseButton.setOnClickListener {
            Toast.makeText(this, "Thank you for making a promise!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Fpass::class.java)
            startActivity(intent)
        }


    }
}

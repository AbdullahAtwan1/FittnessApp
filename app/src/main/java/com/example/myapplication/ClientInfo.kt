package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.*

class ClientInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_info)

        // SET SUBMIT BUTTON
        val tvSubmittedInfo = findViewById<Button>(R.id.btn_submit)
        tvSubmittedInfo.setOnClickListener {
            val fullName = findViewById<EditText>(R.id.etFirstName).text.toString()
            val heightCm = findViewById<EditText>(R.id.etHeightCm).text.toString().toIntOrNull()
            val startWeight = findViewById<EditText>(R.id.etWeightKg).text.toString().toDoubleOrNull()
            val goalWeight = findViewById<EditText>(R.id.etGoalWeightKg).text.toString().toDoubleOrNull()

            // Validate input for height in centimeters
            if (heightCm == null || heightCm < 90 || heightCm > 250) {
                Toast.makeText(this, "Please enter a valid height in centimeters.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate input for weight in kilograms
            if (startWeight == null || startWeight < 30.0 || startWeight > 200.0 ||
                goalWeight == null || goalWeight < 30.0 || goalWeight > 200.0) {
                Toast.makeText(this, "Please enter a valid weight in kilograms.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Intent(this, SubmittedInfo::class.java).also {
                it.putExtra("EXTRA_FLNAME", fullName)
                it.putExtra("EXTRA_HEIGHTCM", heightCm)
                it.putExtra("EXTRA_STWEIGHT", startWeight)
                it.putExtra("EXTRA_GLWEIGHT", goalWeight)
                startActivity(it)
            }
        }

        // SET BACK BUTTON TO GO HOME
        val btnBack = findViewById<Button>(R.id.back_btn_client_info)
        btnBack.setOnClickListener {
            finish()
        }
    }
}

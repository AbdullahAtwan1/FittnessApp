package com.example.myapplication

import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class SubmittedInfo : AppCompatActivity() {
    // To reset input fields when done
    var isNewOp = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submitted_info)

        // Initialize variables from ClientInfo
        val fullName = intent.getStringExtra("EXTRA_FLNAME")
        val heightCm = intent.getIntExtra("EXTRA_HEIGHTCM", 0)
        val startWeight = intent.getDoubleExtra("EXTRA_STWEIGHT", 0.0)
        val goalWeight = intent.getDoubleExtra("EXTRA_GLWEIGHT", 0.0)

        // DISPLAY CLIENT INFO
        val clientInfo = "Hello $fullName, your current weight is $startWeight kg"
        val tvSubmittedInfo = findViewById<TextView>(R.id.tvIntroHeadline)
        tvSubmittedInfo.text = clientInfo

        // Display the estimated date to reach the goal
        val tvEndDateGoal = findViewById<TextView>(R.id.tvEndDateGoal)
        tvEndDateGoal.text = updateGoalDays(startWeight, goalWeight)

        // Calculate BMI and print it out
        calculateBMI(startWeight, heightCm)

        // UPDATE WEIGHT
        val tvUpdateWeight = findViewById<TextView>(R.id.btn_update_weight)
        tvUpdateWeight.setOnClickListener {
            val currentWeightText = findViewById<EditText>(R.id.etUpdateWeightKg).text.toString()

            // Check if the EditText is empty
            if (currentWeightText.isNotBlank()) {
                val currentWeight = currentWeightText.toDouble()
                val updatedClientInfo = "Hello $fullName, your current weight is $currentWeight kg"
                tvSubmittedInfo.text = updatedClientInfo

                // Update progress bar
                val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                val progress = ((startWeight - currentWeight) / (startWeight - goalWeight) * 100).toInt().coerceIn(0, 100)
                progressBar.progress = progress

                // Calculate BMI and print it out
                calculateBMI(currentWeight, heightCm)
                tvEndDateGoal.text = updateGoalDays(currentWeight, goalWeight)

                // Display motivational toast if goal is not met
                if (currentWeight != goalWeight) {
                    // Takes string array from string.xml
                    val messages = resources.getStringArray(R.array.congratulations)
                    val random = Random()
                    val index = random.nextInt(messages.size)
                    val message = messages[index]
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                } else {
                    // Congratulate if weight goal is met
                    congratulationToast(currentWeight, goalWeight)
                }

                // Clear the input field after getting input
                findViewById<EditText>(R.id.etUpdateWeightKg).setText("")
            } else {
                Toast.makeText(this, "Please enter your current weight", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateBMI(weight: Double, heightCm: Int) {
        val heightInM = heightCm / 100.0 // Convert height to meters
        val bmi = weight / (heightInM * heightInM)
        val formattedBMI = String.format("%.1f", bmi) // One decimal place
        val clientBMI = "Your BMI: $formattedBMI"
        val tvBMI = findViewById<TextView>(R.id.tvBMI)
        tvBMI.text = clientBMI
    }

    private fun daysWeightLose(startWeight: Double, goalWeight: Double): Double {
        val rate = 0.2 // Assumed weight loss rate in kg per day
        var currentWeight = startWeight
        var weightToLose = startWeight - goalWeight
        var days = 0.0
        while (weightToLose > 0) {
            weightToLose -= rate
            days++
        }
        return days
    }

    private fun updateGoalDays(currentWeight: Double, goalWeight: Double): String {
        val daysUntilWeightGoal = daysWeightLose(currentWeight, goalWeight)
        return "In $daysUntilWeightGoal days, you will reach your goal"
    }

    private fun congratulationToast(currentWeight: Double, goalWeight: Double) {
        if (currentWeight == goalWeight) {
            Toast.makeText(this, "Congratulations!!! You've reached your goal!", Toast.LENGTH_SHORT).show()
        }
    }
}

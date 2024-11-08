package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init intent button to go to Clientinfo
        val startBtn = findViewById<Button>(R.id.btn_login)
        startBtn.setOnClickListener{
            val intent = Intent(this, ClientInfo::class.java)
            startActivity(intent)
        }

        //init intent button to go to SubmittedInfo
        val continueBtn = findViewById<Button>(R.id.btn_continue)
        continueBtn.setOnClickListener{
            val intent = Intent(this, SubmittedInfo::class.java)
            startActivity(intent)
        }
    }
}
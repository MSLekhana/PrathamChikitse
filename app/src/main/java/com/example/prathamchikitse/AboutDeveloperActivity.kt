package com.example.prathamchikitse

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.android.material.button.MaterialButton

class AboutDeveloperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_developer)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnEmail = findViewById<MaterialButton>(R.id.btnEmail)
        val btnLinkedIn = findViewById<MaterialButton>(R.id.btnLinkedIn)

        btnBack.setOnClickListener {
            finish()
        }

        btnEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:1mp22ai023@gmail.com".toUri()
            }
            startActivity(intent)
        }

        btnLinkedIn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, "https://www.linkedin.com/in/lekhana-ms-90704225a".toUri())
            startActivity(intent)
        }
    }
}
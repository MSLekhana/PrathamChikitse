package com.example.prathamchikitse

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.android.material.button.MaterialButton
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var tts: TextToSpeech
    private var isTtsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val speakButton = findViewById<MaterialButton>(R.id.btnSpeak)
        val backButton = findViewById<ImageButton>(R.id.btnBack)
        val callButton = findViewById<MaterialButton>(R.id.btnCall)

        speakButton.isEnabled = false

        // TTS init
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.ENGLISH
                isTtsReady = true
                speakButton.isEnabled = true
            }
        }

        val title = intent.getStringExtra("title") ?: ""
        val steps = intent.getStringExtra("steps") ?: ""
        val dos = intent.getStringExtra("dos") ?: ""
        val donts = intent.getStringExtra("donts") ?: ""

        val formattedSteps = if (steps.contains("\n")) {
             steps.split("\n").mapIndexed { index, step ->
                "${index + 1}. $step"
            }.joinToString("\n")
        } else {
            steps
        }

        findViewById<TextView>(R.id.tvTitle).text = title
        findViewById<TextView>(R.id.tvSteps).text = formattedSteps
        findViewById<TextView>(R.id.tvDos).text = dos
        findViewById<TextView>(R.id.tvDonts).text = donts

        speakButton.setOnClickListener {
            val textToRead = "$title. Steps: $formattedSteps. Do's: $dos. Don'ts: $donts"

            if (isTtsReady && textToRead.isNotEmpty()) {
                tts.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, "tts1")
            }
        }

        callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:108".toUri()
            startActivity(intent)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
    }
}
package com.example.prathamchikitse

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prathamchikitse.data.JsonParser
import com.example.prathamchikitse.data.JsonUtils
import com.example.prathamchikitse.ui.home.EmergencyAdapter
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private var isKannada = false
    private lateinit var adapter: EmergencyAdapter
    private lateinit var tvNoResults: TextView
    private var keepSplashScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install Splash Screen before super.onCreate()
        val splashScreen = installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        // Keep splash screen visible for 1.5 seconds
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashScreen = false
        }, 1500)

        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val btnLanguage = findViewById<Button>(R.id.btnLanguage)
        val searchView = findViewById<SearchView>(R.id.searchView)
        val btnAboutDeveloper = findViewById<MaterialButton>(R.id.btnAboutDeveloper)
        tvNoResults = findViewById(R.id.tvNoResults)

        val json = JsonUtils.loadJSONFromAssets(this)
        val list = JsonParser.parseEmergencyList(json)

        recyclerView.layoutManager = LinearLayoutManager(this)

        fun setupAdapter() {
            adapter = EmergencyAdapter(list, isKannada) { emergency ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("title", if (isKannada) emergency.name_kn else emergency.name_en)
                intent.putExtra(
                    "steps",
                    if (isKannada) emergency.steps_kn.joinToString("\n")
                    else emergency.steps_en.joinToString("\n")
                )
                intent.putExtra(
                    "dos",
                    if (isKannada) emergency.dos_kn.joinToString("\n")
                    else emergency.dos_en.joinToString("\n")
                )
                intent.putExtra(
                    "donts",
                    if (isKannada) emergency.donts_kn.joinToString("\n")
                    else emergency.donts_en.joinToString("\n")
                )
                startActivity(intent)
            }
            recyclerView.adapter = adapter

            // Listen for data changes in adapter to show/hide "No results" text
            adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    super.onChanged()
                    tvNoResults.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
                }
            })
        }

        setupAdapter()

        btnLanguage.setOnClickListener {
            isKannada = !isKannada
            btnLanguage.text = if (isKannada) "English" else "ಕನ್ನಡ"
            setupAdapter()
            // Clear search when switching language to avoid confusion
            searchView.setQuery("", false)
        }

        btnAboutDeveloper.setOnClickListener {
            val intent = Intent(this, AboutDeveloperActivity::class.java)
            startActivity(intent)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                return true
            }
        })
    }
}
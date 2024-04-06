package com.example.playlistmaker

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val searchButton = findViewById<Button>(R.id.Search)
        val searchClickListener: View.OnClickListener = object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity,"Нажата клавиша \"Поиск\"", Toast.LENGTH_SHORT).show()
            }
        }
        searchButton.setOnClickListener(searchClickListener)

        val libraryButton = findViewById<Button>(R.id.Library)
        libraryButton.setOnClickListener{
            Toast.makeText(this@MainActivity,"Нажата клавиша \"Медиатека\"", Toast.LENGTH_SHORT).show()
        }

        var settingsButton = findViewById<Button>(R.id.Settings)
        settingsButton.setOnClickListener{
            Toast.makeText(this@MainActivity,"Нажата клавиша \"Настройки\"", Toast.LENGTH_SHORT).show()
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val searchButton = findViewById<Button>(R.id.search)
        val searchClickListener: View.OnClickListener = object : View.OnClickListener{
            override fun onClick(v: View?) {
              // Спринт 8/25 → Тема 4/6: Работа в коде → Урок 2/2:  Toast.makeText(this@MainActivity,"Нажата клавиша \"Поиск\"", Toast.LENGTH_SHORT).show()
                val searchIntent = Intent(this@MainActivity,SearchActivity::class.java)
                startActivity(searchIntent)
            }
        }
        searchButton.setOnClickListener(searchClickListener)

        val libraryButton = findViewById<Button>(R.id.library)
        libraryButton.setOnClickListener{
           // Спринт 8/25 → Тема 4/6: Работа в коде → Урок 2/2: Toast.makeText(this@MainActivity,"Нажата клавиша \"Медиатека\"", Toast.LENGTH_SHORT).show()
            val libraryIntent = Intent(this, LibraryActivity::class.java)
            startActivity(libraryIntent)
        }

        val settingsButton = findViewById<Button>(R.id.settings)
        settingsButton.setOnClickListener{
          // Спринт 8/25 → Тема 4/6: Работа в коде → Урок 2/2: Toast.makeText(this@MainActivity,"Нажата клавиша \"Настройки\"", Toast.LENGTH_SHORT).show()
            val  settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

    }
}
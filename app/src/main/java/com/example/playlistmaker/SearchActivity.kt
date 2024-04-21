package com.example.playlistmaker

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {

    private var editValue: String = VALUE_DEF
    private lateinit var searchEdit : EditText


    companion object {
        const val INPUT_TEXT = "INPUT_TEXT"
        const val VALUE_DEF = ""
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val backButton = findViewById<ImageView>(R.id.back_main)
        val clearButton = findViewById<ImageView>(R.id.clear_button)
        searchEdit = findViewById(R.id.search_edit)

        searchEdit.requestFocus()


        backButton.setOnClickListener {
            super.finish()
        }

        clearButton.setOnClickListener {
            searchEdit.setText(VALUE_DEF)
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(searchEdit.windowToken, 0)
            searchEdit.clearFocus()
        }


        val editTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editValue = s.toString()
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }

            private fun clearButtonVisibility(s: CharSequence?): Int {
                return if (s.isNullOrEmpty()) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }

        }
        searchEdit.addTextChangedListener(editTextWatcher)
    }
        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            outState.putString(INPUT_TEXT, editValue)
        }

       override fun onRestoreInstanceState(savedInstanceState: Bundle) {
           super.onRestoreInstanceState(savedInstanceState)
           editValue = savedInstanceState.getString(INPUT_TEXT, VALUE_DEF)
           searchEdit.setText(editValue)
        }

}




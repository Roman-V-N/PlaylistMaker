package com.example.playlistmaker

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : AppCompatActivity() {

    private var editValue: String = VALUE_DEF
    private lateinit var searchEdit: EditText

    private val trackList: MutableList<Track> = mutableListOf()

    private var trackAdapter = TrackAdapter(trackList)

    private lateinit var errorNothingImage: ImageView
    private lateinit var errorInternetImage: ImageView

    private lateinit var errorNothingText: TextView
    private lateinit var errorInternetText: TextView

    private lateinit var errorInternetButton: Button

    private lateinit var trackRecyclerView: RecyclerView


    companion object {
        const val INPUT_TEXT = "INPUT_TEXT"
        const val VALUE_DEF = ""
    }

    private val iTunesUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(iTunesUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(iTunesAPI::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val backButton = findViewById<MaterialToolbar>(R.id.back_main)
        val clearButton = findViewById<ImageView>(R.id.clear_button)

        errorNothingImage = findViewById<ImageView>(R.id.error_nothing_image)
        errorInternetImage = findViewById<ImageView>(R.id.error_internet_image)

        errorNothingText = findViewById<TextView>(R.id.error_nothing_text)
        errorInternetText = findViewById<TextView>(R.id.error_internet_text)

        errorInternetButton = findViewById<Button>(R.id.error_internet_button)

        trackRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewTrackList)
        trackRecyclerView.layoutManager = LinearLayoutManager(this)
        searchEdit = findViewById(R.id.search_edit)
        searchEdit.requestFocus()

        backButton.setNavigationOnClickListener {
            super.finish()
        }

        clearButton.setOnClickListener {
            searchEdit.setText(VALUE_DEF)
            trackRecyclerView.visibility = INVISIBLE
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(searchEdit.windowToken, 0)
            searchEdit.clearFocus()

            errorNothingImage.visibility = GONE
            errorInternetImage.visibility = GONE
            errorInternetButton.visibility = GONE
            errorNothingText.visibility = GONE
            errorInternetText.visibility = GONE
        }

        searchEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                enterSearch()
                true
            }
            false
        }

        errorInternetButton.setOnClickListener {
            enterSearch()
        }

        val editTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editValue = s.toString()
                if (editValue == VALUE_DEF) {
                    trackRecyclerView.visibility = GONE
                    clearButton.visibility = GONE
                    errorNothingImage.visibility = GONE
                    errorInternetImage.visibility = GONE
                    errorInternetButton.visibility = GONE
                    errorNothingText.visibility = GONE
                    errorInternetText.visibility = GONE
                } else {
                    clearButton.visibility = clearButtonVisibility(s)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                editValue = searchEdit.text.toString()
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


    private fun enterSearch() {
        if (searchEdit.text.isNotEmpty()) {
            iTunesService.search(searchEdit.text.toString()).enqueue(object :
                Callback<TracksResponse> {

                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<TracksResponse>,
                    response: Response<TracksResponse>
                ) {
                    if (response.isSuccessful)
                    {
                        trackList.clear()
                        errorNothingImage.visibility = GONE
                        errorInternetImage.visibility = GONE
                        errorInternetButton.visibility = GONE
                        errorNothingText.visibility = GONE
                        errorInternetText.visibility = GONE
                        if (response.body()?.results?.isNotEmpty() == true) {
                            val searchResponse = response.body()?.results
                            if (searchResponse != null) {
                                trackList.addAll(searchResponse)
                                trackAdapter.notifyDataSetChanged()
                                trackAdapter = TrackAdapter(trackList)
                                trackRecyclerView.adapter = trackAdapter
                                trackRecyclerView.visibility = VISIBLE
                            }

                        }
                        if (trackList.isEmpty()) {
                            errorNothingText.visibility = VISIBLE
                            errorNothingImage.visibility = VISIBLE
                        } else {
                            errorNothingText.visibility = GONE
                            errorNothingImage.visibility = GONE
                        }
                    } else {
                        errorInternetText.visibility = VISIBLE
                        errorInternetImage.visibility = VISIBLE
                        errorInternetButton.visibility = VISIBLE
                        trackRecyclerView.visibility = GONE
                    }
                }

                override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                    errorInternetText.visibility = VISIBLE
                    errorInternetImage.visibility = VISIBLE
                    errorInternetButton.visibility = VISIBLE
                    trackRecyclerView.visibility = GONE

                }
            })
        }
    }


    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            GONE
        } else {
            VISIBLE
        }
    }

}




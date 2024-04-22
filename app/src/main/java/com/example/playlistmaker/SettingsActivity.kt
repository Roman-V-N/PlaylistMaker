package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val backButton = findViewById<MaterialToolbar>(R.id.back_main)
        val shareButton = findViewById<MaterialTextView>(R.id.share_app)
        val supportButton = findViewById<MaterialTextView>(R.id.support)
        val agreementButton = findViewById<MaterialTextView>(R.id.user_agreement)


        backButton.setNavigationOnClickListener {
            super.finish()
        }

        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plane"
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.site_share))
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_app)))
        }

        supportButton.setOnClickListener {
            val supportEmail = Intent(Intent.ACTION_SENDTO)
            supportEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email)))
            supportEmail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_theme))
            supportEmail.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_text))
            supportEmail.data = Uri.parse("mailto:")
            startActivity(supportEmail)
        }

        agreementButton.setOnClickListener {
            val agreementIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.site_agreement)))
            startActivity(agreementIntent)
        }


    }
}
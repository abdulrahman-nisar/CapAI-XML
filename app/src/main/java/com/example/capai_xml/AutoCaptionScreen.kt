package com.example.capai_xml

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AutoCaptionScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auto_caption_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editCaptionButton = findViewById<Button>(R.id.btnEditCaption)
        val backButtonImage = findViewById<ImageView>(R.id.ACBack)
        val saveButtonImage = findViewById<ImageView>(R.id.ivSave)

        editCaptionButton.setOnClickListener {
            val intent = Intent(this, EditCaptionScreen::class.java)
            startActivity(intent)
        }

        backButtonImage.setOnClickListener {
            Intent(this, HomeScreen::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(it)
            }
        }

        saveButtonImage.setOnClickListener {
            Intent(this, HomeScreen::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(it)
            }
        }
    }
}
package com.example.capai_xml

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SelectVideoScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_video_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val selectVideoButton = findViewById<Button>(R.id.selectVideoButton)
        val toolbarSelectVideo = findViewById<Toolbar>(R.id.toolbarSelectVideo)

        selectVideoButton.setOnClickListener {
            val bottomSheet = CaptionTranslationBottomSheet()
            bottomSheet.show(supportFragmentManager, "AIOptionsBottomSheet")
        }

        toolbarSelectVideo.setNavigationOnClickListener {
            finish()
        }
    }
}
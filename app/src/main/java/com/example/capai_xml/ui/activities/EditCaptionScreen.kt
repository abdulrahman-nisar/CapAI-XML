package com.example.capai_xml.ui.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capai_xml.R

class EditCaptionScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_caption_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButtonImage = findViewById<ImageView>(R.id.ivBack)
        val saveButtonImage = findViewById<ImageView>(R.id.ivSave)
        val videoView = findViewById<VideoView>(R.id.editCaptionVideoView)
        val uri = intent.getStringExtra("videoUri")
        videoView.setVideoPath(uri)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()

        backButtonImage.setOnClickListener {
            finish()
        }

        saveButtonImage.setOnClickListener {
            finish()
        }
    }
}
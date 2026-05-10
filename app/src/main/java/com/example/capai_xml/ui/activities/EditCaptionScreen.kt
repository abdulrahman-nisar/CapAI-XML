package com.example.capai_xml.ui.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capai_xml.R
import com.example.capai_xml.ui.compose.CaptionEditorSection

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
        val composeCaptionEditor = findViewById<ComposeView>(R.id.composeCaptionEditor)
        val uri = intent.getStringExtra("videoUri")
        val initialCaption = intent.getStringExtra("caption_text")
            ?: intent.getStringExtra("transcriptionText")
            ?: ""
        var editedCaption = initialCaption
        videoView.setVideoPath(uri)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()

        composeCaptionEditor.setContent {
            MaterialTheme {
                CaptionEditorSection(
                    initialText = initialCaption,
                    suggestions = buildSuggestions(initialCaption),
                    onTextChanged = { editedCaption = it }
                )
            }
        }

        backButtonImage.setOnClickListener {
            finish()
        }

        saveButtonImage.setOnClickListener {
            val data = intent.apply {
                putExtra("edited_caption", editedCaption)
            }
            setResult(RESULT_OK, data)
            finish()
        }
    }

    private fun buildSuggestions(baseText: String): List<String> {
        val cleaned = baseText.trim()
        if (cleaned.isBlank()) {
            return listOf(
                "New caption idea",
                "Short and catchy",
                "Add a call to action"
            )
        }

        val short = if (cleaned.length > 80) cleaned.take(80) + "..." else cleaned
        return listOf(
            cleaned,
            "$short #CapAI",
            "New drop: $short"
        )
    }
}
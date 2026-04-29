package com.example.capai_xml.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capai_xml.CapAiApp
import com.example.capai_xml.R
import com.example.capai_xml.ui.CapAiViewModel
import com.example.capai_xml.ui.CapAiViewModelFactory

class GeneratingCaptionScreen : AppCompatActivity() {
    private val viewModel: CapAiViewModel by viewModels {
        CapAiViewModelFactory((application as CapAiApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_generating_caption_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val uri = intent.getStringExtra("videoUri")
        if (uri.isNullOrBlank()) {
            Toast.makeText(this, "No video selected", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        var hasNavigated = false
        viewModel.videoResult.observe(this) { res ->
            if (hasNavigated) return@observe

            res?.errorMessage?.takeIf { it.isNotBlank() }?.let {
                Log.e("error1",it)
                Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
                finish()
                return@observe
            }

            if (res?.isSuccess == true) {
                hasNavigated = true
                val intent = Intent(this, AutoCaptionScreen::class.java)
                intent.putExtra("videoUri", uri)
                intent.putExtra("transcriptionText", res.transcriptionText)
                startActivity(intent)
                finish()
            }
        }

        viewModel.transcribeFromVideoUrl(uri, this)
    }
}
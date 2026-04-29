package com.example.capai_xml.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capai_xml.R
import com.example.capai_xml.ui.fragements.CaptionTranslationBottomSheet

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

        val pickVideoLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                val intent = Intent(this, GeneratingCaptionScreen::class.java)
                intent.putExtra("videoUri", it.toString())
                startActivity(intent)
//                val bottomSheet = CaptionTranslationBottomSheet.Companion.newInstance(it.toString())
//                bottomSheet.show(supportFragmentManager, "AIOptionsBottomSheet")

            }
        }

        selectVideoButton.setOnClickListener {
            pickVideoLauncher.launch("video/*")
        }

        toolbarSelectVideo.setNavigationOnClickListener {
            finish()
        }
    }
}
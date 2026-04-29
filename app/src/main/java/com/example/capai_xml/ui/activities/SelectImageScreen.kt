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

class SelectImageScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_image_screen)

        val toolbar = findViewById<Toolbar>(R.id.toolbarSelectImage)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val selectImageScreenButton = findViewById<Button>(R.id.selectImageButton)

        val pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->
            uri?.let {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                val intent = Intent(this, ImageCaptionPreferencesScreen::class.java)
                intent.putExtra("selectedImageUri", it.toString())
                startActivity(intent)
            }
        }
        selectImageScreenButton.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*"))
        }

    }
}
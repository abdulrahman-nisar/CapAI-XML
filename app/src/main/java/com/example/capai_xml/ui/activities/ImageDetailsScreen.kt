package com.example.capai_xml.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.capai_xml.CapAiApp
import com.example.capai_xml.R
import com.example.capai_xml.domain.model.Length
import com.example.capai_xml.ui.CapAiViewModel
import com.example.capai_xml.ui.CapAiViewModelFactory
import com.example.capai_xml.ui.adapter.CaptionPagerAdapter

class ImageDetailsScreen : AppCompatActivity() {

    private val viewModel: CapAiViewModel by viewModels {
        CapAiViewModelFactory((application as CapAiApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image_details_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbarGeneratedCaptions)
        val selectedImageUri = intent.getStringExtra("selectedImageUri")?.toUri()
        val length = intent.getStringExtra("captionLength")
            ?.let { runCatching { Length.valueOf(it) }.getOrNull() }
            ?: Length.SHORT

        val selectedImageView = findViewById<ImageView>(R.id.ivDetailedSelectedImage)
        val progress = findViewById<ProgressBar>(R.id.progressGenerating)
        val contentScroll = findViewById<View>(R.id.contentScroll)
        val pager = findViewById<ViewPager2>(R.id.pagerCaptions)

        selectedImageUri?.let {
            selectedImageView.setImageURI(it)
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.generateCaptionForImage(selectedImageUri.toString(), length, this)
        }

        viewModel.result.observe(this) { res ->
            val isGenerating = res?.isGenerating == true
            progress.visibility = if (isGenerating) View.VISIBLE else View.GONE
            contentScroll.visibility = if (isGenerating) View.GONE else View.VISIBLE

            res?.errorMessage?.takeIf { it.isNotBlank() }?.let {
                Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
            }

            val item = res?.captionItem ?: return@observe
            val pages = listOfNotNull(
                item.instagramCaption?.takeIf { it.isNotBlank() }?.let { CaptionPagerAdapter.CaptionPage("Instagram", it) },
                item.facebookCaption?.takeIf { it.isNotBlank() }?.let { CaptionPagerAdapter.CaptionPage("Facebook", it) },
                item.threadCaption?.takeIf { it.isNotBlank() }?.let { CaptionPagerAdapter.CaptionPage("Thread", it) },
                item.twitterCaption?.takeIf { it.isNotBlank() }?.let { CaptionPagerAdapter.CaptionPage("Twitter", it) },
                item.pinterestCaption?.takeIf { it.isNotBlank() }?.let { CaptionPagerAdapter.CaptionPage("Pinterest", it) },
                item.linkedinCaption?.takeIf { it.isNotBlank() }?.let { CaptionPagerAdapter.CaptionPage("LinkedIn", it) },
                item.snapChatCaption?.takeIf { it.isNotBlank() }?.let { CaptionPagerAdapter.CaptionPage("Snapchat", it) },
                item.tiktokCaption?.takeIf { it.isNotBlank() }?.let { CaptionPagerAdapter.CaptionPage("TikTok", it) },
            )
            pager.adapter = CaptionPagerAdapter(pages)
        }

        toolbar.setNavigationOnClickListener {
            Intent(this, HomeScreen::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(it)
            }
        }
    }
}
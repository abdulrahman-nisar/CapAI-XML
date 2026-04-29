package com.example.capai_xml.ui.activities

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capai_xml.CapAiApp
import com.example.capai_xml.R
import com.example.capai_xml.ui.CapAiViewModel
import com.example.capai_xml.ui.CapAiViewModelFactory
import com.example.capai_xml.ui.adapter.HistoryItemAdapter
import com.example.capai_xml.ui.fragements.HomeNewButtonBottomSheet
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.example.capai_xml.domain.model.CaptionItem
import com.example.capai_xml.domain.model.SourceTable
import com.example.capai_xml.domain.model.TranscriptionItem

class HomeScreen : AppCompatActivity() {

    private val viewModel: CapAiViewModel by viewModels {
        CapAiViewModelFactory((application as CapAiApp).repository)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryItemAdapter

    private lateinit var etSearch: TextInputEditText

    private var captionHistory: List<CaptionItem> = emptyList()
    private var transcriptionHistory: List<TranscriptionItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.main)
        val navView = findViewById<NavigationView>(R.id.navView)
        val menuButton = findViewById<ImageView>(R.id.ivMenu)

        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_logout -> {
                    viewModel.signOut()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent = Intent(this, LoginScreen::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        etSearch = findViewById(R.id.etSearch)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HistoryItemAdapter(
            emptyList(),
            onItemClick = { item -> openHistoryItem(item) },
            onDownloadClick = { item -> downloadHistoryItem(item) },
            onDeleteClick = { item ->
                when (item.source) {
                    SourceTable.CAPTION -> viewModel.deleteCaptionById(item.id)
                    SourceTable.TRANSCRIPTION -> viewModel.deleteTranscriptionById(item.id)
                }
            }
        )
        recyclerView.adapter = adapter

        viewModel.captionHistory.observe(this) { list ->
            captionHistory = list
            updateHistoryList()
        }

        viewModel.transcriptionHistory.observe(this) { list ->
            transcriptionHistory = list
            updateHistoryList()
        }

        etSearch.doOnTextChanged { text, _, _, _ ->
            adapter.search(text?.toString().orEmpty())
        }

        val fabButton = findViewById<ExtendedFloatingActionButton>(R.id.fabNew)
        fabButton.setOnClickListener {
            val bottomSheet = HomeNewButtonBottomSheet()
            bottomSheet.show(supportFragmentManager, "HomeNewButtonBottomSheet")
        }
    }

    private fun updateHistoryList() {
        val captions = captionHistory.map { caption ->
            HistoryListItem(
                id = caption.id,
                name = "Image",
                description = caption.instagramCaption.orEmpty(),
                source = SourceTable.CAPTION,
                imageUri = caption.imageUri,
                videoUri = "",
                instagramCaption = caption.instagramCaption,
                facebookCaption = caption.facebookCaption,
                threadCaption = caption.threadCaption,
                twitterCaption = caption.twitterCaption,
                pinterestCaption = caption.pinterestCaption,
                linkedinCaption = caption.linkedinCaption,
                snapChatCaption = caption.snapChatCaption,
                tiktokCaption = caption.tiktokCaption,
                transcriptionText = null
            )
        }

        val transcriptions = transcriptionHistory.map { transcription ->
            HistoryListItem(
                id = transcription.id,
                name = "Video",
                description = transcription.transcriptionText.orEmpty(),
                source = SourceTable.TRANSCRIPTION,
                imageUri = "",
                videoUri = transcription.videoUri,
                instagramCaption = null,
                facebookCaption = null,
                threadCaption = null,
                twitterCaption = null,
                pinterestCaption = null,
                linkedinCaption = null,
                snapChatCaption = null,
                tiktokCaption = null,
                transcriptionText = transcription.transcriptionText
            )
        }

        adapter.updateItems(captions + transcriptions)
    }

    private fun openHistoryItem(item: HistoryListItem) {
        when (item.source) {
            SourceTable.CAPTION -> {
                val intent = Intent(this, ImageDetailsScreen::class.java).apply {
                    putExtra("selectedImageUri", item.imageUri)
                    putExtra("caption_instagram", item.instagramCaption)
                    putExtra("caption_facebook", item.facebookCaption)
                    putExtra("caption_thread", item.threadCaption)
                    putExtra("caption_twitter", item.twitterCaption)
                    putExtra("caption_pinterest", item.pinterestCaption)
                    putExtra("caption_linkedin", item.linkedinCaption)
                    putExtra("caption_snapchat", item.snapChatCaption)
                    putExtra("caption_tiktok", item.tiktokCaption)
                }
                startActivity(intent)
            }
            SourceTable.TRANSCRIPTION -> {
                val intent = Intent(this, AutoCaptionScreen::class.java).apply {
                    putExtra("videoUri", item.videoUri)
                    putExtra("transcriptionText", item.transcriptionText)
                }
                startActivity(intent)
            }
        }
    }

    private fun downloadHistoryItem(item: HistoryListItem) {
        val uriString = if (item.source == SourceTable.CAPTION) item.imageUri else item.videoUri
        if (uriString.isBlank()) {
            Toast.makeText(this, "No media to download", Toast.LENGTH_SHORT).show()
            return
        }

        val uri = uriString.toUri()
        val mimeType = contentResolver.getType(uri)
            ?: if (item.source == SourceTable.CAPTION) "image/*" else "video/*"
        val ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        val prefix = if (item.source == SourceTable.CAPTION) "caption_image" else "caption_video"
        val fileName = buildString {
            append(prefix)
            append("_")
            append(System.currentTimeMillis())
            if (!ext.isNullOrBlank()) {
                append(".")
                append(ext)
            }
        }

        val values = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, mimeType)
            put(MediaStore.Downloads.IS_PENDING, 1)
        }

        val outputUri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
        if (outputUri == null) {
            Toast.makeText(this, "Unable to save file", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            contentResolver.openInputStream(uri)?.use { input ->
                contentResolver.openOutputStream(outputUri)?.use { output ->
                    input.copyTo(output)
                }
            }
            values.clear()
            values.put(MediaStore.Downloads.IS_PENDING, 0)
            contentResolver.update(outputUri, values, null, null)
            Toast.makeText(this, "Saved to Downloads", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Download failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        currentFocus?.let { view ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}
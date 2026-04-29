package com.example.capai_xml.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

class HomeScreen : AppCompatActivity() {

    private val viewModel: CapAiViewModel by viewModels {
        CapAiViewModelFactory((application as CapAiApp).repository)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryItemAdapter
    private lateinit var historyItems: List<HistoryListItem>

    private lateinit var etSearch: TextInputEditText

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


        historyItems = listOf(
            HistoryListItem("Sun Set", "29/03/2026"),
            HistoryListItem("BirthDay Video", "29/03/2026"),
            HistoryListItem("Movie", "29/03/2026"),
            HistoryListItem("Movie", "29/03/2026"),
            HistoryListItem("Movie", "29/03/2026"),
            HistoryListItem("Movie", "29/03/2026"),
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HistoryItemAdapter(historyItems)
        recyclerView.adapter = adapter

        etSearch.doOnTextChanged { text, _, _, _ ->
            adapter.search(text?.toString().orEmpty())
        }

        val fabButton = findViewById<ExtendedFloatingActionButton>(R.id.fabNew)
        fabButton.setOnClickListener {
            val bottomSheet = HomeNewButtonBottomSheet()
            bottomSheet.show(supportFragmentManager, "HomeNewButtonBottomSheet")
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
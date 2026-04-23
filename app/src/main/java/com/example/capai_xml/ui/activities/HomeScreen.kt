package com.example.capai_xml.ui.activities

import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capai_xml.ui.fragements.HomeNewButtonBottomSheet
import com.example.capai_xml.R
import com.example.capai_xml.ui.adapter.HistoryItemAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class HomeScreen : AppCompatActivity() {

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
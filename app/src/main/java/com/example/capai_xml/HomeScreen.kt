package com.example.capai_xml

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class HomeScreen : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var adapter : HistoryItemAdapter
    lateinit var historyItems : List<HistoryListItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)

        historyItems = listOf(
            HistoryListItem("Sun Set", " 29/03/2026"),
            HistoryListItem("BirthDay Video", " 29/03/2026"),
            HistoryListItem("Movie", " 29/03/2026"),
            HistoryListItem("Movie", " 29/03/2026"),
            HistoryListItem("Movie", " 29/03/2026"),
            HistoryListItem("Movie", " 29/03/2026"),

        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HistoryItemAdapter(historyItems)
        recyclerView.adapter = adapter

        val fabButton = findViewById<ExtendedFloatingActionButton>(R.id.fabNew)
        fabButton.setOnClickListener {
            val bottomSheet = HomeNewButtonBottomSheet()
            bottomSheet.show(supportFragmentManager, "HomeNewButtonBottomSheet")
        }
    }
}
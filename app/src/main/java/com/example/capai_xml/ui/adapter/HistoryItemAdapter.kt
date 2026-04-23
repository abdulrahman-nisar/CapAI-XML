package com.example.capai_xml.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capai_xml.R
import com.example.capai_xml.ui.activities.HistoryListItem

class HistoryItemAdapter(items: List<HistoryListItem>) :
    RecyclerView.Adapter<HistoryItemAdapter.ViewHolder>() {

    private val allItems: MutableList<HistoryListItem> = items.toMutableList()

    private val visibleItems: MutableList<HistoryListItem> = items.toMutableList()

    private var currentQuery: String = ""

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val date: TextView = view.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.historyitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = visibleItems[position]
        holder.name.text = item.name
        holder.date.text = item.date
    }

    override fun getItemCount(): Int = visibleItems.size

    @SuppressLint("NotifyDataSetChanged")
    fun search(query: String) {
        currentQuery = query

        val q = query.trim()
        val filtered = if (q.isBlank()) {
            allItems
        } else {
            allItems.filter {
                it.name.contains(q, ignoreCase = true) ||
                    it.date.contains(q, ignoreCase = true)
            }
        }

        visibleItems.clear()
        visibleItems.addAll(filtered)
        notifyDataSetChanged()
    }

}
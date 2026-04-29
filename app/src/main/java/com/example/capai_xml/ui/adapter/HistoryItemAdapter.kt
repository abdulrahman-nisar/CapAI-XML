package com.example.capai_xml.ui.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.capai_xml.R
import com.example.capai_xml.domain.model.SourceTable
import com.example.capai_xml.ui.activities.HistoryListItem

class HistoryItemAdapter(
    items: List<HistoryListItem>,
    private val onItemClick: (HistoryListItem) -> Unit,
    private val onDownloadClick: (HistoryListItem) -> Unit,
    private val onDeleteClick: (HistoryListItem) -> Unit
) : RecyclerView.Adapter<HistoryItemAdapter.ViewHolder>() {

    private val allItems: MutableList<HistoryListItem> = items.toMutableList()

    private val visibleItems: MutableList<HistoryListItem> = items.toMutableList()

    private var currentQuery: String = ""

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val date: TextView = view.findViewById(R.id.description)
        val deleteButton: View = view.findViewById(R.id.btnDelete)
        val downloadButton: View = view.findViewById(R.id.btnDownload)
        val thumbnail: ImageView = view.findViewById(R.id.ivHistoryThumb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.historyitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = visibleItems[position]
        holder.name.text = item.name
        holder.date.text = item.description
        holder.deleteButton.setOnClickListener { onDeleteClick(item) }
        holder.downloadButton.setOnClickListener { onDownloadClick(item) }
        holder.itemView.setOnClickListener { onItemClick(item) }

        if (item.source == SourceTable.TRANSCRIPTION) {
            holder.thumbnail.setImageResource(R.drawable.video)
            return
        }

        val uri = item.imageUri.trim()
        if (uri.isEmpty()) {
            holder.thumbnail.setImageDrawable(null)
            return
        }

        try {
            holder.thumbnail.setImageURI(uri.toUri())
        } catch (e: SecurityException) {
            Log.w("HistoryItemAdapter", "No permission for image uri: $uri", e)
            holder.thumbnail.setImageDrawable(null)
        }
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
                    it.description.contains(q, ignoreCase = true)
            }
        }

        visibleItems.clear()
        visibleItems.addAll(filtered)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<HistoryListItem>) {
        allItems.clear()
        allItems.addAll(items)
        search(currentQuery)
    }

}
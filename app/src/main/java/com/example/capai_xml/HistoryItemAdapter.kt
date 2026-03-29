package com.example.capai_xml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryItemAdapter(
    private val historyItems: List<HistoryListItem>
) : RecyclerView.Adapter<HistoryItemAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name : TextView = view.findViewById<TextView>(R.id.name)
        val date : TextView = view.findViewById<TextView>(R.id.date)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.historyitem, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = historyItems[p1]
        p0.name.text = item.name
        p0.date.text = item.date

    }

    override fun getItemCount(): Int {
        return historyItems.size
    }
}
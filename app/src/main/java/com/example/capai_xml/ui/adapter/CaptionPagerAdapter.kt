package com.example.capai_xml.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capai_xml.R

class CaptionPagerAdapter(
    private val pages: List<CaptionPage>
) : RecyclerView.Adapter<CaptionPagerAdapter.CaptionViewHolder>() {

    data class CaptionPage(
        val platform: String,
        val caption: String
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaptionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_caption_page, parent, false)
        return CaptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaptionViewHolder, position: Int) {
        holder.bind(pages[position])
    }

    override fun getItemCount(): Int = pages.size

    class CaptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPlatform: TextView = itemView.findViewById(R.id.tvPlatform)
        private val tvCaption: TextView = itemView.findViewById(R.id.tvCaption)

        fun bind(page: CaptionPage) {
            tvPlatform.text = page.platform
            tvCaption.text = page.caption
        }
    }
}


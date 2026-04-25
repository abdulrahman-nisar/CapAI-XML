package com.example.capai_xml.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        private val ivPlatformIcon: ImageView = itemView.findViewById(R.id.ivPlatformIcon)
        private val tvPlatform: TextView = itemView.findViewById(R.id.tvPlatform)
        private val tvCaption: TextView = itemView.findViewById(R.id.tvCaption)

        fun bind(page: CaptionPage) {
            tvPlatform.text = page.platform
            tvCaption.text = page.caption

            val iconRes = when (page.platform.trim().lowercase()) {
                "instagram" -> R.drawable.instagram_playstore
                "facebook" -> R.drawable.facebook_playstore
                "tiktok" -> R.drawable.tiktok_playstore
                "thread", "threads" -> R.drawable.threads_playstore
                "pinterest" -> R.drawable.pinterest_playstore
                "snapchat" -> R.drawable.snapchat_playstore
                "linkedin" -> R.drawable.linkldin_playstore
                "twitter" -> R.drawable.twitter_playstore
                else -> null
            }

            if (iconRes != null) {
                ivPlatformIcon.visibility = View.VISIBLE
                ivPlatformIcon.setImageResource(iconRes)
            } else {
                ivPlatformIcon.visibility = View.GONE
                ivPlatformIcon.setImageDrawable(null)
            }
        }
    }
}


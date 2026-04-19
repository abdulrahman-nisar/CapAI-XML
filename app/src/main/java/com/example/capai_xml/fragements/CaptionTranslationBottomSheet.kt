package com.example.capai_xml.fragements

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.capai_xml.R
import com.example.capai_xml.activities.GeneratingCaptionScreen
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CaptionTranslationBottomSheet : BottomSheetDialogFragment() {

    companion object{
        private const val ARG_VIDEO_URI = "videoUri"
        fun newInstance(videoUri: String): CaptionTranslationBottomSheet {
            val args = Bundle().apply {
                putString(ARG_VIDEO_URI, videoUri)
            }
            return CaptionTranslationBottomSheet().apply {
                arguments = args
            }
        }
    }

    private var selectedChipId: Int = R.id.chipEnglish
    private var selectedLanguageText: String = "English"

    private val chipLanguageMap = mapOf(
        R.id.chipEnglish to "English",
        R.id.chipUrdu to "Urdu",
        R.id.chipArabic to "Arabic",
        R.id.chipSpanish to "Spanish",
        R.id.chipFrench to "French",
        R.id.chipGerman to "German",
        R.id.chipChinese to "Chinese",
        R.id.chipHindi to "Hindi",
        R.id.chipTurkish to "Turkish",
        R.id.chipPortuguese to "Portuguese"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.activity_bottom_sheet_caption_translation,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val chipIds = chipLanguageMap.keys
        val videoUri = arguments?.getString(ARG_VIDEO_URI)
        chipIds.forEach { chipId ->
            view.findViewById<TextView>(chipId).setOnClickListener {
                selectLanguage(view, chipId)
            }
        }

        selectLanguage(view, selectedChipId)

        val generateCaptionButton = view.findViewById<Button>(R.id.btnGenerateCaption)
        generateCaptionButton.setOnClickListener {
                val intent = Intent(view.context, GeneratingCaptionScreen::class.java)
                intent.putExtra("videoUri", videoUri)
                startActivity(intent)
                dismiss()
        }
    }

    private fun selectLanguage(root: View, newSelectedChipId: Int) {
        chipLanguageMap.keys.forEach { chipId ->
            val chip = root.findViewById<TextView>(chipId)
            val isSelected = chipId == newSelectedChipId
            chip.setBackgroundResource(
                if (isSelected) R.drawable.bg_chip_selected else R.drawable.bg_chip_unselected
            )
            chip.setTextColor(if (isSelected) Color.WHITE else Color.parseColor("#B0FFFFFF"))
            chip.setTypeface(chip.typeface, if (isSelected) Typeface.BOLD else Typeface.NORMAL)
        }

        selectedChipId = newSelectedChipId
        selectedLanguageText = chipLanguageMap.getValue(newSelectedChipId)
    }
}
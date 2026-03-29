package com.example.capai_xml

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CaptionTranslationBottomSheet : BottomSheetDialogFragment() {


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
        val generateCaptionButton = view.findViewById<Button>(R.id.btnGenerateCaption)
        generateCaptionButton.setOnClickListener {
            val intent = Intent(view.context, GeneratingCaptionScreen::class.java)
            startActivity(intent)
        }
    }

}
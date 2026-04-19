package com.example.capai_xml.fragements

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.capai_xml.R
import com.example.capai_xml.activities.SelectImageScreen
import com.example.capai_xml.activities.SelectVideoScreen


class HomeNewButtonBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_new_btn_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.optionImage).setOnClickListener {
            val intent = Intent(view.context, SelectImageScreen::class.java)
            dismiss()
            startActivity(intent)
        }

        view.findViewById<TextView>(R.id.optionVideo).setOnClickListener {
            val intent = Intent(view.context, SelectVideoScreen::class.java )
            dismiss()
            startActivity(intent)
        }

    }
}
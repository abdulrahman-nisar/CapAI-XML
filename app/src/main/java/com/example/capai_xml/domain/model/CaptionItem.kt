package com.example.capai_xml.domain.model


data class CaptionItem(
    var id: Int = 0,
    var instagramCaption: String? = "",
    var facebookCaption: String? = "",
    var twitterCaption: String? = "",
    var pinterestCaption: String? = "",
    var linkedinCaption: String? = "",
    var threadCaption: String? = "",
    var snapChatCaption: String? = "",
    var tiktokCaption: String? = "",
    var imageUri : String
) {
    var snapchatCaption: ByteArray = TODO("initialize me")
}

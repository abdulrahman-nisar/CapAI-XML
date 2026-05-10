package com.example.capai_xml.data.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseHistoryService(
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
) {
    private fun historyRoot(userId: String) =
        database.reference.child("users").child(userId).child("history")

    fun pushCaption(userId: String, item: FirebaseCaptionItem) {
        historyRoot(userId).child("captions").push().setValue(item)
    }

    fun pushTranscription(userId: String, item: FirebaseTranscriptionItem) {
        historyRoot(userId).child("transcriptions").push().setValue(item)
    }

    fun fetchHistory(
        userId: String,
        onSuccess: (FirebaseHistoryBundle) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        historyRoot(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val captions = snapshot.child("captions").children.mapNotNull { child ->
                    child.getValue(FirebaseCaptionItem::class.java)
                }
                val transcriptions = snapshot.child("transcriptions").children.mapNotNull { child ->
                    child.getValue(FirebaseTranscriptionItem::class.java)
                }
                onSuccess(FirebaseHistoryBundle(captions, transcriptions))
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.toException())
            }
        })
    }
}


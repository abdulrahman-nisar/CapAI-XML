package com.example.capai_xml.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun CaptionEditorSection(
    initialText: String,
    suggestions: List<String>,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember(initialText) { mutableStateOf(initialText) }
    val clipboardManager = LocalClipboardManager.current

    Column(modifier = modifier) {
        if (suggestions.isNotEmpty()) {
            Text(
                text = "Suggestions",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(suggestions) { suggestion ->
                    AssistChip(
                        onClick = {
                            text = suggestion
                            onTextChanged(text)
                        },
                        label = { Text(text = suggestion, maxLines = 1) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color(0xFF2A2A3E),
                            labelColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Text(
            text = "Edit Caption",
            color = Color.White,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onTextChanged(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Write a caption...") },
            maxLines = 6
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    clipboardManager.setText(AnnotatedString(text))
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
            ) {
                Text(text = "Copy", color = Color.White)
            }
            OutlinedButton(
                onClick = {
                    text = ""
                    onTextChanged("")
                }
            ) {
                Text(text = "Clear", color = Color.White)
            }
            Spacer(modifier = Modifier.width(1.dp))
            Text(
                text = "${text.length} chars",
                color = Color(0x66FFFFFF),
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}


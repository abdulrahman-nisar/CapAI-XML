package com.example.capai_xml.ui.compose

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.example.capai_xml.R
import com.example.capai_xml.domain.model.SourceTable
import com.example.capai_xml.ui.activities.HistoryListItem

@Composable
fun HistoryFeed(
    items: List<HistoryListItem>,
    query: String,
    onItemClick: (HistoryListItem) -> Unit,
    onDownloadClick: (HistoryListItem) -> Unit,
    onDeleteClick: (HistoryListItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredItems = remember(items, query) {
        val q = query.trim()
        if (q.isBlank()) {
            items
        } else {
            items.filter {
                it.name.contains(q, ignoreCase = true) ||
                    it.description.contains(q, ignoreCase = true)
            }
        }
    }

    if (filteredItems.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = if (query.isBlank()) "No history yet" else "No results",
                color = Color(0x66FFFFFF),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(filteredItems, key = { it.source.name + it.id }) { item ->
            HistoryItemCard(
                item = item,
                onItemClick = { onItemClick(item) },
                onDownloadClick = { onDownloadClick(item) },
                onDeleteClick = { onDeleteClick(item) }
            )
        }
    }
}

@Composable
private fun HistoryItemCard(
    item: HistoryListItem,
    onItemClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A3E))
                ) {
                    Box(modifier = Modifier.size(60.dp), contentAlignment = Alignment.Center) {
                        if (item.source == SourceTable.TRANSCRIPTION) {
                            Image(
                                painter = painterResource(id = R.drawable.video),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            HistoryThumbnail(uri = item.imageUri)
                        }
                    }
                }

                Spacer(modifier = Modifier.size(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.description,
                        color = Color(0x66FFFFFF),
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onDownloadClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Text(text = "Download", color = Color.White)
                }
                OutlinedButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text(text = "Delete")
                }
            }
        }
    }
}

@Composable
private fun HistoryThumbnail(uri: String) {
    if (uri.isBlank()) {
        Image(
            painter = painterResource(id = R.drawable.ic_ai_lines),
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            contentScale = ContentScale.Fit
        )
        return
    }

    AndroidView(
        factory = { ctx ->
            ImageView(ctx).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { view ->
            try {
                view.setImageURI(uri.toUri())
            } catch (_: SecurityException) {
                view.setImageDrawable(null)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}



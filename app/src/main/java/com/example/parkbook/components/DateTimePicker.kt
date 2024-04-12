package com.example.parkbook.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun DateTimePickerButton(
    text: String,
    icon: ImageVector,
    onButtonClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,

        modifier = Modifier.padding(16.dp).border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            shape = MaterialTheme.shapes.small
        )

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f).padding(16.dp),


        )
        IconButton(onClick = { onButtonClick() }) {
            Icon(icon, contentDescription = null)
        }
    }
}
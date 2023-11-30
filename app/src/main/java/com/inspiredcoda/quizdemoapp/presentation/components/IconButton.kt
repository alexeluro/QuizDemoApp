package com.inspiredcoda.quizdemoapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.Add,
    text: String = "No Action",
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .clickable {
                onClick?.invoke()
            }
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary
        )
        Text(
            text = text,
            style = TextStyle(
                color = MaterialTheme.colors.onPrimary
            )
        )
    }
}
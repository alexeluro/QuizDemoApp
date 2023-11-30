package com.inspiredcoda.quizdemoapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun QuizTopAppBar(
    title: String,
    navIcon: ImageVector? = null,
    navAction: (() -> Unit)? = null
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary),
        navigationIcon = {
            navIcon?.let {
                Icon(
                    imageVector = navIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            navAction?.invoke()
                        }
                )
            }
        },
        title = { Text(text = title, color = MaterialTheme.colors.onPrimary) }
    )
}
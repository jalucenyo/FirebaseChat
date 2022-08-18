package com.luceno.firebasechat.shared.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun SocialButton(
    text: String,
    onClick: () -> Unit,
    color: Color,
    icon: Painter,
    modifier: Modifier = Modifier
){
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = color
        ),
        border = BorderStroke(
            width = (1.5).dp,
            color = color
        ),
        onClick = onClick,
    ) {
        Icon(painter = icon,
            modifier = Modifier
                .height(32.dp)
                .padding(end = 8.dp),
            tint = Color.Unspecified,
            contentDescription = text)
        Text(text = text, color = color)
    }
}
package com.luceno.firebasechat.shared.composable

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun TextBiColor(
    textStart: String,
    textEnd: String,
    color: Color = MaterialTheme.colors.primary,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    ClickableText(
        modifier = Modifier.composed { modifier },
        text = buildAnnotatedString {
            append (textStart)
            append(" ")
            withStyle(
                style = SpanStyle(
                    color = color,
                    fontWeight = FontWeight.Bold
                )
            ){
                append(textEnd)
            }
        },
        onClick = onClick
    )
}
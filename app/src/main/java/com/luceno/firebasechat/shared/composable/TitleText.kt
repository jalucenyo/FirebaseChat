package com.luceno.firebasechat.shared.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TitleText(
    text: String,
    textAlign: TextAlign = TextAlign.Left,
    modifier: Modifier = Modifier
){
    Text(
        modifier = Modifier.fillMaxWidth().composed { modifier },
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.h5.copy(
            color = MaterialTheme.colors.primary
        ))

}
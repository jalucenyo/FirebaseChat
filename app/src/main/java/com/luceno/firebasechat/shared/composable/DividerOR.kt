package com.luceno.firebasechat.shared.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.luceno.firebasechat.R

@Composable
fun DividerOR(
    modifier: Modifier = Modifier
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .composed { modifier },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Divider(
            modifier = Modifier.width(24.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
        Text(text = stringResource(id = R.string.or),
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.h6.copy(color = Color.Black
            ))
        Divider(
            modifier = Modifier.width(24.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
    }
}
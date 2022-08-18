package com.luceno.firebasechat.firebase_splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luceno.firebasechat.R
import com.luceno.firebasechat.ui.theme.FirebaseChatTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onLoad: () -> Unit,
    delayMin: Long = 2000,
    onNavigateTo: () -> Unit)
{
    LaunchedEffect(key1 = true ){
        onLoad()
        delay(delayMin)
        onNavigateTo()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Image(
            modifier = Modifier.padding( horizontal = 64.dp, vertical = 24.dp),
            painter = painterResource(id = R.drawable.lucenyo_logo_green),
            contentDescription = "Logo Lucenyo developer")

        Text(
            text = stringResource(id = R.string.title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colors.onPrimary
        )

    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    FirebaseChatTheme {
        SplashScreen(
            onLoad = {},
            onNavigateTo = {}
        )
    }
}

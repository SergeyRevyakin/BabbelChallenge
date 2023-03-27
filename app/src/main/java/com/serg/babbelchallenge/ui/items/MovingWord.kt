package com.serg.babbelchallenge.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serg.babbelchallenge.ui.theme.BabbelChallengeTheme
import kotlinx.coroutines.delay

@Composable
fun MovingWord(word: String, onLoose: () -> Unit, paddingTop: Dp) {
    val configuration = LocalConfiguration.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(top = paddingTop)
    ) {
        var currentXCoordinate by remember {
            mutableStateOf(0f)
        }

        val screenHeight = configuration.screenHeightDp.dp - paddingTop

        LaunchedEffect(word) {
            currentXCoordinate = 0f
            while (currentXCoordinate <= screenHeight.value) {
                currentXCoordinate += 1f
                delay(16)
            }
            onLoose.invoke()
        }
        Text(
            text = word,
            modifier = Modifier
                .absoluteOffset(
                    y = currentXCoordinate.dp
                )
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BabbelChallengeTheme {
        MovingWord(word = "Android", paddingTop = 64.dp, onLoose = {})
    }
}
package com.serg.babbelchallenge.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serg.babbelchallenge.R
import com.serg.babbelchallenge.utils.getColoredString
import com.serg.babbelchallenge.utils.getStringWithColoredParam

@Composable
fun GameOverScreen(
    score: Int,
    correctAnswer: String,
    isTimeoutLoose: Boolean,
    onRestartClick: () -> Unit
) {
    val descriptionText =
        when {
            isTimeoutLoose -> getColoredString(
                stringResource(id = R.string.timeout_loose),
                MaterialTheme.colors.primary
            )

            correctAnswer.isEmpty() -> getColoredString(
                stringResource(id = R.string.this_was_correct),
                MaterialTheme.colors.primary
            )

            else -> getStringWithColoredParam(
                stringResource(id = R.string.correct_answer),
                correctAnswer,
                MaterialTheme.colors.primary
            )
        }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.game_over),
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = descriptionText,
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = getStringWithColoredParam(
                    stringResource(id = R.string.your_score),
                    score.toString(),
                    MaterialTheme.colors.primary
                ),
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onRestartClick,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            ) {
                Text(text = stringResource(id = R.string.restart))
            }

        }
    }
}

@Composable
@Preview
fun PreviewGameOverScreen() {
    GameOverScreen(score = 12, "Test", false) {

    }
}
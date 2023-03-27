package com.serg.babbelchallenge.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serg.babbelchallenge.R
import com.serg.babbelchallenge.ui.items.DoubleButton
import com.serg.babbelchallenge.ui.items.GameOverScreen
import com.serg.babbelchallenge.ui.items.MovingWord
import com.serg.babbelchallenge.utils.MainScreenState
import com.serg.babbelchallenge.utils.getStringWithColoredParam

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val screenState by viewModel.uiState.collectAsState()
    val word by viewModel.fallingWord

    AnimatedVisibility(
        visible = screenState is MainScreenState.Loading,
        enter = fadeIn(
            animationSpec = tween(300)
        ),
        exit = fadeOut(
            animationSpec = tween(300)
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    AnimatedVisibility(
        visible = screenState is MainScreenState.Loose,
        enter = fadeIn(
            animationSpec = tween(300)
        ),
        exit = fadeOut(
            animationSpec = tween(0)
        )
    ) {
        val looseScreenState = (screenState as? MainScreenState.Loose)
        looseScreenState?.let {
            GameOverScreen(
                score = looseScreenState.scoreCount,
                correctAnswer = looseScreenState.correctAnswer,
                isTimeoutLoose = looseScreenState.isTimeoutLoose
            ) {
                viewModel.restartGame()
            }
        }
    }

    AnimatedVisibility(
        visible = screenState is MainScreenState.Success,
        enter = fadeIn(
            animationSpec = tween(300)
        ),
        exit = fadeOut(
            animationSpec = tween(300)
        )
    ) {


        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = getStringWithColoredParam(
                        stringResource(R.string.current_word),
                        viewModel.targetWord.value,
                        MaterialTheme.colors.primary
                    ),
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(16.dp),
                    fontSize = 24.sp
                )
                Text(
                    text = getStringWithColoredParam(
                        stringResource(R.string.score),
                        viewModel.score.value.toString(),
                        MaterialTheme.colors.primary
                    ),
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(16.dp),
                    fontSize = 24.sp,
                    textAlign = TextAlign.End
                )

            }
            MovingWord(
                word = word,
                paddingTop = 64.dp,
                onLoose = {
                    viewModel.onLoose(
                        true
                    )
                })

            DoubleButton(
                onLeftButtonClick = { viewModel.onWrongButtonClicked() },
                onRightButtonClick = { viewModel.onCorrectButtonClicked() },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}


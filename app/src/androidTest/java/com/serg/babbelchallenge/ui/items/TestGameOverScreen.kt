package com.serg.babbelchallenge.ui.items

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.serg.babbelchallenge.ComposableItemTest
import org.junit.Assert.assertEquals
import org.junit.Test

class TestGameOverScreen : ComposableItemTest() {

    @Test
    fun testGameOverScreen() = runTest {
        var clickCount = 0

        setContent {
            GameOverScreen(score = 10, correctAnswer = "TestAnswer", isTimeoutLoose = false) {
                clickCount++
            }
        }

        val gameOverButton = onNodeWithText("Restart")
        val answerText = onNodeWithText("The Right Answer is\nTestAnswer")
        val scoreText = onNodeWithText("Your Score:10")

        gameOverButton.assertIsDisplayed()
        answerText.assertIsDisplayed()
        scoreText.assertIsDisplayed()

        gameOverButton.performClick()
        assertEquals(1, clickCount)
    }

    @Test
    fun testGameOverOnTimeout() =runTest {
        setContent {
            GameOverScreen(score = 10, correctAnswer = "", isTimeoutLoose = true) {
            }
        }

        val gameOverText = onNodeWithText("You have been thinking too long :)")
        gameOverText.assertIsDisplayed()
    }
}
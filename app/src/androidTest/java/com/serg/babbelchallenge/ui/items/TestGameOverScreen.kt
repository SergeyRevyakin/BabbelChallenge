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
            GameOverScreen(score = 0, correctAnswer = "TestAnswer", isTimeoutLoose = false) {
                clickCount++
            }
        }

        val gameOverButton = onNodeWithText("Restart")
        val answerText = onNodeWithText("Correct Answer Is TestAnswer")

        gameOverButton.assertIsDisplayed()
        answerText.assertIsDisplayed()

        gameOverButton.performClick()
        assertEquals(1, clickCount)
    }
}
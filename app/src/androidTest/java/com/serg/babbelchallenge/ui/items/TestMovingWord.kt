package com.serg.babbelchallenge.ui.items

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.serg.babbelchallenge.ComposableItemTest
import org.junit.Test

class TestMovingWord : ComposableItemTest() {
    @Test
    fun testMovingWord() = runTest {
        var clickCount = 0

        setContent {
            MovingWord(word = "Test",
                paddingTop = 64.dp,
                onLoose = {
                    clickCount++
                })
        }

        val movingWord = onNodeWithText("Test")

        movingWord.assertIsDisplayed()
    }
}
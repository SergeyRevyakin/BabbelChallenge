package com.serg.babbelchallenge.ui.items

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.serg.babbelchallenge.ComposableItemTest
import org.junit.Assert
import org.junit.Test

class TestDoubleButton : ComposableItemTest() {

    @Test
    fun test() = runTest {
        var leftButtonClickCounter = 0
        var rightButtonClickCounter = 0

        setContent {
            DoubleButton(
                onLeftButtonClick = { leftButtonClickCounter++ },
                onRightButtonClick = { rightButtonClickCounter++ })
        }

        val leftButton = onNodeWithText("Wrong")
        val rightButton = onNodeWithText("Correct")

        leftButton.assertIsDisplayed()
        rightButton.assertIsDisplayed()

        leftButton.performClick()

        rightButton.performClick()
        rightButton.performClick()

        Assert.assertEquals(1, leftButtonClickCounter)
        Assert.assertEquals(2, rightButtonClickCounter)
    }


}
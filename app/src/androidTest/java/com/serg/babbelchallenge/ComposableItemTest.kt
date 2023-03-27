package com.serg.babbelchallenge

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule

abstract class ComposableItemTest {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    fun runTest(body: ComposeContentTestRule.() -> Unit) = composeTestRule.run(body)

}
package com.serg.babbelchallenge.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

fun getStringWithColoredParam(commonText: String, accentText: String, accentColor: Color) =
    buildAnnotatedString {
        append(commonText)

        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
        ) {
            append(accentText)
        }
    }

fun getColoredString(accentText: String, accentColor: Color) =
    buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
        ) {
            append(accentText)
        }
    }
package com.serg.babbelchallenge.utils

import com.serg.babbelchallenge.data.dto.DictionaryItem

sealed interface MainScreenState {
    object Loading : MainScreenState
    data class Error(val message: String?) : MainScreenState
    data class Success(
        var dictionary: MutableList<DictionaryItem>,
    ) : MainScreenState

    data class Loose(val scoreCount: Int, val correctAnswer: String, val isTimeoutLoose: Boolean) :
        MainScreenState
}
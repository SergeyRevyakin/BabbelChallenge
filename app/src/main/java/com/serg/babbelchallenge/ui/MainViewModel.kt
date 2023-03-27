package com.serg.babbelchallenge.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serg.babbelchallenge.data.DictionaryRepository
import com.serg.babbelchallenge.data.dto.DictionaryItem
import com.serg.babbelchallenge.utils.MainScreenState
import com.serg.babbelchallenge.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {
    val score = mutableStateOf(0)
    val targetWord = mutableStateOf("")
    private val correctAnswer = mutableStateOf("")
    val fallingWord = mutableStateOf("")
    private val dictionary = mutableListOf<DictionaryItem>()

    private var _uiState: MutableStateFlow<MainScreenState> =
        MutableStateFlow(MainScreenState.Loading)
    val uiState: StateFlow<MainScreenState> = _uiState

    init {
        startRound()
        viewModelScope.launch {

            dictionaryRepository.getDictionary().map {
                when (it) {
                    is NetworkResult.Loading -> MainScreenState.Loading
                    is NetworkResult.Error -> MainScreenState.Error(null)
                    is NetworkResult.Success -> {
                        dictionary.apply {
                            clear()
                            addAll(it.data)
                        }
                        MainScreenState.Success(
                            dictionary = it.data.toMutableList(),
                        )
                    }
                }
            }.collectLatest { state ->
                _uiState.update {
                    state
                }
            }
        }
    }

    private fun startRound() {
        viewModelScope.launch {
            uiState.collectLatest {
                if (it is MainScreenState.Success) {

                    it.dictionary.apply {
                        shuffle()
                        val random = Random.nextInt(0, 4) + 1
                        val items = subList(0, random)

                        val fallingWordIndex = Random.nextInt(0, items.size)
                        if (Random.nextBoolean()) {
                            targetWord.value = items.first().englishWord
                            correctAnswer.value = items.first().spanishWord
                            fallingWord.value = items[fallingWordIndex].spanishWord
                        } else {
                            targetWord.value = items.first().spanishWord
                            correctAnswer.value = items.first().englishWord
                            fallingWord.value = items[fallingWordIndex].englishWord
                        }
                    }
                }
            }
        }
    }

    fun onCorrectButtonClicked() {
        if (fallingWord.value == correctAnswer.value) increaseScore()
        else onLoose()
    }

    fun onWrongButtonClicked() {
        if (fallingWord.value != correctAnswer.value) increaseScore()
        else onLoose()
    }

    private fun increaseScore() {
        score.value++
        startRound()
    }

    fun onLoose(isTimeOut: Boolean = false) {
        val isTargetWordCorrect = fallingWord.value == correctAnswer.value

        viewModelScope.launch {
            _uiState.emit(
                MainScreenState.Loose(
                    scoreCount = score.value,
                    correctAnswer = if (isTargetWordCorrect) "" else correctAnswer.value,
                    isTimeoutLoose = isTimeOut
                )
            )
        }
    }

    fun restartGame() {
        score.value = 0
        viewModelScope.launch {
            _uiState.emit(MainScreenState.Success(dictionary))
        }
    }
}
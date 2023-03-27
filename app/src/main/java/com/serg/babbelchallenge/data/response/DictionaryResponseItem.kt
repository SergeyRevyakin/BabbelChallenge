package com.serg.babbelchallenge.data.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DictionaryResponseItem(
    @SerialName("text_eng")
    val textEng: String,
    @SerialName("text_spa")
    val textSpa: String
)
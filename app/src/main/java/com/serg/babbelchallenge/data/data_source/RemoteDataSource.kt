package com.serg.babbelchallenge.data.data_source

import com.serg.babbelchallenge.data.response.DictionaryResponseItem
import com.serg.babbelchallenge.utils.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    fun getWords(): Flow<NetworkResult<List<DictionaryResponseItem>>> {
        return flow {
            try {
                emit(NetworkResult.Loading)
                httpClient.get {
                    accept(ContentType.Text.Plain)
                }.let {
                    if (it.status.value == 200) {
                        emit(NetworkResult.Success(it.body()))
                    } else {
                        emit(NetworkResult.Error())
                    }
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e))
            }
        }
    }
}
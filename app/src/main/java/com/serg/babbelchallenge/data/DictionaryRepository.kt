package com.serg.babbelchallenge.data

import com.serg.babbelchallenge.data.data_source.RemoteDataSource
import com.serg.babbelchallenge.data.dto.DictionaryItem
import com.serg.babbelchallenge.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DictionaryRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getDictionary(): Flow<NetworkResult<List<DictionaryItem>>> =
        remoteDataSource.getWords().map { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> NetworkResult.Loading
                is NetworkResult.Error -> NetworkResult.Error(networkResult.exception)
                is NetworkResult.Success -> {
                    val data = networkResult.data.map {
                        DictionaryItem(
                            it.textEng,
                            it.textSpa
                        )
                    }
                    (NetworkResult.Success(data))
                }
            }
        }
}
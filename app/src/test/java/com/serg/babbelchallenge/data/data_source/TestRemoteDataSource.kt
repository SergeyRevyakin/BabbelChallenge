package com.serg.babbelchallenge.data.data_source

import com.serg.babbelchallenge.data.response.DictionaryResponseItem
import com.serg.babbelchallenge.utils.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class TestRemoteDataSource {

    private val testRemoteDataSource = RemoteDataSource(
        getSuccessHttpClient()
    )

    @Test
    fun testSuccessRequest() =
        runBlocking {
            val response = testRemoteDataSource.getWords()
            assertEquals(NetworkResult.Success::class, response.last()::class)
        }

    @Test
    fun testCounterSuccessRequest() =
        runBlocking {
            val response = testRemoteDataSource.getWords().last() as NetworkResult.Success
            assertEquals(297, response.data.size)
        }

    @Test
    fun testContainsSuccessRequest() =
        runBlocking {
            val response = testRemoteDataSource.getWords().last() as NetworkResult.Success
            assert(
                response.data.contains(
                    DictionaryResponseItem(
                        textEng = "primary school",
                        textSpa = "escuela primaria"
                    )
                )
            )
        }

    private val testErrorRemoteDataSource = RemoteDataSource(
        getErrorHttpClient()
    )

    @Test
    fun testErrorRequest() =
        runBlocking {
            val response = testErrorRemoteDataSource.getWords()
            assertEquals(NetworkResult.Error::class, response.last()::class)
        }

    private fun getSuccessHttpClient(): HttpClient {
        val engine = MockEngine {
            respond(
                content = ByteReadChannel(ResponseConstants.SUCCESS_RESPONSE),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "text/plain")
            )

        }

        val httpClient = HttpClient(
            engine = engine
        ) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }, contentType = ContentType.Text.Plain)
            }
            expectSuccess = false
        }
        return httpClient
    }

    private fun getErrorHttpClient(): HttpClient {
        val engine = MockEngine {
            respond(
                content = ByteReadChannel(ResponseConstants.EMPTY_RESPONSE),
                status = HttpStatusCode.NotFound,
                headers = headersOf(HttpHeaders.ContentType, "text/plain")
            )

        }

        val httpClient = HttpClient(
            engine = engine
        ) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }, contentType = ContentType.Text.Plain)
            }
            expectSuccess = false
        }
        return httpClient
    }

}
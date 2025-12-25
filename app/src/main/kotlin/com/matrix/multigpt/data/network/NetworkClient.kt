package com.matrix.multigpt.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Singleton
class NetworkClient @Inject constructor(
    private val httpEngine: HttpClientEngineFactory<*>
) {

    private val client by lazy {
        HttpClient(httpEngine) {
            expectSuccess = true

            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                        allowSpecialFloatingPointValues = true
                        useArrayPolymorphism = true
                    }
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT.toLong()
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = if (com.matrix.multigpt.BuildConfig.DEBUG) LogLevel.HEADERS else LogLevel.NONE
                sanitizeHeader { header -> 
                    // Sanitize all credential-related headers
                    header == HttpHeaders.Authorization || 
                    header.lowercase().contains("key") || 
                    header.lowercase().contains("token") ||
                    header.lowercase().contains("credential") ||
                    header.lowercase().contains("secret")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    operator fun invoke(): HttpClient = client

    companion object {
        private const val TIMEOUT = 1_000 * 60 * 5
    }
}

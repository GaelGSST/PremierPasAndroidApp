package com.example.premierpas.data.remote

import com.example.premierpas.data.remote.dto.AnimalApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class AnimalApiService {
    private val httpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun fetchAnimals(): AnimalApiResponse {
        return httpClient.get("https://extinct-api.herokuapp.com/api/v1/animal/").body()
    }

    fun close() {
        httpClient.close()
    }
}

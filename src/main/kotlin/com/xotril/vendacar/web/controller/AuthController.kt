package com.xotril.vendacar.web.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/auth")
class AuthController(

    @Value("\${keycloak.token-uri}")
    private val tokenUri: String,

    @Value("\${keycloak.client-id}")
    private val clientId: String,

    @Value("\${keycloak.client-secret}")
    private val clientSecret: String,

    @Value("\${keycloak.base-url}")
    private val keycloakBaseUrl: String,

    @Value("\${keycloak.realm}")
    private val realm: String,
) {

    private val restTemplate = RestTemplate()

    data class LoginRequest(val username: String, val password: String)

    data class TokenResponse(
        val access_token: String?,
        val expires_in: Int?,
        val refresh_expires_in: Int?,
        val refresh_token: String?,
        val token_type: String?,
        val scope: String?
    )

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): TokenResponse {
        val formData = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "password")
            add("client_id", clientId)
            add("client_secret", clientSecret)
            add("username", request.username)
            add("password", request.password)
        }

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val entity = HttpEntity(formData, headers)

        val response = restTemplate.exchange(
            tokenUri,
            HttpMethod.POST,
            entity,
            TokenResponse::class.java
        )

        return response.body!!
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<String> {
        val adminToken = getAdminAccessToken()

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(adminToken)
        }

        val userPayload = mapOf(
            "username" to request.username,
            "email" to request.email,
            "enabled" to true,
            "attributes" to mapOf(
                "cpf" to listOf(request.cpf) // Keycloak exige LISTA sempre
            ),
            "credentials" to listOf(
                mapOf(
                    "type" to "password",
                    "value" to request.password,
                    "temporary" to false
                )
            )
        )

        val entity = HttpEntity(userPayload, headers)

        val response = restTemplate.postForEntity(
            "$keycloakBaseUrl/admin/realms/$realm/users",
            entity,
            String::class.java
        )

        return ResponseEntity.status(response.statusCode).body("User created")
    }

    data class RegisterRequest(
        val username: String,
        val email: String,
        val password: String,
        val cpf: String,
    )
    fun getAdminAccessToken(): String {
        val formData = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "client_credentials")
            add("client_id", clientId)
            add("client_secret", clientSecret)
        }

        val entity = HttpEntity(formData, HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        })

        val response = restTemplate.exchange(
            tokenUri,
            HttpMethod.POST,
            entity,
            TokenResponse::class.java
        )

        val token = response.body?.access_token
            ?: throw RuntimeException("Failed to get admin token")

        return token
    }
}

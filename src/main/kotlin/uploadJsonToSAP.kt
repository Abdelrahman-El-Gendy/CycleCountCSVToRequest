package org.example

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.example.Constants.CREATE_CYCLE_COUNT_QAS_TEST
import org.example.Constants.PASSWORD
import org.example.Constants.USERNAME
import java.io.File
import java.net.UnknownHostException
import java.util.Base64

class SapClient {
    private var csrfToken: String? = null
    private val cookieJar = InMemoryCookieJar
    private val client = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .build()
    private fun getAuthHeader(): String {
        val credentials = "$USERNAME:$PASSWORD"
        return "Basic " + Base64.getEncoder().encodeToString(credentials.toByteArray())
    }
    private fun getCsrfToken(): String? {
        // Return existing token if we have one
        if (!csrfToken.isNullOrEmpty()) {
            return csrfToken
        }

        try {
            val getRequest = Request.Builder()
                .url(CREATE_CYCLE_COUNT_QAS_TEST)
                .addHeader("Authorization", getAuthHeader())
                .addHeader("X-CSRF-Token", "Fetch")
                .get()
                .build()

            client.newCall(getRequest).execute().use { response ->
                if (!response.isSuccessful) {
                    println("❌ Failed to get CSRF token. Status code: ${response.code}")
                    return null
                }

                csrfToken = response.headers["X-CSRF-Token"]
                    ?: response.headers["CSRF-Token"]
                    ?: response.headers["X-XSRF-TOKEN"]

                if (csrfToken == null) {
                    println("❌ No CSRF token found in response headers")
                    println("Response headers: ${response.headers}")
                    return null
                }

                println("✅ CSRF token retrieved successfully")
                return csrfToken
            }
        } catch (e: Exception) {
            println("❌ Error getting CSRF token: ${e.message}")
            return null
        }
    }
    fun uploadJsonToSAP(jsonFilePath: String) {
        try {
            // First get the CSRF token
            val token = getCsrfToken()
            if (token == null) {
                println("❌ Cannot proceed without CSRF token")
                return
            }

            // Read JSON file
            val json = File(jsonFilePath).readText(Charsets.UTF_8)

            // Create request body
            val mediaType = "application/json".toMediaType()
            val requestBody = json.toRequestBody(mediaType)

            // Build HTTP request with CSRF token
            val request = Request.Builder()
                .url(CREATE_CYCLE_COUNT_QAS_TEST)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", getAuthHeader())
                .addHeader("X-CSRF-Token", token)
                .post(requestBody)
                .build()

            // Execute request
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    println("✅ Upload successful: ${response.code}")
                    println(response.body?.string())
                } else {
                    println("❌ Upload failed: ${response.code}")
                    println(response.body?.string())
                    println("Response headers: ${response.headers}")
                    
                    // If we get a 403 with CSRF error, invalidate token and retry
                    if (response.code == 403 && response.body?.string()?.contains("CSRF", ignoreCase = true) == true) {
                        csrfToken = null  // Invalidate token
                        println("🔄 CSRF token expired, retrying request...")
                        uploadJsonToSAP(jsonFilePath)  // Retry the request
                    }
                }
            }
        } catch (e: UnknownHostException) {
            println("❌ Cannot connect to host: ${e.message}")
            println("Please check:\n" +
                    "1. Your internet connection\n" +
                    "2. The hostname is correct\n" +
                    "3. The host is accessible from your network")
        } catch (e: Exception) {
            println("❌ Error occurred: ${e.message}")
        }
    }
}
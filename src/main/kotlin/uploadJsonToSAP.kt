package org.example

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.example.Constants.CREATE_CYCLE_COUNT_QAS_TEST
import org.example.Constants.PASSWORD
import org.example.Constants.USERNAME
import java.io.File
import java.util.Base64

fun uploadJsonToSAP(jsonFilePath: String) {

    // Read JSON file
    val json = File(jsonFilePath).readText(Charsets.UTF_8)

    // Basic auth (replace with actual credentials)
    val username = USERNAME
    val password = PASSWORD
    val credentials = "$username:$password"
    val authHeader = "Basic " + Base64.getEncoder().encodeToString(credentials.toByteArray())

    // Create request body
    val mediaType = "application/json".toMediaType()
    val requestBody = RequestBody.create(mediaType, json)

    // Build HTTP request
    val request = Request.Builder()
        .url(CREATE_CYCLE_COUNT_QAS_TEST)
        .addHeader("Content-Type", "application/json")
        .addHeader("Accept", "application/json")
        .addHeader("Authorization", authHeader)
        .post(requestBody)
        .build()

    // Execute request
    val client = OkHttpClient()
    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            println("✅ Upload successful: ${response.code}")
            println(response.body?.string())
        } else {
            println("❌ Upload failed: ${response.code}")
            println(response.body?.string())
        }
    }
}

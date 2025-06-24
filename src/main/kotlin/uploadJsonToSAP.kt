package org.example

import okhttp3.*
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import java.io.File
import java.util.Base64

fun uploadJsonToSAP(jsonFilePath: String) {
    val url = "http://prd-erp-dia.hyperone.com:8000/sap/bc/zppc_ccount_up/ZCREATE_CYCLE_COUNT/?sap-client=200"

    // Read JSON file
    val json = File(jsonFilePath).readText(Charsets.UTF_8)

    // Basic auth (replace with actual credentials)
    val username = "ppcuser"
    val password = "ppc1234"
    val credentials = "$username:$password"
    val authHeader = "Basic " + Base64.getEncoder().encodeToString(credentials.toByteArray())

    // Create request body
    val mediaType = "application/json".toMediaType()
    val requestBody = RequestBody.create(mediaType, json)

    // Build HTTP request
    val request = Request.Builder()
        .url(url)
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

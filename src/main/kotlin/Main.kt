package org.example

import org.example.Constants.CSV_FILE_PATH
import org.example.Constants.JSON_FILE_PATH

fun main() {

    //Reading CSV file and Converting it to Json file
//    val savedPath = readCsvToRequestBody(CSV_FILE_PATH, JSON_FILE_PATH)
//    println("✅ JSON exported to: $savedPath")


    /**
     * @author Abdelrahman Eid 6/24/2025 in order uploading the json file directly we have implemented this method
     * @param jsonFilePath is the file path for the converted CSV file into json file
     */

//    val jsonFilePath = "C:\\Users\\Abdelrahman\\Downloads\\PPC_cyclecount_202506220436"
//    val jsonFilePath = "C:\\Users\\Abdelrahman\\Downloads\\PPC_cyclecount_202506220532"
//    val jsonFilePath = "C:\\Users\\Abdelrahman\\Downloads\\PPC_cyclecount_202506220533"
    val sapClient = SapClient()
    sapClient.uploadJsonToSAP(jsonFilePath = "C:\\Users\\Abdelrahman\\Downloads\\test.json")
}

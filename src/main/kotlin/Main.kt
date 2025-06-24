package org.example

fun main() {

    //Reading CSV file and Converting it to Json file
    val csvPath = "C:\\Users\\Abdelrahman\\Downloads\\PPC_cyclecount_202506220436.csv"
    val jsonPath = "C:\\Users\\Abdelrahman\\Downloads\\PPC_cyclecount_202506220436.json"
    val savedPath = readCsvToRequestBody(csvPath, jsonPath)
    println("✅ JSON exported to: $savedPath")



    /**
     * @author Abdelrahman Eid 6/24/2025 in order uploading the json file directly we have implemented this method
     * @param jsonFilePath is the file path for the converted CSV file into json file
     */

//    val jsonFilePath = "C:\\Users\\Abdelrahman\\Downloads\\PPC_cyclecount_202506220436"
//    val jsonFilePath = "C:\\Users\\Abdelrahman\\Downloads\\PPC_cyclecount_202506220532"
//    val jsonFilePath = "C:\\Users\\Abdelrahman\\Downloads\\PPC_cyclecount_202506220533"
    uploadJsonToSAP(jsonFilePath = "")
}

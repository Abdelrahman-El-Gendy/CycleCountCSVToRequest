package org.example

import com.google.gson.GsonBuilder
import com.opencsv.CSVReader
import java.io.File
import java.io.FileReader

//data class InventoryItem(
//    val PHYSINVENTORY: String,
//    val FISCALYEAR: String,
//    val ITEM: String,
//    val MATERIAL: String,
//    val EAN11: String,
//    val ENTRY_QNT: String,
//    val ENTRY_UOM: String,
//    val ZERO_COUNT: String,
//    val BASE_UOM: String
//)
//
//data class SerialCount(
//    val PHYSINVENTORY: String,
//    val FISCALYEAR: String,
//    val ITEM: String,
//    val SERIALNO: String
//)

fun readCsvToRequestBody(filePath: String, outputJsonPath: String): String {
    val reader = CSVReader(FileReader(filePath))
    val lines = reader.readAll()
    val headers = lines.first()
    val dataLines = lines.drop(1)

    val items = mutableListOf<InventoryItem>()
    val serials = mutableListOf<SerialCount>()

    dataLines.forEach { row ->
        val rowMap = headers.zip(row).toMap()

        items.add(
            InventoryItem(
                PHYSINVENTORY = rowMap["PHYSINVENTORY"] ?: "",
                FISCALYEAR = rowMap["FISCALYEAR"] ?: "",
                ITEM = rowMap["ITEM"]?.trimStart('0') ?: "",
                MATERIAL = rowMap["MATERIAL"]?.trimStart('0') ?: "",
                EAN11 = rowMap["EAN11"] ?: "",
                ENTRY_QNT = rowMap["QTY"] ?: "",
                ENTRY_UOM = rowMap["MEINH"] ?: "", //BASE_UOM
                ZERO_COUNT = rowMap["ZERO_COUNT"] ?: "",
                BASE_UOM = rowMap["BASE_UOM"]?.trimStart('0') ?: ""
            )
        )
    }

    val requestBody = mapOf(
        "IT_ITEMS" to items,
    )

    val gson = GsonBuilder().setPrettyPrinting().create()
    val jsonOutput = gson.toJson(requestBody)

    File(outputJsonPath).writeText(jsonOutput)

    return outputJsonPath
}

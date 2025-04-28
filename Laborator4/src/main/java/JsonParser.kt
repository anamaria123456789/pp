package org.example

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonParser : Parser {
    private val gson = Gson()

    override fun parse(text: String): Map<String, Any> {
        return if (text.trimStart().startsWith("[")) {
            // Handle JSON array
            parseArray(text)
        } else if (text.trimStart().startsWith("{")) {
            // Handle JSON object
            parseObject(text)
        } else {
            throw IllegalArgumentException("Invalid JSON: Expected array or object")
        }
    }

    private fun parseArray(text: String): Map<String, Any> {
        // Use TypeToken to capture generic type information for List<Map<String, Any>> at runtime
        val type = object : TypeToken<List<Map<String, Any>>>() {}.type
        val list: List<Map<String, Any>> = gson.fromJson(text, type)

        return mapOf("data" to list) // Wrap the list in a map: key => data, value => list
    }

    private fun parseObject(text: String): Map<String, Any> {
        val type = object : TypeToken<Map<String, Any>>() {}.type

        return gson.fromJson(text, type) // Directly return the parsed object
    }
}

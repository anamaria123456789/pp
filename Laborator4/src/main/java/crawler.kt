package org.example

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class Crawler (private val url: String) {
    private val client = OkHttpClient()

    private fun getResource() : Response {
        val request = Request
            .Builder()
            .url(url)
            .build()

        return client
            .newCall(request)
            .execute()
    }

    fun processContent(contentType: String)
    {
        val response = getResource()
        val content = response.body?.string() ?: throw IllegalStateException("Response body is null!")

        when (contentType)
        {
            "json" -> {
                val jsonParser = JsonParser()
                val parsedData = jsonParser.parse(content)

                println("Parsed JSON: $parsedData")
            }

            "xml" -> {
                val xmlParser = XmlParser()
                val parsedData = xmlParser.parse(content)

                println("Parsed XML: $parsedData")
            }

            "yaml" -> {
                val yamlParser = YamlParser()
                val parsedData = yamlParser.parse(content)

                println("Parsed YAML: $parsedData")
            }

            else -> println("Unsupported content type $contentType")
        }
    }
}
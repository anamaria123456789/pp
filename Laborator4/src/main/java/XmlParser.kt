package org.example

import org.jsoup.Jsoup

class XmlParser : Parser {
    override fun parse(text: String): Map<String, String> {
        val document = Jsoup.parse(text)
        val map = mutableMapOf<String, String>()

        document.children().forEach() { element ->
            map[element.tagName()] = element.text()
        }

        return map
    }
}

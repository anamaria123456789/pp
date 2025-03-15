package ro.mike.tuiasi

import java.io.File
import java.net.URL
import java.nio.charset.Charset


class TextProcessor {
    companion object {
        // Funcții existente rămân neschimbate
        // ...
        fun removeMultipleSpaces(text: String): String {
            return text.replace(Regex("\\s+"), " ")
        }

        // Elimină linii noi multiple
        fun removeMultipleNewlines(text: String): String {
            return text.replace(Regex("\n+"), "\n")
        }

        // Elimină numere de pagină (ex: "   123   ")
        fun removePageNumbers(text: String): String {
            return text.replace(Regex("\\s+\\d+\\s+"), " ")
        }

        // Elimină numele autorului (secvențe de 2+ cuvinte pe linie separată)
        fun removeAuthor(text: String): String {
            return text.replace(Regex("(?m)^\\s*([A-Z][a-z]+\\s+){2,}\\s*$"), "")
        }

        // Elimină titlurile capitolelor (ex: "Capitolul I", "Chapter 1")
        fun removeChapterTitles(text: String): String {
            return text.replace(Regex("(?i)(capitolu?l|chapter)\\s+[IVXLCDM0-9]+.*$"), "")
        }

        fun normalizeNewlines(text: String): String {
            return text.replace(Regex("\r\n"), "\n")
                .replace(Regex("\r"), "\n")
        }

        fun fixRomanianCharacters(text: String): String {
            val replacementMap = mapOf(
                "ş" to "ș", "ţ" to "ț",
                "Ş" to "Ș", "Ţ" to "Ț",
                "ã" to "ă", "Ã" to "Ă",
                "â" to "î", "Â" to "Î",
                "á" to "a", "é" to "e",
                "í" to "i", "ó" to "o",
                "ú" to "u"
            )
            return replacementMap.entries.fold(text) { acc, (old, new) ->
                acc.replace(old, new)
            }
        }

        fun processText(text: String, niceToHave: Boolean = false): String {
            var processed = text
                .let { normalizeNewlines(it) }
                .let { removeMultipleSpaces(it) }
                .let { removeMultipleNewlines(it) }
                .let { removePageNumbers(it) }

            if(niceToHave) {
                processed = processed
                    .let { removeAuthor(it) }
                    .let { removeChapterTitles(it) }
                    .let { fixRomanianCharacters(it) }
            }

            return processed.trim()
        }
    }
}

fun readContent(source: String, charset: Charset = Charsets.UTF_8): String {
    return when {
        source.startsWith("http://") || source.startsWith("https://") -> {
            URL(source).openStream().bufferedReader(charset).use { it.readText() }
        }
        else -> {
            File(source).readText(charset)
        }
    }
}

fun main() {
    println("""
        ====================================
        Procesor E-book - Text Cleaner 2.0
        ====================================
        Introdu sursa (cale fișier local sau URL):
    """.trimIndent())

    val source = readLine()?.trim() ?: ""

    println("Introdu encoding-ul (UTF-8, Windows-1252, ISO-8859-2):")
    val charsetName = readLine()?.trim() ?: "UTF-8"
    val charset = try {
        Charset.forName(charsetName)
    } catch (e: Exception) {
        println("Encoding invalid, folosim UTF-8")
        Charsets.UTF_8
    }

    try {
        val inputText = readContent(source, charset)
        val processedText = TextProcessor.processText(inputText, niceToHave = true)

        File("output.txt").writeText(processedText, Charsets.UTF_8)

        println("""
            Procesare completă cu succes!
            Rezultat salvat în: ${File("output.txt").absolutePath}
            Encoding output: UTF-8
        """.trimIndent())

    } catch (e: Exception) {
        println("""
            Eroare la procesare:
            ${e.message}
            
            Soluții posibile:
            1. Verificați encoding-ul fișierului sursă
            2. Asigurați-vă că sursa conține text simplu
            3. Încercați un alt encoding (ex: Windows-1252)
        """.trimIndent())
    }
}
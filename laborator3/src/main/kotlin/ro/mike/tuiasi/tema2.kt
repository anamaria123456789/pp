import java.io.File

fun main() {
    val inputFile = "input.txt"
    val outputFile = "output.txt"

    try {
        val text = File(inputFile).readText() // Citește textul din fișier
        val cleanedText = cleanText(text) // Curăță textul conform cerințelor
        File(outputFile).writeText(cleanedText) // Salvează textul curățat
        println("Procesare completă! Rezultatul a fost salvat în $outputFile")
    } catch (e: Exception) {
        println("Eroare la citirea fișierului: ${e.message}")
    }
}

fun cleanText(text: String): String {
    var result = text

    //elimin spatii multiple
    result = result.replace(Regex("\\s+"), " ")

    // elimin salturi multiple de linie
    result = result.replace(Regex("\n{2,}"), "\n")

    // elimin numerele de pagina
    result = result.replace(Regex("\\s+\\d+\\s+"), "\n")

    return result.trim() // Elimină spațiile inutile de la început și sfârșit
}
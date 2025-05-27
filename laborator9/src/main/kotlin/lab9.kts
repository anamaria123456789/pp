import java.io.File
import kotlin.*


abstract class FileTypeHandler {
    protected var next: FileTypeHandler? = null

    fun setNext(handler: FileTypeHandler): FileTypeHandler {
        this.next = handler
        return handler
    }

    fun handle(content: String): String? {
        return if (canHandle(content)) {
            getCommand(content)
        } else {
            next?.handle(content)
        }
    }

    protected abstract fun canHandle(content: String): Boolean
    protected abstract fun getCommand(content: String): String
}

class KotlinHandler : FileTypeHandler() {
    override fun canHandle(content: String): Boolean {
        return content.contains("fun ") && content.contains("when")
    }

    override fun getCommand(content: String): String {
        val tempFile = File.createTempFile("temp", ".kt")
        tempFile.writeText(content)
        return "kotlinc ${tempFile.absolutePath} -include-runtime -d temp.jar && java -jar temp.jar"
    }
}

class PythonHandler : FileTypeHandler() {
    override fun canHandle(content: String): Boolean {
        return content.contains("def ") || content.contains("#!") && content.contains("python")
    }

    override fun getCommand(content: String): String {
        val tempFile = File.createTempFile("temp", ".py")
        tempFile.writeText(content)
        return "python ${tempFile.absolutePath}"
    }
}

class JavaHandler : FileTypeHandler() {
    override fun canHandle(content: String): Boolean {
        return content.contains("public static void main")
    }

    override fun getCommand(content: String): String {
        val tempFile = File.createTempFile("Temp", ".java")
        tempFile.writeText(content)
        val className = tempFile.nameWithoutExtension
        return "javac ${tempFile.absolutePath} && java -cp ${tempFile.parent} $className"
    }
}

class BashHandler : FileTypeHandler() {
    override fun canHandle(content: String): Boolean {
        return content.contains("#!/bin/bash") || content.contains("echo ") || content.contains("if [")
    }

    override fun getCommand(content: String): String {
        val tempFile = File.createTempFile("temp", ".sh")
        tempFile.writeText(content)
        tempFile.setExecutable(true)
        return "source ${tempFile.absolutePath}"
    }
}

object CommandExecutor {
    fun executeCommand(command: String): String {
        return try {
            val process = ProcessBuilder("/bin/bash", "-c", command)
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readText()
            process.waitFor()
            output
        } catch (e: Exception) {
            "Eroare la rulare: ${e.message}"
        }
    }
}

fun main() {
    println("Introduceți calea către fișierul fără extensie:")
    val path = readln()
    val file = File(path)

    if (!file.exists()) {
        println("Fișierul nu există.")
        return
    }

    val content = file.readText()

    // Setăm lanțul
    val kotlinHandler = KotlinHandler()
    val pythonHandler = PythonHandler()
    val javaHandler = JavaHandler()
    val bashHandler = BashHandler()

    kotlinHandler.setNext(pythonHandler).setNext(javaHandler).setNext(bashHandler)

    val command = kotlinHandler.handle(content)

    if (command != null) {
        println("Execut comanda: $command")
        val output = CommandExecutor.executeCommand(command)
        println("Output:\n$output")
    } else {
        println("Nu s-a putut determina tipul fișierului.")
    }
}

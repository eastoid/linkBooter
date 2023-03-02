//  -   Can add VBS file to execute without gui

import java.io.File
import java.io.FileWriter

val appdata = System.getenv("APPDATA")
val del = "$appdata\\Microsoft\\Windows\\Start Menu\\Programs"
val fileName = "TemporaryStartupLink.bat"
val sourceFile = "$del\\startup\\$fileName"
val delFile = File("$del\\startupDeleter.bat")
val delString = "cd C:\\Users\\strom\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs"
val delString2 = "start startupDeleter.bat"
val delString3 = "exit"
val newFile = "TemporaryStartupFile.bat"

fun main() {
    deleter()
    println("Enter link: ")
    val input = readLine()
    val file = File("$del\\startup\\$fileName")
    val secondFile = File("$del\\startup\\$newFile")

    if (file.exists()) {
        rewriter()
        if (secondFile.exists()) {
            oldDeleter()
            val writer = FileWriter(secondFile, true)
            writer.write("start $input\ncd $del\nstart startupDeleter.bat\nexit")
            writer.close()
            renamer()
            println("Entry added.")
        }

    } else {
        file.createNewFile()
        val writer = FileWriter(file, true)
        writer.write("start $input\ncd $del\nstart startupDeleter.bat\nexit")
        println("Written entry with delete string. (New file)")
        writer.close()

    }

}

fun deleter() {
    if (!delFile.exists()) {
        delFile.createNewFile()
        Thread.sleep(100)
        val delWriter = FileWriter(delFile, true)
        delWriter.write("cd $del\\startup\ndel $fileName\nexit")
        delWriter.close()
        println("Created deletion file.")
    }
}

fun rewriter() {
    val newFile = File("$del\\startup\\$newFile")
    val sourceFile = File(sourceFile)

    sourceFile.forEachLine { line ->
        when {
            line.contains(delString) -> {
            }
            line.contains(delString2) -> {
            }
            line.contains(delString3) -> {
            }
            else -> {
                newFile.appendText(line)
                newFile.appendText("\n")
            }
        }
    }
}

fun renamer() {
    val newFileObj = File("$del\\startup\\$newFile")
    val newFileNameObj = File("$del\\startup\\$fileName")

    val success = newFileObj.renameTo(newFileNameObj)
    if (success) {
        //github.com/eastoid
    }
}

fun oldDeleter() {
    val oldFile = File("$del\\startup\\$fileName")
    
    try {
        oldFile.delete()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

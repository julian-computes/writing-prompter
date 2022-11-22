package org.julian.prompter

import edu.stanford.nlp.simple.Document
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.random.Random

class WritingPrompter(sourceFiles: List<File>) {
    private val minPromptLength = 1
    private val maxPromptLength = 3

    private val sourceFileTexts = sourceFiles.parallelStream().map {
        val text =
            it.readText(Charsets.UTF_8)
                .replace("\n", " ")
                .replace("\"", "")
                .replace("“", " ")
                .replace("”", " ")
                .replace("\r", "")
                .replace("\\s+".toRegex(), " ")

        val doc = Document(text)
        doc.sentences()
    }.toList()

    fun generatePrompts(numPrompts: Int) = sequence {
        for (i in 1..numPrompts) {
            yield(generatePrompt())
        }
    }

    private fun generatePrompt(): String {
        // Pick a file at random
        val text = sourceFileTexts.random()
        val startTokenIdx = Random.nextInt(text.size)
        val length =
            Random.nextInt(minPromptLength, maxPromptLength).coerceAtMost(text.size - startTokenIdx)
        return text.slice(startTokenIdx until startTokenIdx + length).joinToString(" ")
    }
}

fun loadFilesFromDataFolder(dataFolderPath: Path): List<File> {
    val dataFolder = dataFolderPath.toFile()

    if (!dataFolder.exists()) {
        throw FileNotFoundException("Data folder not found: $dataFolderPath")
    }

    return dataFolder.walk().filter {
        it.absolutePath.endsWith(".txt")
    }.toList()
}

fun main(args: Array<String>) {

    val dataFolderPath =
        if (args.isEmpty())
            Paths.get("src", "main", "resources", "data")
        else
            Paths.get(args[0])


    val prompter = WritingPrompter(
        loadFilesFromDataFolder(dataFolderPath)
    )

    val prompts = prompter.generatePrompts(30)
    for ((idx, prompt) in prompts.withIndex()) {
        println("Prompt ${idx + 1}:")
        println(prompt)
        println()
    }
}
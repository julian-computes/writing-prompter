package org.julian.prompter

import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.test.assertEquals

internal class WritingPrompterTest {

    @Test
    fun testFindFiles() {
        val files = loadFilesFromDataFolder(Paths.get("src", "test", "resources"))
            .map { it.nameWithoutExtension }
        assertEquals(
            setOf("file1", "file2"),
            files.toSet()
        )
    }

    @Test
    fun testSentences() {
        val prompter = WritingPrompter(
            loadFilesFromDataFolder(
                Paths.get(
                    "src", "test", "resources"
                )
            )
        )

        val numPrompts = 10
        val prompts = prompter.generatePrompts(numPrompts).toList()
        assertEquals(numPrompts, prompts.size)
    }
}
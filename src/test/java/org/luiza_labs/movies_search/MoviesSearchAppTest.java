package org.luiza_labs.movies_search;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoviesSearchAppTest {

    @TempDir
    Path tempDir;

    @Test
    void testMainWithNoArguments() throws Exception {
        // Redirect output to capture it
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream, true, "UTF-8"));

        // Simular execução do main sem argumentos
        String[] args = {};
        MoviesSearchApp.main(args);

        // Restaurar saída padrão
        System.setOut(originalOut);

        // Verificar a saída esperada
        String output = outputStream.toString("UTF-8");
        assertEquals("Por favor, forneça um termo de pesquisa.\n", output);
    }

    @Test
    void testMainWithSearchTerm() throws Exception {
        // Preparar o arquivo ZIP fictício e o diretório de destino
        File zipFile = tempDir.resolve("movies.zip").toFile();
        Path destDir = tempDir.resolve("data");
        Files.createDirectory(destDir);

        // Criar um arquivo ZIP de teste com um arquivo de texto
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            ZipEntry entry = new ZipEntry("testfile.txt");
            zos.putNextEntry(entry);
            zos.write("example content\n".getBytes());
            zos.closeEntry();
        }

        // Redirect output to capture it
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream, true, "UTF-8"));

        // Simular execução do main com argumento
        String[] args = {"example"};
        MoviesSearchApp.main(args);

        // Restaurar saída padrão
        System.setOut(originalOut);

        // Verificar a saída esperada
        String output = outputStream.toString("UTF-8");
        assertEquals("Foram encontradas 1 ocorrências pelo termo \"example\".\nOs arquivos que possuem \"example\" são:\ntestfile.txt\n", output);
    }
}

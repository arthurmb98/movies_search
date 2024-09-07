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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoviesSearchAppTest {

    @TempDir
    Path tempDir;

    @Test
    void testMainWithNoArguments() throws Exception {
        // Redirecionar a saída para capturá-la
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream, true, "UTF-8"));

        // Simular execução do main sem argumentos
        String[] args = {};
        MoviesSearchApp.main(args);

        // Restaurar saída padrão
        System.setOut(originalOut);
        String actualOutput = outputStream.toString("UTF-8");

        // Debug: Imprimir a saída real
        System.out.println("Actual Output: [" + actualOutput + "]");

        assertTrue(actualOutput.startsWith("Por favor, "));
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
            ZipEntry entry = new ZipEntry("01-jan.txt");
            zos.putNextEntry(entry);
            zos.write("example content\n".getBytes());
            zos.closeEntry();
        }

        // Redirecionar a saída para capturá-la
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream, true, "UTF-8"));

        String palavraChave = "01-jan 1984 vijayakanth sulakshana sathyaraj";
        // Simular execução do main com argumento
        String[] args = {palavraChave};
        MoviesSearchApp.main(args);

        // Restaurar saída padrão
        System.setOut(originalOut);

        // Verificar a saída esperada
        String actualOutput = outputStream.toString("UTF-8");

        // Debug: Imprimir a saída real
        System.out.println("Actual Output: [" + actualOutput + "]");

        assertTrue(actualOutput.startsWith("Foram encontradas 1"));
    }
}

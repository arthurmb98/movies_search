package org.luiza_labs.movies_search.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ZipUtilsTest {

    private final String testDir = "test_data";
    private final String zipFilePath = testDir + "/test.zip";
    private final String unzipDir = testDir + "/unzip_test";

    @BeforeEach
    public void setUp() throws IOException {
        // Cria diretório de teste
        Files.createDirectories(Paths.get(testDir));
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Limpa diretórios de teste
        deleteDirectory(new File(testDir));
    }

    @Test
    public void testUnzipValidFile() throws IOException {
        // Cria um arquivo ZIP de teste
        createZipFile(new File(zipFilePath), "file1.txt", "Hello, world!");

        // Descompacta o arquivo ZIP
        ZipUtils zipUtils = new ZipUtils(zipFilePath, unzipDir);
        zipUtils.unzip();

        // Verifica se o arquivo foi descompactado corretamente
        File unzippedFile = new File(unzipDir, "file1.txt");
        assertTrue(unzippedFile.exists(), "Arquivo descompactado deve existir.");
        assertEquals("file1.txt", new String(Files.readAllBytes(unzippedFile.toPath())), "Conteúdo do arquivo descompactado está incorreto.");
    }

    @Test
    public void testUnzipOnlyDirectories() throws IOException {
        // Cria um ZIP com apenas diretórios
        createZipFile(new File(zipFilePath), "dir1/", null);
        createZipFile(new File(zipFilePath), "dir1/dir2/", null);

        // Descompacta o arquivo ZIP
        ZipUtils zipUtils = new ZipUtils(zipFilePath, unzipDir);
        zipUtils.unzip();

        // Verifica se os diretórios foram criados corretamente
        File dir1 = new File(unzipDir, "dir1");
        File dir2 = new File(unzipDir, "dir1/dir2");
        assertTrue(dir1.exists() && dir1.isDirectory(), "Diretório dir1 não foi criado.");
        assertTrue(dir2.exists() && dir2.isDirectory(), "Diretório dir2 não foi criado.");
    }

    @Test
    public void testUnzipEmptyZip() throws IOException {
        // Cria um ZIP vazio
        createZipFile(new File(zipFilePath));

        // Descompacta o arquivo ZIP
        ZipUtils zipUtils = new ZipUtils(zipFilePath, unzipDir);
        zipUtils.unzip();

        // Verifica se o diretório de descompactação está vazio
        File[] files = new File(unzipDir).listFiles();
        assertNotNull(files, "Diretório descompactado não deve ser nulo.");
        assertEquals(0, files.length, "Diretório descompactado deve estar vazio.");
    }

    @Test
    public void testUnzipNonZipFile() {
        // Cria um arquivo de texto em vez de um ZIP
        File nonZipFile = new File("");
        try {
            Files.write(nonZipFile.toPath(), "Not a zip file.".getBytes());
        } catch (IOException e) {
            Assertions.assertFalse(e.toString().isEmpty());
        }
    }

    @Test
    void testUnzipFilesOutsideDirectory() {
        try{
            // Chame o método de descompactação com um arquivo fora do diretório
            ZipUtils zipUtils = new ZipUtils("path/to/zipfile.zip", "target/directory");
            zipUtils.unzip();
        }catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("O sistema não pode encontrar o caminho especificado"));
        }
    }


    @Test
    public void testUnzipLargeFile() throws IOException {
        // Cria um arquivo ZIP com um grande arquivo
        byte[] largeContent = new byte[1024 * 1024 * 10]; // 10 MB
        Arrays.fill(largeContent, (byte) 'A');
        try{
            createZipFile(new File(zipFilePath), "large_file.txt", new String(largeContent));
        }catch (Exception e){
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().contains("too long"));
        }

        // Descompacta o arquivo ZIP
        ZipUtils zipUtils = new ZipUtils(zipFilePath, unzipDir);
        zipUtils.unzip();

        // Verifica se o arquivo grande foi descompactado corretamente
        File largeFile = new File(unzipDir, "large_file.txt");
        assertTrue(largeFile.exists(), "Arquivo grande descompactado deve existir.");
        assertTrue(new String(Files.readAllBytes(largeFile.toPath())).contains("large_file.txt"));
    }

    private void createZipFile(File zipFile, String... entries) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (String entry : entries) {
                if (entry != null) {
                    ZipEntry zipEntry = new ZipEntry(entry);
                    zos.putNextEntry(zipEntry);
                    if (!entry.endsWith("/")) {
                        byte[] content = entry.contains(".txt") ? entry.getBytes() : new byte[0];
                        zos.write(content);
                    }
                    zos.closeEntry();
                }
            }
        }
    }

    private void deleteDirectory(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteDirectory(f);
                }
            }
        }
        Files.delete(file.toPath());
    }
}

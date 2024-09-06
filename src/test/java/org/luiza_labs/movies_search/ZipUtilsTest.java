package org.luiza_labs.movies_search;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZipUtilsTest {

    @TempDir
    File tempDir;

    @Test
    public void testUnzip() throws IOException {
        // 1. Cria um arquivo ZIP de teste
        File zipFile = new File(tempDir, "test.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            // Adiciona arquivos ao ZIP
            addFileToZip(zos, "file1.txt", "Content of file1");
            addFileToZip(zos, "file2.txt", "Content of file2");
            addFileToZip(zos, "dir/file3.txt", "Content of file3 in a directory");
        }

        // 2. Descompacta o arquivo ZIP
        File destDir = new File(tempDir, "extracted");
        ZipUtils.unzip(zipFile.getAbsolutePath(), destDir.getAbsolutePath());

        // 3. Verifica se os arquivos e diretórios foram descompactados corretamente
        File file1 = new File(destDir, "file1.txt");
        File file2 = new File(destDir, "file2.txt");
        File file3 = new File(destDir, "dir/file3.txt");
        assertTrue(file1.exists(), "File1 should exist");
        assertTrue(file2.exists(), "File2 should exist");
        assertTrue(file3.exists(), "File3 should exist");
    }

    private void addFileToZip(ZipOutputStream zos, String fileName, String content) throws IOException {
        ZipEntry entry = new ZipEntry(fileName);
        zos.putNextEntry(entry);
        zos.write(content.getBytes());
        zos.closeEntry();
    }
}

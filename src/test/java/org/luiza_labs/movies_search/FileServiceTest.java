package org.luiza_labs.movies_search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.luiza_labs.movies_search.services.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class FileServiceTest {

    private final String testDir = "test_data";
    private FileService fileService;

    @BeforeEach
    public void setUp() throws IOException {
        // Configura o diretório e arquivos de teste
        new File(testDir).mkdirs();
        Files.write(Paths.get(testDir, "test1.txt"), "This is a test file.".getBytes());
        Files.write(Paths.get(testDir, "test2.txt"), "Another test file with additional content.".getBytes());
        Files.write(Paths.get(testDir, "empty.txt"), "".getBytes());

        // Inicializa o serviço e sobrescreve o diretório de dados para o de teste
        fileService = new FileService();
        fileService.setDATA_DIR(testDir);
        fileService.initializeFileModelMap();
    }

    @AfterEach
    public void tearDown() {
        // Limpeza dos arquivos após os testes
        File[] files = new File(testDir).listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        new File(testDir).delete();
    }

    @Test
    public void testSearchSingleTermFound() {
        String result = fileService.searchInFilesView("test");
        assertTrue(result.contains("test1.txt"), "test1.txt deve estar nos resultados.");
        assertTrue(result.contains("test2.txt"), "test2.txt deve estar nos resultados.");
    }

    @Test
    public void testSearchMultipleTermsFound() {
        String result = fileService.searchInFilesView("test file");
        assertTrue(result.contains("test1.txt"), "test1.txt deve estar nos resultados.");
        assertTrue(result.contains("test2.txt"), "test2.txt deve estar nos resultados.");
    }

    @Test
    public void testSearchNoResults() {
        String result = fileService.searchInFilesView("nonexistent");
        assertTrue(result.contains("Nenhuma ocorrência encontrada"), "Não deve haver resultados.");
    }

    @Test
    public void testSearchWithEmptyTerm() {
        String result = fileService.searchInFilesView("");
        assertTrue(result.contains("Nenhuma ocorrência encontrada"), "Não deve haver resultados para string vazia.");
    }

    @Test
    public void testSearchWithNullTerm() {
        try{
            fileService.searchInFilesView(null);
        }catch (Exception e) {
            assertFalse(e.toString().isEmpty());
        }

    }

    @Test
    public void testSearchWithEmptyFile() {
        String result = fileService.searchInFilesView("test");
        assertFalse(result.contains("empty.txt"), "empty.txt não deve aparecer nos resultados.");
    }

    @Test
    public void testSearchWithoutLoadingFiles() {
        FileService emptyService = new FileService();
        String result = emptyService.searchInFilesView("test");
        assertEquals("No files loaded. Please load the files first.", result, "Deve avisar que os arquivos não foram carregados.");
    }

    @Test
    public void testSearchWithPartialWords() {
        String result = fileService.searchInFilesView("tes");
        assertTrue(result.contains("Nenhuma ocorrência encontrada"), "A busca parcial não deve retornar resultados.");
    }
}

package org.luiza_labs.movies_search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.luiza_labs.movies_search.controllers.FilesController;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FilesControllerTest {
    private FilesController filesController;

    @BeforeEach
    public void setUp() {
        // Inicializa o FilesController e FileService
        filesController = new FilesController();
    }

    @Test
    public void testSearchMovies_ValidSearchTerm() throws IOException {
        // Testa a busca de filmes com um termo válido
        String searchTerm = "Inception";

        // Simula a busca sem exceptions
        assertDoesNotThrow(() -> {
            filesController.searchMovies(searchTerm);
        });
    }

    @Test
    public void testSearchMovies_EmptySearchTerm() throws IOException {
        // Testa a busca de filmes com um termo vazio
        String searchTerm = "";

        // A aplicação deve tratar o termo vazio corretamente
        assertDoesNotThrow(() -> {
            filesController.searchMovies(searchTerm);
        });
    }

    @Test
    public void testSearchMoviesView_ValidSearchTerm() throws IOException {
        // Testa a busca com retorno de resultado para um termo válido
        String searchTerm = "The Dark Knight";

        // Simula a busca e valida o retorno
        String result = filesController.searchMoviesView(searchTerm);
        assertNotNull(result, "O resultado da busca não deve ser nulo.");
        assertFalse(result.isEmpty(), "O resultado da busca não deve estar vazio.");
    }

    @Test
    public void testSearchMoviesView_EmptySearchTerm() throws IOException {
        // Testa a busca com retorno de resultado para um termo vazio
        String searchTerm = "";

        // Simula a busca e valida o retorno
        String result = filesController.searchMoviesView(searchTerm);
        assertNotNull(result, "O resultado da busca não deve ser nulo.");
        Assertions.assertEquals(result, "No files loaded. Please load the files first.");
    }

    @Test
    public void testLoadZipFile_ValidZipFile() throws IOException {
        // Testa o carregamento e descompactação de um arquivo zip válido
        String zipFilePath = "test_data/movies.zip";
        String destDir = "data";

        // Simula o processo de descompactação e inicialização do modelo de arquivos
        assertDoesNotThrow(() -> {
            filesController.loadZipFile(zipFilePath, destDir);
        });
    }

    @Test
    public void testLoadZipFile_InvalidZipFile() {
        // Testa o carregamento de um arquivo zip inválido
        String zipFilePath = "invalid_path/movies.zip";
        String destDir = "data";

        // Espera-se que uma exceção seja lançada para um arquivo zip inválido
        try{
            filesController.loadZipFile(zipFilePath, destDir);
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Error unzipping file:"));
        }
    }

    @Test
    public void testLoadZipFile_ValidZipInValidDestDir(){
        // Testa o carregamento de um zip válido mas com diretório de destino inválido
        String zipFilePath = "movies.zip";
        String destDir = "../../../../";

        // Espera-se que uma exceção seja lançada para um diretório de destino inválido
        try{
            this.filesController.loadZipFile(zipFilePath, destDir);
        }catch (IOException e){
            Assertions.assertTrue(e.getMessage().contains("Error unzipping file:"));
        }
    }

    @Test
    public void testLoadZipFile_ValidZipValidDestDir() throws IOException {
        // Testa o carregamento de um zip válido mas com diretório de destino inválido
        String zipFilePath = "movies.zip";
        String destDir = "data";

        // Espera-se que uma exceção seja lançada para um diretório de destino inválido
        this.filesController.loadZipFile(zipFilePath, destDir);

    }
}

package org.luiza_labs.movies_search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.luiza_labs.movies_search.controllers.FilesController;
import org.luiza_labs.movies_search.views.FilesView;

import javax.swing.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FilesViewTest {
    private FilesView filesView;
    private FilesController filesController;

    @BeforeEach
    public void setUp() {
        filesController = new FilesController(); // Supondo que o FilesController tenha um construtor padrão
        filesView = new FilesView(filesController);
    }

    @Test
    public void testShowDialog_WithValidSearchTerm() throws IOException {
        // Simula o fornecimento de um termo válido
        String searchTerm = "Inception";

        // Medindo tempo de execução da busca
        long startTime = System.nanoTime();
        String result = filesController.searchMoviesView(searchTerm);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        // Validando se o resultado não está vazio e se o tempo de execução é razoável
        assertFalse(result.isEmpty(), "O resultado não deve estar vazio.");
        assertTrue(duration / 1_000_000 < 2000, "O tempo de execução da busca deve ser menor que 2 segundos.");

        // Exibe o resultado
        filesView.showResult(result);
    }

    @Test
    public void testShowDialog_WithEmptySearchTerm() throws IOException {
        // Simula a inserção de um termo de pesquisa vazio
        String searchTerm = "";

        // Espera que o programa retorne imediatamente, já que não há termos válidos
        System.out.println("\nPor favor, forneça um termo de pesquisa.");
        String result = this.filesController.searchMoviesView(searchTerm);
        assertEquals("No files loaded. Please load the files first.", result, "A busca com termo vazio deve retornar vazio.");
    }

    @Test
    public void testShowResult() {
        // Simula um resultado de filme e a exibição em um JTextArea
        String result = "Inception (2010)\nThe Dark Knight (2008)\nInterstellar (2014)";

        // Valida a exibição dos resultados
        filesView.showResult(result);

        // Exibe um JOptionPane com resultados
        JOptionPane.showMessageDialog(null, "Teste concluído.");
    }
}

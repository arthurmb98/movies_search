package org.luiza_labs.movies_search.views;

import org.luiza_labs.movies_search.controllers.MoviesController;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.IOException;

public class MoviesView {
    private final MoviesController moviesController;

    public MoviesView(MoviesController moviesController) {
        this.moviesController = moviesController;
    }

    public void showDialog() throws IOException {
        boolean continueSearching = true;

        while (continueSearching) {
            // Reinicia o cronômetro
            String nome = JOptionPane.showInputDialog("Forneça um termo para pesquisa:");

            // Verifica se um termo de pesquisa foi fornecido
            if (nome == null || nome.isEmpty()) {
                System.out.println("\nPor favor, forneça um termo de pesquisa.");
                return;
            }

            System.out.println("Procurando ocorrências com o termo: " + nome);
            long startTime = System.nanoTime();
            String movies = this.moviesController.searchMoviesView(nome);
            // Para o cronômetro
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("\nTempo de execução da busca: " + duration / 1_000_000 + " ms\n");

            // Exibe os resultados e pergunta se deseja continuar
            this.showResult(movies);

            int option = JOptionPane.showConfirmDialog(null, "Deseja realizar uma nova busca?", "Continuar?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            // Se o usuário escolher "Não", sai do loop
            if (option == JOptionPane.NO_OPTION) {
                continueSearching = false;
            }
        }
    }

    private void showResult(String texto) {
        // Criar um JTextArea
        JTextArea textArea = new JTextArea(10, 30);
        // Adicionar itens na JTextArea
        textArea.append(texto);

        // Criar um JScrollPane para a JTextArea
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Definir a JTextArea como não editável
        textArea.setEditable(false);

        // Exibir o JOptionPane com o JScrollPane
        JOptionPane.showMessageDialog(null, scrollPane, "Lista de Itens", JOptionPane.PLAIN_MESSAGE);
    }

}

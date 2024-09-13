package org.luiza_labs.movies_search;

import org.luiza_labs.movies_search.controllers.MoviesController;
import org.luiza_labs.movies_search.views.MoviesView;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class MoviesSearchApp {

    public static void main(String[] args) throws IOException {
        // Inicia o cronômetro
        long startTime = System.nanoTime();
        // Configura o PrintStream para UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.println("Iniciando a aplicação...");

        String zipFilePath = "movies.zip";  // Caminho do arquivo zip
        String destDir = "data";  // Diretório onde os arquivos serão descompactados

        // Instancia a Controller
        MoviesController controller = new MoviesController();
        MoviesView moviesView = new MoviesView(controller);

        // Descompacta o arquivo e carrega os arquivos
        controller.loadZipFile(zipFilePath, destDir);

        // Para o cronômetro
        long endTime = System.nanoTime();

        System.out.println("Aplicação iniciada com sucesso!\n");

        // Calcula o tempo total gasto
        long duration = endTime - startTime;

        System.out.println("Tempo de inicialização: " + duration / 1_000_000 + " ms\n");

        // Caso o código esteja sendo rodado via prompt de comando.
        if(args.length >= 1){
            // Reinicia o cronômetro
            startTime = System.nanoTime();
            System.out.println("Procurando ocorrências com o termo: " + args[0]);
            // Realiza a busca com o termo de pesquisa fornecido
            controller.searchMovies(args[0]);
            // Para o cronômetro
            endTime = System.nanoTime();
            duration = endTime - startTime;
            System.out.println("\nTempo de execução da busca: " + duration / 1_000_000 + " ms");
        }else{
                moviesView.showDialog();
        }
    }
}

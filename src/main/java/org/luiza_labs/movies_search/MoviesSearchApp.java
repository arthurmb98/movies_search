package org.luiza_labs.movies_search;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class MoviesSearchApp {

    public static void main(String[] args) throws IOException {
        // Configura o PrintStream para UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        // Verifica se um termo de pesquisa foi fornecido
        if (args.length < 1) {
            System.out.println("Por favor, forneça um termo de pesquisa.");
            return;
        }

        // Caminho para o arquivo zip e o diretório de destino
        String zipFilePath = "movies.zip";  // Caminho do arquivo zip
        String destDir = "data";  // Diretório onde os arquivos serão descompactados

        // Descompactar o arquivo ZIP
        ZipUtils.unzip(zipFilePath, destDir);

        // Realizar a busca nos arquivos do diretório 'data'
        FileSearchService fileSearchService = new FileSearchService();
        fileSearchService.searchInFiles(args[0]);  // Apenas o termo de pesquisa
    }
}

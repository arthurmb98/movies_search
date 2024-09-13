package org.luiza_labs.movies_search.controllers;

import org.luiza_labs.movies_search.services.FileService;
import java.io.IOException;


public class MoviesController {
    // Queria usar @Autowired, mas quis evitar dependencia ;)
    private final FileService fileService;

    public MoviesController() {
        this.fileService = new FileService();
    }

    public void searchMovies(String searchTerm) throws IOException {
        // Realiza a busca nos arquivos através do service
        fileService.searchInFiles(searchTerm);
    }

    public String searchMoviesView(String searchTerm) throws IOException {
        // Realiza a busca nos arquivos através do service
        return fileService.searchInFilesView(searchTerm);
    }

    public void loadZipFile(String zipFilePath, String destDir) throws IOException {
        // Chama o utilitário de descompactação (pode ser ZipUtils ou algo similar)
        org.luiza_labs.movies_search.utils.ZipUtils.unzip(zipFilePath, destDir);

        // Carrega os arquivos do diretório descompactado no FileService
        fileService.initializeFileModelMap();
    }
}

package org.luiza_labs.movies_search.controllers;

import org.luiza_labs.movies_search.services.FileService;
import org.luiza_labs.movies_search.utils.ZipUtils;

import java.io.IOException;


public class FilesController {
    // Queria usar @Autowired, mas quis evitar dependencia ;)
    private final FileService fileService;

    public FilesController() {
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
        final ZipUtils zipUtilszipUtils = new ZipUtils(zipFilePath, destDir);
        //Unzip arquivos
        zipUtilszipUtils.unzip();
        // Carrega os arquivos do diretório descompactado no FileService
        fileService.initializeFileModelMap();
    }
}

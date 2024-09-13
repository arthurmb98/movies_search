package org.luiza_labs.movies_search.services;

import org.luiza_labs.movies_search.models.FileModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class FileService {

    private static final String DATA_DIR = "data/data";
    private final Map<Long, FileModel> fileModelMap = new HashMap<>();
    private long idCounter = 1L;  // Controle do auto-incremento para ID dos arquivos

    // Método para inicializar o HashMap de FileModels
    public void initializeFileModelMap() {
        if (fileModelMap.isEmpty()) {
            System.out.println("Iniciando o carregamento dos arquivos para a memória...");
            loadFiles();
            System.out.println("Carregamento concluído. Arquivos prontos para busca.");
        } else {
            System.out.println("Os arquivos já foram carregados na memória.");
        }
    }

    // Método para carregar os arquivos e colocar no HashMap
    private void loadFiles() {
        File dataFolder = new File(DATA_DIR);
        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            System.out.println("Data folder not found. Please unzip the movies.zip file.");
            return;
        }

        File[] files = dataFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    try (Stream<String> lines = Files.lines(Paths.get(file.getPath()))) {
                        StringBuilder fileContent = new StringBuilder();
                        lines.forEach(line -> fileContent.append(" ").append(line));

                        // Criar o FileModel e adicionar ao HashMap
                        FileModel fileModel = new FileModel(idCounter++, file.getName(), fileContent.toString());
                        fileModelMap.put(fileModel.getId(), fileModel);

                    } catch (IOException e) {
                        System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    // Método para buscar os termos nos arquivos carregados
    public void searchInFiles(String searchTerm) {
        if (fileModelMap.isEmpty()) {
            System.out.println("No files loaded. Please load the files first.");
            return;
        }

        String[] searchTerms = searchTerm.toLowerCase().split("\\s+");
        List<FileModel> matchingFiles = new ArrayList<>();
        int occurrenceCount = 0;

        for (FileModel fileModel : fileModelMap.values()) {
            boolean allTermsFound = Arrays.stream(searchTerms)
                    .allMatch(term -> fileModel.getContentFile().toLowerCase().contains(term));

            if (allTermsFound) {
                matchingFiles.add(fileModel);
                occurrenceCount++;
            }
        }

        // Ordenar os arquivos pelo nome
        matchingFiles.sort(Comparator.comparing(FileModel::getNameFile));

        // Imprimir resultados
        System.out.printf("Foram encontradas %d ocorrências pelo termo \"%s\".%n", occurrenceCount, searchTerm);
        if (occurrenceCount > 0) {
            System.out.println("Os arquivos que possuem \"" + searchTerm + "\" são:");
            matchingFiles.forEach(fileModel -> System.out.println(fileModel.getNameFile()));
        }
    }
}

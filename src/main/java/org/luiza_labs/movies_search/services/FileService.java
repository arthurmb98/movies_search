package org.luiza_labs.movies_search.services;

import org.luiza_labs.movies_search.models.FileModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileService {

    private static final String DATA_DIR = "data/data";
    private final Map<Long, FileModel> fileModelMap = new HashMap<>();
    private final Map<String, Set<Long>> invertedIndex = new HashMap<>();  // Índice invertido
    private long idCounter = 1L;  // Controle do auto-incremento para ID dos arquivos

    // Método para inicializar o HashMap de FileModels e construir o índice invertido
    public void initializeFileModelMap() {
        if (fileModelMap.isEmpty()) {
            System.out.println("Iniciando o carregamento dos arquivos para a memória...");
            loadFiles();
            System.out.println("Carregamento concluído. Arquivos prontos para busca.");
        } else {
            System.out.println("Os arquivos já foram carregados na memória.");
        }
    }

    // Método para carregar os arquivos e construir o índice invertido
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
                    try {
                        // Ler a linha única do arquivo
                        String content = new String(Files.readAllBytes(Paths.get(file.getPath()))).toLowerCase();

                        // Criar o FileModel e adicionar ao HashMap
                        FileModel fileModel = new FileModel(idCounter++, file.getName(), content);
                        fileModelMap.put(fileModel.getId(), fileModel);

                        // Construir o índice invertido a partir do conteúdo do arquivo (apenas uma linha)
                        buildInvertedIndex(fileModel);

                    } catch (IOException e) {
                        System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    // Método para construir o índice invertido
    private void buildInvertedIndex(FileModel fileModel) {
        // Dividir o conteúdo do arquivo em palavras
        String[] words = fileModel.getContentFile().split("\\W+");

        // Para cada palavra, adicionar ao índice invertido
        for (String word : words) {
            if (!word.isEmpty()) {
                invertedIndex
                        .computeIfAbsent(word, k -> new HashSet<>())
                        .add(fileModel.getId());
            }
        }
    }

    // Método para buscar termos nos arquivos usando o índice invertido
    public void searchInFiles(String searchTerm) {
        if (fileModelMap.isEmpty()) {
            System.out.println("No files loaded. Please load the files first.");
            return;
        }

        String[] searchTerms = searchTerm.toLowerCase().split("\\s+");
        Set<Long> resultSet;

        // Realizar a busca para o primeiro termo
        if (invertedIndex.containsKey(searchTerms[0])) {
            resultSet = new HashSet<>(invertedIndex.get(searchTerms[0]));
        } else {
            System.out.printf("Nenhuma ocorrência encontrada para o termo \"%s\".%n", searchTerm);
            return;
        }

        // Refinar o resultado para outros termos
        for (int i = 1; i < searchTerms.length; i++) {
            String term = searchTerms[i];
            if (invertedIndex.containsKey(term)) {
                resultSet.retainAll(invertedIndex.get(term));
            } else {
                resultSet.clear();  // Se algum termo não for encontrado, o resultado é vazio
                break;
            }
        }

        // Exibir os arquivos que contêm todos os termos
        if (!resultSet.isEmpty()) {
            System.out.printf("Os arquivos que possuem \"%s\" são:%n", searchTerm);
            resultSet.stream()
                    .map(fileModelMap::get)
                    .sorted(Comparator.comparing(FileModel::getNameFile))
                    .forEach(fileModel -> System.out.println(fileModel.getNameFile()));
        } else {
            System.out.printf("Nenhuma ocorrência encontrada para o termo \"%s\".%n", searchTerm);
        }
    }

    // Método para buscar termos nos arquivos usando o índice invertido para view
    public String searchInFilesView(String searchTerm) {
        if (fileModelMap.isEmpty()) {
            return "No files loaded. Please load the files first.";
        }

        String[] searchTerms = searchTerm.toLowerCase().split("\\s+");
        Set<Long> resultSet;

        // Realizar a busca para o primeiro termo
        if (invertedIndex.containsKey(searchTerms[0])) {
            resultSet = new HashSet<>(invertedIndex.get(searchTerms[0]));
        } else {
            return "Nenhuma ocorrência encontrada para o termo " + searchTerm  + "\n";
        }

        // Refinar o resultado para outros termos
        for (int i = 1; i < searchTerms.length; i++) {
            String term = searchTerms[i];
            if (invertedIndex.containsKey(term)) {
                resultSet.retainAll(invertedIndex.get(term));
            } else {
                resultSet.clear();  // Se algum termo não for encontrado, o resultado é vazio
                break;
            }
        }

        // Exibir os arquivos que contêm todos os termos
        if (!resultSet.isEmpty()) {
            StringBuilder completeResult = new StringBuilder("Os arquivos que possuem \"" + searchTerm + "\" são:\n");

            resultSet.stream()
                    .map(fileModelMap::get)
                    .sorted(Comparator.comparing(FileModel::getNameFile))
                    .forEach(fileModel -> completeResult.append(fileModel.getNameFile()).append("\n"));

            return completeResult.toString();  // Retorna a string final
        } else {
            return "Nenhuma ocorrência encontrada para o termo \"" + searchTerm + "\".\n";
        }

    }
}

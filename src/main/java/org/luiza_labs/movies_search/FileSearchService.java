package org.luiza_labs.movies_search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileSearchService {

    private static final String DATA_DIR = "data/data";

    public void searchInFiles(String searchTerm) {
        File dataFolder = new File(DATA_DIR);
        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            System.out.println("Data folder not found. Please unzip the movies.zip file.");
            return;
        }

        String[] searchTerms = searchTerm.toLowerCase().split("\\s+");
        List<String> matchingFiles = new ArrayList<>();
        int occurrenceCount = 0;

        File[] files = dataFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    try (Stream<String> lines = Files.lines(Paths.get(file.getPath()))) {
                        StringBuilder fileContent = new StringBuilder();
                        lines.forEach(line -> fileContent.append(" ").append(line));
                        boolean allTermsFound = Arrays.stream(searchTerms).allMatch(fileContent.toString().toLowerCase()::contains);

                        if (allTermsFound) {
                            matchingFiles.add(file.getName());
                            occurrenceCount++;
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
                    }
                }
            }
        }

        matchingFiles.sort(String::compareTo);
        System.out.printf("Foram encontradas %d ocorrências pelo termo \"%s\".%n", occurrenceCount, searchTerm);
        if (occurrenceCount > 0) {
            System.out.println("Os arquivos que possuem \"" + searchTerm + "\" são:");
            matchingFiles.forEach(System.out::println);
        }
    }
}

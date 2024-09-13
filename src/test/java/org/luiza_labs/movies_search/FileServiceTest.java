package org.luiza_labs.movies_search;

import org.junit.jupiter.api.Test;
import org.luiza_labs.movies_search.services.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileServiceTest {

    @Test
    public void testSearchInFiles() throws IOException {
        // Setup: Prepare a test data directory with sample files
        String testDir = "test_data";
        new File(testDir).mkdirs();
        File file1 = new File(testDir, "test1.txt");
        File file2 = new File(testDir, "test2.txt");
        Files.write(file1.toPath(), "This is a test file.".getBytes());
        Files.write(file2.toPath(), "Another test file.".getBytes());

        // Initialize the FileSearchService
        FileService service = new FileService();

        // Run the search
        service.searchInFiles("test");

        // Verify the results
        // You would check if the correct files are identified and results are as expected.

        // Cleanup: Remove test data
        file1.delete();
        file2.delete();
        new File(testDir).delete();
    }
}

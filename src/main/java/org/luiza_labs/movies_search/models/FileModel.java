package org.luiza_labs.movies_search.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FileModel {
    private Long id;
    private String nameFile;
    private String contentFile;
}

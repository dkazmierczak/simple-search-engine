package com.exercise.simplesearchengine.core.tokenizer;

import com.exercise.simplesearchengine.core.document.DocumentDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class Tokenizer {

    private static final String REGEX = "\\W+";

    public List<String> tokenize(String str) {
        if (!str.isBlank()) {
            return Arrays.asList(str.split(REGEX));
        }
        throw new IllegalArgumentException("Document is blank!");
    }

    public List<List<String>> tokenizeAlDocuments(List<DocumentDTO> allDocuments) {
        return allDocuments.stream()
                .map(DocumentDTO::getContent)
                .map(this::tokenize)
                .collect(Collectors.toList());
    }
}

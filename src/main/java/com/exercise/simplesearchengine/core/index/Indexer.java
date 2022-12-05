package com.exercise.simplesearchengine.core.index;

import com.exercise.simplesearchengine.core.document.DocumentDTO;
import com.exercise.simplesearchengine.core.tokenizer.Tokenizer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.exercise.simplesearchengine.core.index.IndexDTO.fromDto;

@Service
@AllArgsConstructor
public class Indexer {

    private final Tokenizer tokenizer;
    private final IndexRepository repository;

    public IndexDTO buildIndex(String token, double score, List<DocumentDTO> allDocuments) {
        return IndexDTO.builder()
                .id(token)
                .score(score)
                .matchingDocuments(createMatchingDocuments(token, allDocuments))
                .build();
    }

    private List<DocumentDTO> createMatchingDocuments(String token, List<DocumentDTO> allDocuments) {
        return allDocuments.stream()
                .filter(document -> checkIfTokenMatch(token, document))
                .collect(Collectors.toList());
    }

    public void save(IndexDTO indexDTO) {
        repository.save(fromDto(indexDTO));
    }

    private boolean checkIfTokenMatch(String token, DocumentDTO document) {
        return tokenizer.tokenize(document.getContent()).stream().anyMatch(token::equals);
    }

    public List<IndexDTO> getAllIndexes() {
        return repository.findAll().stream().map(IndexDTO::toDto).collect(Collectors.toList());
    }
}

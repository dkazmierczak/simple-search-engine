package com.exercise.simplesearchengine.core.index;

import com.exercise.simplesearchengine.core.document.DocumentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.exercise.simplesearchengine.core.index.IndexDTO.fromDto;
import static com.exercise.simplesearchengine.core.index.IndexDTO.toDto;

@Service
@AllArgsConstructor
public class Indexer {

    private final IndexRepository repository;

    public IndexDTO buildIndex(String token, double score, List<DocumentDTO> allDocuments) {
        return IndexDTO.builder()
                .id(token)
                .score(score)
                .matchingDocumentIds(createListOfDocumentsContainIds(token, allDocuments))
                .build();
    }

    private List<String> createListOfDocumentsContainIds(String token, List<DocumentDTO> allDocuments) {
        return allDocuments.stream()
                .filter(document -> checkIfTokenMatch(token, document))
                .map(DocumentDTO::getDocId)
                .collect(Collectors.toList());
    }

    public void saveAll(List<IndexDTO> scoredIndexes) {
        scoredIndexes.forEach(this::save);
    }

    public void save(IndexDTO indexDTO) {
        toDto(repository.save(fromDto(indexDTO)));
    }

    private static boolean checkIfTokenMatch(String token, DocumentDTO document) {
        return document.getTokenizedContent().stream().anyMatch(token::equals);
    }
}

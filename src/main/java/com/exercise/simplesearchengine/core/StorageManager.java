package com.exercise.simplesearchengine.core;

import com.exercise.simplesearchengine.core.document.DocumentDTO;
import com.exercise.simplesearchengine.core.document.DocumentService;
import com.exercise.simplesearchengine.core.index.IndexDTO;
import com.exercise.simplesearchengine.core.index.Indexer;
import com.exercise.simplesearchengine.core.scoring.ScoreCalculator;
import com.exercise.simplesearchengine.core.tokenizer.Tokenizer;
import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StorageManager implements SearchEngine {

    private final DocumentService documentService;
    private final Indexer indexer;
    private final Tokenizer tokenizer;
    private final ScoreCalculator calculator;

    private static List<IndexDTO> filterCorrelatedIndex(List<IndexDTO> indexes, String query) {
        return indexes.stream()
                .filter(index -> index.getId().equals(query))
                .collect(Collectors.toList());
    }

    @Override
    public void indexDocument(String id, String document) {
        DocumentDTO saved = id.isBlank()
                ? documentService.save(document)
                : documentService.save(id, document);
        buildAndScoreIndexes(saved).forEach(indexer::save);
    }

    @Override
    public List<IndexEntry> search(String term) {
        List<IndexDTO> indexes = indexer.getAllIndexes();
        return tokenizer.tokenize(term).stream()
                .map(query -> filterCorrelatedIndex(indexes, query))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    List<IndexDTO> buildAndScoreIndexes(DocumentDTO storedDocument) {
        List<DocumentDTO> allDocuments = documentService.getAllDocuments();
        List<List<String>> tokenizedDocuments = tokenizer.tokenizeAlDocuments(allDocuments);
        List<String> tokenizedContent = tokenizer.tokenize(storedDocument.getContent());

        return tokenizedContent.stream()
                .map(token -> indexer.buildIndex(token,
                        calculator.score(tokenizedContent, tokenizedDocuments, token),
                        allDocuments))
                .collect(Collectors.toList());
    }

    public void uploadDocuments(List<String> documents) {
        documents.forEach(doc -> indexDocument("", doc));
    }
}

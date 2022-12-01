package com.exercise.simplesearchengine.core;

import com.exercise.simplesearchengine.core.document.DocumentDTO;
import com.exercise.simplesearchengine.core.document.DocumentService;
import com.exercise.simplesearchengine.core.index.IndexDTO;
import com.exercise.simplesearchengine.core.index.Indexer;
import com.exercise.simplesearchengine.core.scoring.TFIDFCalculator;
import com.exercise.simplesearchengine.core.tokenizer.Tokenizer;
import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StorageManager implements SearchEngine {

    private final DocumentService documentService;
    private final Indexer indexer;
    private final Tokenizer tokenizer;
    private final TFIDFCalculator calculator;

    @Override
    public void indexDocument(String id, String document) {
        DocumentDTO saved = id.isBlank()
                ? documentService.save(document)
                : documentService.save(id, document);
        List<IndexDTO> scoredIndexes = scoreAndStoreIndexes(saved);
        indexer.saveAll(scoredIndexes);
    }

    @Override
    public List<IndexEntry> search(String term) {
        return null;
    }

    private List<IndexDTO> scoreAndStoreIndexes(DocumentDTO storedDocument) {
        List<DocumentDTO> allDocuments = documentService.getAllDocuments();
        List<List<String>> tokenizedDocuments = allDocuments.stream()
                .map(DocumentDTO::getContent)
                .map(tokenizer::tokenizing)
                .collect(Collectors.toList());
        List<String> tokenizedContent = tokenizer.tokenizing(storedDocument.getContent());

        return tokenizedContent.stream()
                .map(token ->
                        indexer.buildIndex(token,
                                calculator.score(tokenizedContent, tokenizedDocuments, token),
                                allDocuments))
                .collect(Collectors.toList());
    }
}

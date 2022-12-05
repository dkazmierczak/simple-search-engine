package com.exercise.simplesearchengine.core.index;

import com.exercise.simplesearchengine.TestData;
import com.exercise.simplesearchengine.core.document.DocumentDTO;
import com.exercise.simplesearchengine.core.tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndexerTest {

    private final TestData testData = new TestData();
    @Mock
    private IndexRepository repository;
    @Mock
    private Tokenizer tokenizer;
    @InjectMocks
    private Indexer indexer;

    @Test
    void should_build_proper_index_empty_list() {
        //given
        String token = "token";
        double score = 0.2d;
        List<DocumentDTO> allDocuments = testData.prepareListOfDocumentDTOs();
        IndexDTO expected = IndexDTO.builder()
                .id(token)
                .score(score)
                .matchingDocuments(Collections.emptyList())
                .build();
        when(tokenizer.tokenize(any())).thenReturn(testData.prepareTokenizedDocument());

        //when
        IndexDTO result = indexer.buildIndex(token, score, allDocuments);

        //then
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getScore(), result.getScore());
        assertTrue(expected.getMatchingDocuments().isEmpty());
    }

    @Test
    void should_build_proper_index_with_documents_match() {
        //given
        String token = "fox";
        double score = 0.2d;
        List<DocumentDTO> allDocuments = testData.prepareListOfDocumentDTOs();
        IndexDTO expected = IndexDTO.builder()
                .id(token)
                .score(score)
                .matchingDocuments(testData.prepareListOfDocumentDTOs())
                .build();
        when(tokenizer.tokenize(any())).thenReturn(testData.prepareTokenizedDocument());

        //when
        IndexDTO result = indexer.buildIndex(token, score, allDocuments);

        //then
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getScore(), result.getScore());
        assertEquals(expected.getMatchingDocuments().size(), result.getMatchingDocuments().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(expected.getMatchingDocuments().get(i), result.getMatchingDocuments().get(i));
        }
    }

    @Test
    void should_return_all_indexes() {
        //given
        List<Index> expected = testData.prepareListOfIndexes();
        when(repository.findAll()).thenReturn(expected);

        //then
        List<IndexDTO> results = indexer.getAllIndexes();

        //then
        assertEquals(expected.get(0).getId(), results.get(0).getId());
        assertEquals(expected.get(0).getScore(), results.get(0).getScore());
        verify(repository, atMostOnce()).findAll();
    }
}

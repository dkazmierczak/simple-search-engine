package com.exercise.simplesearchengine.core.index;

import com.exercise.simplesearchengine.Base;
import com.exercise.simplesearchengine.core.document.DocumentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IndexerTest {

    @Mock
    private IndexRepository repository;

    @InjectMocks
    private Indexer indexer;

    private final Base base = new Base();

    @Test
    void should_build_proper_index_empty_list() {
        //given
        String token = "token";
        double score = 0.2d;
        List<DocumentDTO> allDocuments = prepareListOfAllDocuments();
        IndexDTO expected = IndexDTO.builder()
                .id(token)
                .score(score)
                .matchingDocumentIds(Collections.emptyList())
                .build();

        //when
        IndexDTO result = indexer.buildIndex(token, score, allDocuments);

        //then
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getScore(), result.getScore());
        assertTrue(expected.getMatchingDocumentIds().isEmpty());
    }

    @Test
    void should_build_proper_index_with_documents_match() {
        //given
        String token = "fox";
        double score = 0.2d;
        List<DocumentDTO> allDocuments = prepareListOfAllDocuments();
        IndexDTO expected = IndexDTO.builder()
                .id(token)
                .score(score)
                .matchingDocumentIds(List.of("id1", "id2", "id3", "id4"))
                .build();

        //when
        IndexDTO result = indexer.buildIndex(token, score, allDocuments);

        //then
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getScore(), result.getScore());
        assertEquals(expected.getMatchingDocumentIds().size(), result.getMatchingDocumentIds().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(expected.getMatchingDocumentIds().get(i), result.getMatchingDocumentIds().get(i));
        }
    }

    @Test
    void should_call_save_3_times() {
        //given
        List<IndexDTO> indexes = prepareListOfIndexes();

        //when
        indexer.saveAll(indexes);

        //then
        verify(repository, times(3)).save(any());
    }

    private List<IndexDTO> prepareListOfIndexes() {
        return List.of(
                IndexDTO.builder()
                        .id("fox")
                        .score(0.2d)
                        .matchingDocumentIds(List.of("doc1", "doc2"))
                        .build(),
                IndexDTO.builder()
                        .id("brown")
                        .score(0.25d)
                        .matchingDocumentIds(List.of("doc2", "doc3"))
                        .build(),
                IndexDTO.builder()
                        .id("jumped")
                        .score(0.15d)
                        .matchingDocumentIds(List.of("doc3", "doc4"))
                        .build()
        );
    }

    private List<DocumentDTO> prepareListOfAllDocuments() {
        return List.of(
                DocumentDTO.builder()
                        .docId("id1")
                        .tokenizedContent(base.createTokenizedDocument())
                        .build(),
                DocumentDTO.builder()
                        .docId("id2")
                        .tokenizedContent(base.createTokenizedDocument())
                        .build(),
                DocumentDTO.builder()
                        .docId("id3")
                        .tokenizedContent(base.createTokenizedDocument())
                        .build(),
                DocumentDTO.builder()
                        .docId("id4")
                        .tokenizedContent(base.createTokenizedDocument())
                        .build()
        );
    }
}
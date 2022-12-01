package com.exercise.simplesearchengine.core.index;

import com.exercise.simplesearchengine.Base;
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

    @Mock
    private IndexRepository repository;

    @Mock
    private Tokenizer tokenizer;

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

        when(tokenizer.tokenizing(any())).thenReturn(base.createTokenizedDocument());

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
                .matchingDocumentIds(prepareListOfDocuments())
                .build();
        when(tokenizer.tokenizing(any())).thenReturn(base.createTokenizedDocument());

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

    private List<DocumentDTO> prepareListOfDocuments() {
        return List.of(
                DocumentDTO.builder()
                        .docId(1L)
                        .content("the brown fox jumped over the brown dog")
                        .build(),
                DocumentDTO.builder()
                        .docId(2L)
                        .content("the brown fox jumped over the brown dog")
                        .build(),
                DocumentDTO.builder()
                        .docId(3L)
                        .content("the brown fox jumped over the brown dog")
                        .build(),
                DocumentDTO.builder()
                        .docId(4L)
                        .content("the brown fox jumped over the brown dog")
                        .build()
        );
    }

    private List<IndexDTO> prepareListOfIndexes() {
        return List.of(
                IndexDTO.builder()
                        .id("fox")
                        .score(0.2d)
                        .matchingDocumentIds(prepareListOfDocuments())
                        .build(),
                IndexDTO.builder()
                        .id("brown")
                        .score(0.25d)
                        .matchingDocumentIds(prepareListOfDocuments())
                        .build(),
                IndexDTO.builder()
                        .id("jumped")
                        .score(0.15d)
                        .matchingDocumentIds(prepareListOfDocuments())
                        .build()
        );
    }

    private List<DocumentDTO> prepareListOfAllDocuments() {
        return List.of(
                DocumentDTO.builder()
                        .docId(1L)
                        .content(base.prepareDocumentContent())
                        .build(),
                DocumentDTO.builder()
                        .docId(2L)
                        .content(base.prepareDocumentContent())
                        .build(),
                DocumentDTO.builder()
                        .docId(3L)
                        .content(base.prepareDocumentContent())
                        .build(),
                DocumentDTO.builder()
                        .docId(4L)
                        .content(base.prepareDocumentContent())
                        .build()
        );
    }
}
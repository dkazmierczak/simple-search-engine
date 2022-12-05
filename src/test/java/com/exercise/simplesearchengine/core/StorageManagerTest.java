package com.exercise.simplesearchengine.core;

import com.exercise.simplesearchengine.TestData;
import com.exercise.simplesearchengine.core.document.DocumentDTO;
import com.exercise.simplesearchengine.core.document.DocumentService;
import com.exercise.simplesearchengine.core.index.IndexDTO;
import com.exercise.simplesearchengine.core.index.Indexer;
import com.exercise.simplesearchengine.core.scoring.ScoreCalculator;
import com.exercise.simplesearchengine.core.tokenizer.Tokenizer;
import com.findwise.IndexEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageManagerTest {

    private final TestData testData = new TestData();
    @Mock
    private DocumentService documentService;
    @Mock
    private Indexer indexer;
    @Mock
    private Tokenizer tokenizer;
    @Mock
    private ScoreCalculator calculator;
    @InjectMocks
    private StorageManager manager;
    private DocumentDTO documentDTO;

    public static Stream<Arguments> parametrizeSearchTest() {
        return Stream.of(
                Arguments.of("brown dog", List.of("brown", "dog"), 2, "brown"),
                Arguments.of("fox blue", List.of("fox", "blue"), 1, "fox"),
                Arguments.of("", List.of(""), 0, ""),
                Arguments.of("the sit", List.of("the", "sit"), 1, "the")
        );
    }

    @BeforeEach
    void setup() {
        documentDTO = DocumentDTO.builder()
                .docId(1L)
                .content(testData.prepareDocumentContent())
                .build();
    }

    @Test
    void should_call_save_3_times() {
        //given
        String token = "token";
        double score = 0.2d;
        List<String> documents = testData.prepareDocumentsToUpload();
        when(documentService.getAllDocuments()).thenReturn(testData.prepareListOfDocumentDTOs());
        when(documentService.save(any())).thenReturn(documentDTO);
        when(tokenizer.tokenize(any())).thenReturn(List.of(token));
        when(indexer.buildIndex(token, score, testData.prepareListOfDocumentDTOs()))
                .thenReturn(IndexDTO.builder().build());
        when(calculator.score(any(), any(), any())).thenReturn(score);

        //when
        manager.uploadDocuments(documents);

        //then
        verify(documentService, times(3)).save(any());
    }

    @Test
    void should_call_save_when_given_id() {
        //given
        String id = "1";
        String document = testData.prepareDocumentContent();
        DocumentDTO dto = DocumentDTO.builder().docId(1L).content(document).build();
        when(documentService.getAllDocuments()).thenReturn(testData.prepareListOfDocumentDTOs());
        when(documentService.save(id, document)).thenReturn(dto);

        //when
        manager.indexDocument(id, document);

        //then
        verify(documentService, atMostOnce()).save(any(), any());
    }

    @Test
    void should_properly_build_and_score_indexes() {
        //given
        String token = "the";
        List<String> tokenized = testData.prepareTokenizedDocument();
        double score = 0.0d;
        List<DocumentDTO> allDocuments = testData.prepareListOfDocumentDTOs();
        IndexDTO dto = IndexDTO.builder()
                .id(token)
                .score(score)
                .matchingDocuments(allDocuments)
                .build();
        int expectedSize = 8;
        when(documentService.getAllDocuments()).thenReturn(testData.prepareListOfDocumentDTOs());
        when(tokenizer.tokenize(documentDTO.getContent())).thenReturn(tokenized);
        when(tokenizer.tokenizeAlDocuments(allDocuments)).thenReturn(testData.prepareListOfAllTokenizedDocuments());
        when(indexer.buildIndex(token, score, allDocuments)).thenReturn(dto);

        //when
        List<IndexDTO> results = manager.buildAndScoreIndexes(documentDTO);

        //then
        assertEquals(expectedSize, results.size());
        assertEquals(token, results.get(0).getId());
        assertEquals(score, results.get(0).getScore());
        assertEquals(allDocuments, results.get(0).getMatchingDocuments());
    }

    @ParameterizedTest
    @MethodSource("parametrizeSearchTest")
    void should_find_relevant_indexes(String term, List<String> tokenized, int size, String firstTerm) {
        //given
        when(tokenizer.tokenize(term)).thenReturn(tokenized);
        when(indexer.getAllIndexes()).thenReturn(testData.prepareListOfIndexDTOs());

        //when
        List<IndexEntry> results = manager.search(term);

        //then
        assertEquals(size, results.size());
        if (!firstTerm.isBlank()) {
            assertEquals(firstTerm, results.get(0).getId());
        }
    }
}

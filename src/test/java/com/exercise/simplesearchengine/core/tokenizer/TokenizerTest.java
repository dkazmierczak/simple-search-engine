package com.exercise.simplesearchengine.core.tokenizer;

import com.exercise.simplesearchengine.TestData;
import com.exercise.simplesearchengine.core.document.DocumentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TokenizerTest {

    private final TestData testData = new TestData();
    @InjectMocks
    private Tokenizer tokenizer;

    @Test
    void should_tokenize_document_correctly() {
        //given
        String document = testData.prepareDocumentContent();
        List<String> expectedResults = testData.prepareTokenizedDocument();

        //when
        List<String> results = tokenizer.tokenize(document);

        //then
        assertEquals(expectedResults, results);
    }

    @Test
    void should_throw_exception() {
        //given
        String document = "";
        String expectedMessage = "Document is blank!";

        //when
        Executable function = () -> tokenizer.tokenize(document);

        //then
        Throwable exception = assertThrows(IllegalArgumentException.class, function);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_correctly_tokenize_all_documents() {
        //give
        List<DocumentDTO> allDocuments = testData.prepareListOfDocumentDTOs();
        int expectedSize = 4;
        int expectedSizeOdInsideList = 8;

        //when
        List<List<String>> tokenizedDocuments = tokenizer.tokenizeAlDocuments(allDocuments);

        //then
        assertEquals(expectedSize, tokenizedDocuments.size());
        assertEquals(expectedSizeOdInsideList, tokenizedDocuments.get(0).size());
    }
}
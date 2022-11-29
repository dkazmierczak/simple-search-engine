package com.exercise.simplesearchengine.core.tokenizer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TokenizerTest {

    @InjectMocks
    private Tokenizer tokenizer;

    @Test
    void should_tokenize_document_correctly() {
        //given
        String document = "the brown fox jumped over the brown dog";
        List<String> expectedResults =
                List.of("the", "brown", "fox", "jumped", "over", "the", "brown", "dog");

        //when
        List<String> results = tokenizer.tokenizing(document);

        //then
        assertEquals(expectedResults, results);
    }

    @Test
    void should_throw_exception() {
        //given
        String document = "";
        String expectedMessage ="Document is blank!";

        //when
        Throwable exception =
                assertThrows(IllegalArgumentException.class, () -> tokenizer.tokenizing(document));

        //then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
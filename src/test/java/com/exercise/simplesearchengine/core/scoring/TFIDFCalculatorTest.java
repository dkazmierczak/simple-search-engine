package com.exercise.simplesearchengine.core.scoring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TFIDFCalculatorTest {

    @InjectMocks
    private TFIDFCalculator calculator;

    @Test
    void should_correctly_count_score() {
        //given
        List<String> doc = List.of("the", "brown", "fox", "jumped", "over", "the", "brown", "dog");
        List<List<String>> docs = prepareTokenizedDocs();
        String term = "brown";
        double expected = 0.1013662770270411d;

        //when
        double result = calculator.score(doc, docs, term);

        //then
        assertEquals(expected, result);
    }

    @Test
    void should_correctly_count_0_score() {
        List<String> doc = List.of("the", "brown", "fox", "jumped", "over", "the", "brown", "dog");
        List<List<String>> docs = prepareTokenizedDocs();
        String term = "lamp";
        double expected = 0.1013662770270411d;
        assertThrows(RuntimeException.class, () -> calculator.score(doc, docs, term));
    }

    private List<List<String>> prepareTokenizedDocs() {
        return List.of(List.of("the", "brown", "fox", "jumped", "over", "the", "brown", "dog"),
                List.of("the", "lazy", "brown", "dog", "sat", "in", "the", "corner"),
                List.of("the", "red", "fox", "bit", "the", "lazy", "dog"));
    }
}
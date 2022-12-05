package com.exercise.simplesearchengine.core.scoring;

import com.exercise.simplesearchengine.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ScoreCalculatorTest {

    private final TestData testData = new TestData();
    @InjectMocks
    private ScoreCalculator calculator;
    private List<String> doc;
    private List<List<String>> docs;

    @BeforeEach
    void setup() {
        doc = testData.prepareTokenizedDocument();
        docs = testData.prepareListOfAllTokenizedDocuments();
    }

    @Test
    void should_correctly_count_score() {
        //given

        String term = "brown";
        double expected = 0.1013662770270411d;

        //when
        double result = calculator.score(doc, docs, term);

        //then
        assertEquals(expected, result);
    }

    @Test
    void should_thrown_exception_when_term_not_present() {
        //given
        String term = "lamp";

        //when
        Executable function = () -> calculator.score(doc, docs, term);

        //then
        assertThrows(RuntimeException.class, function);
    }
}
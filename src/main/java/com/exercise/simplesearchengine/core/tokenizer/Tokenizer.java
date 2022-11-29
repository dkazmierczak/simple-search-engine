package com.exercise.simplesearchengine.core.tokenizer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class Tokenizer {

    private static final String REGEX = "\\W+";

    public List<String> tokenizing(String str) {
        if (!str.isBlank()) {
            return Arrays.asList(str.split(REGEX));
        }
        throw new IllegalArgumentException("Document is blank!");
    }
}

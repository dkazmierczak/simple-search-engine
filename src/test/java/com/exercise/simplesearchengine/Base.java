package com.exercise.simplesearchengine;

import java.util.List;

public class Base {

    public List<String> createTokenizedDocument() {
        return List.of("the", "brown", "fox", "jumped", "over", "the", "brown", "dog");
    }

    public String prepareDocumentContent() {
        return "the brown fox jumped over the brown dog";
    }
}

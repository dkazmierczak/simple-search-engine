package com.exercise.simplesearchengine;

import com.exercise.simplesearchengine.core.document.Document;
import com.exercise.simplesearchengine.core.document.DocumentDTO;
import com.exercise.simplesearchengine.core.index.Index;
import com.exercise.simplesearchengine.core.index.IndexDTO;

import java.util.List;

public class TestData {

    public List<String> prepareTokenizedDocument() {
        return List.of("the", "brown", "fox", "jumped", "over", "the", "brown", "dog");
    }

    public String prepareDocumentContent() {
        return "the brown fox jumped over the brown dog";
    }

    public List<DocumentDTO> prepareListOfDocumentDTOs() {
        return List.of(
                DocumentDTO.builder()
                        .docId(1L)
                        .content(prepareDocumentContent())
                        .build(),
                DocumentDTO.builder()
                        .docId(2L)
                        .content(prepareDocumentContent())
                        .build(),
                DocumentDTO.builder()
                        .docId(3L)
                        .content(prepareDocumentContent())
                        .build(),
                DocumentDTO.builder()
                        .docId(4L)
                        .content(prepareDocumentContent())
                        .build()
        );
    }

    public List<Index> prepareListOfIndexes() {
        Index index1 = new Index();
        index1.setId("fox");
        index1.setScore(0.2d);
        index1.setMatchingDocuments(prepareListOfDocuments());
        Index index2 = new Index();
        index2.setId("brown");
        index2.setScore(0.15d);
        index2.setMatchingDocuments(prepareListOfDocuments());
        return List.of(index1, index2);
    }

    public List<IndexDTO> prepareListOfIndexDTOs() {
        IndexDTO dto1 = IndexDTO.builder()
                .id("fox")
                .score(0.2d)
                .matchingDocuments(prepareListOfDocumentDTOs())
                .build();
        IndexDTO dto2 = IndexDTO.builder()
                .id("brown")
                .score(0.15d)
                .matchingDocuments(prepareListOfDocumentDTOs())
                .build();
        IndexDTO dto3 = IndexDTO.builder()
                .id("the")
                .score(0.0d)
                .matchingDocuments(prepareListOfDocumentDTOs())
                .build();
        IndexDTO dto4 = IndexDTO.builder()
                .id("dog")
                .score(0.1d)
                .matchingDocuments(prepareListOfDocumentDTOs())
                .build();
        return List.of(dto1, dto2, dto3, dto4);
    }

    public List<Document> prepareListOfDocuments() {
        Document document1 = new Document();
        document1.setDocId(1L);
        document1.setContent("the brown fox jumped over the brown dog");
        Document document2 = new Document();
        document2.setDocId(2L);
        document2.setContent("the brown fox jumped over the brown dog");
        Document document3 = new Document();
        document3.setDocId(3L);
        document3.setContent("the brown fox jumped over the brown dog");
        Document document4 = new Document();
        document4.setDocId(4L);
        document4.setContent("the brown fox jumped over the brown dog");
        return List.of(document1, document2, document3, document4);
    }

    public List<String> prepareDocumentsToUpload() {
        return List.of(
                "the brown fox jumped over the brown dog",
                "the lazy brown dog sat in the corner",
                "the red fox bit the lazy dog"
        );
    }

    public List<List<String>> prepareListOfAllTokenizedDocuments() {
        return List.of(
                List.of("the", "brown", "fox", "jumped", "over", "the", "brown", "dog"),
                List.of("the", "lazy", "brown", "dog", "sat", "in", "the", "corner"),
                List.of("the", "red", "fox", "bit", "the", "lazy", "dog"));
    }
}

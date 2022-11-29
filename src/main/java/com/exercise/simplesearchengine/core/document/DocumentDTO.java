package com.exercise.simplesearchengine.core.document;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DocumentDTO {

    private String docId;
    private List<String> tokenizedContent;

    public static DocumentDTO toDto(Document document) {
        return DocumentDTO.builder()
                .docId(document.getDocId())
                .tokenizedContent(document.getTokenizedContent())
                .build();
    }

    public static Document fromDto(DocumentDTO dto) {
        return Document.builder()
                .docId(dto.getDocId())
                .tokenizedContent(dto.getTokenizedContent())
                .build();
    }
}

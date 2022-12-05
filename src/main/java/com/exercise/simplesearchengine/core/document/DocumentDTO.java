package com.exercise.simplesearchengine.core.document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentDTO {

    private Long docId;
    private String content;

    public static DocumentDTO toDto(Document document) {
        return DocumentDTO.builder()
                .docId(document.getDocId())
                .content(document.getContent())
                .build();
    }

    public static Document fromDto(DocumentDTO dto) {
        Document doc = new Document();
        doc.setDocId(dto.getDocId());
        doc.setContent(dto.getContent());
        return doc;
    }
}

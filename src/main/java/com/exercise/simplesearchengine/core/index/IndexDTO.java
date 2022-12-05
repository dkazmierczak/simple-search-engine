package com.exercise.simplesearchengine.core.index;

import com.exercise.simplesearchengine.core.document.Document;
import com.exercise.simplesearchengine.core.document.DocumentDTO;
import com.findwise.IndexEntry;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class IndexDTO implements IndexEntry {

    private String id;
    private double score;
    private List<DocumentDTO> matchingDocuments;

    public static IndexDTO toDto(Index index) {
        return IndexDTO.builder()
                .id(index.getId())
                .score(index.getScore())
                .matchingDocuments(prepareDTOList(index.getMatchingDocuments()))
                .build();
    }

    private static List<DocumentDTO> prepareDTOList(List<Document> matchingDocumentIds) {
        return matchingDocumentIds.stream().map(DocumentDTO::toDto).collect(Collectors.toList());
    }

    public static Index fromDto(IndexDTO dto) {
        Index index = new Index();
        index.setId(dto.getId());
        index.setScore(dto.getScore());
        index.setMatchingDocuments(prepareDocList(dto.getMatchingDocuments()));
        return index;
    }

    private static List<Document> prepareDocList(List<DocumentDTO> matchingDocumentIds) {
        return matchingDocumentIds.stream().map(DocumentDTO::fromDto).collect(Collectors.toList());
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public double getScore() {
        return this.score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }
}

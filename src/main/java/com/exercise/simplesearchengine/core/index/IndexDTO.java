package com.exercise.simplesearchengine.core.index;

import com.findwise.IndexEntry;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IndexDTO implements IndexEntry {

    private String id;
    private double score;
    private List<String> matchingDocumentIds;

    public static IndexDTO toDto(Index index) {
        return IndexDTO.builder()
                .id(index.getId())
                .score(index.getScore())
                .matchingDocumentIds(index.getMatchingDocumentIds())
                .build();
    }

    public static Index fromDto(IndexDTO dto) {
        return Index.builder()
                .id(dto.getId())
                .score(dto.getScore())
                .matchingDocumentIds(dto.getMatchingDocumentIds())
                .build();
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

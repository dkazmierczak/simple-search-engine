package com.exercise.simplesearchengine.core.index;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table
@Data
@Builder
public class Index {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private double score;
    @JoinColumn
    private List<String> matchingDocumentIds;
}

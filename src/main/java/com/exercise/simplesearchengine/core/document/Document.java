package com.exercise.simplesearchengine.core.document;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "Document")
@Data
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String docId;
    @Column
    private List<String> tokenizedContent;
}

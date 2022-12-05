package com.exercise.simplesearchengine.core.index;

import com.exercise.simplesearchengine.core.document.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Index {

    @Id
    private String id;

    @Column
    private double score;

    @ManyToMany
    @JoinTable(
            name = "INDEX_DOCUMENT",
            joinColumns = {@JoinColumn(name = "INDEX_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DOCUMENT_ID")}
    )
    private List<Document> matchingDocuments;
}

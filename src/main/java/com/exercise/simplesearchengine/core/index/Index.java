package com.exercise.simplesearchengine.core.index;

import com.exercise.simplesearchengine.core.document.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Index {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private double score;

    @OneToMany(mappedBy = "index")
    private List<Document> matchingDocumentIds;
}

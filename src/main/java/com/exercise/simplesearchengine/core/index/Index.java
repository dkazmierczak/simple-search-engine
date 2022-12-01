package com.exercise.simplesearchengine.core.index;

import com.exercise.simplesearchengine.core.document.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Index {

    @Id
    private String id;

    @Column
    private double score;

    @OneToMany(mappedBy = "index")
    private List<Document> matchingDocumentIds;
}

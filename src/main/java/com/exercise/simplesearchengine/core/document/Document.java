package com.exercise.simplesearchengine.core.document;


import com.exercise.simplesearchengine.core.index.Index;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;

    @Column
    private String content;

    @ManyToMany(mappedBy = "matchingDocuments")
    @JsonIgnore
    private List<Index> index;

}

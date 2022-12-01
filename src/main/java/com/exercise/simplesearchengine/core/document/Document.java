package com.exercise.simplesearchengine.core.document;


import com.exercise.simplesearchengine.core.index.Index;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "fk_doc")
    private Index index;

}

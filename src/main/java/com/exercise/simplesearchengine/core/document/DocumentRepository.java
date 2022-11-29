package com.exercise.simplesearchengine.core.document;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends CrudRepository<Document, String> {

    List<Document> findAll();

}

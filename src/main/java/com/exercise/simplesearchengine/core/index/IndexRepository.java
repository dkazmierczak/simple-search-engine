package com.exercise.simplesearchengine.core.index;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexRepository extends CrudRepository<Index, String> {

    List<Index> findAll();
}

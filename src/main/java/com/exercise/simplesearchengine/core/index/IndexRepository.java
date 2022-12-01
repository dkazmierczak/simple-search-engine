package com.exercise.simplesearchengine.core.index;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexRepository extends JpaRepository<Index, String> {

    List<Index> findAll();
}

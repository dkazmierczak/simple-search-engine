package com.exercise.simplesearchengine.core;

import com.findwise.IndexEntry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class StorageController {

    private final StorageManager manager;

    @PostMapping("/document")
    public void uploadDocument(@RequestBody List<String> documents) {
        manager.uploadDocuments(documents);
    }

    @GetMapping("/document")
    public ResponseEntity<List<IndexEntry>> search(@RequestParam String term) {
        return ResponseEntity.status(HttpStatus.OK).body(manager.search(term));
    }
}

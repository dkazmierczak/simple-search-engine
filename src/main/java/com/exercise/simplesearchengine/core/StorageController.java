package com.exercise.simplesearchengine.core;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class StorageController {

    private final StorageManager manager;

    @PostMapping("/file/upload")
    public void uploadDocument(@RequestParam String document) {
        manager.indexDocument("", document);
    }
}

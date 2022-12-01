package com.exercise.simplesearchengine.core.document;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.exercise.simplesearchengine.core.document.DocumentDTO.fromDto;
import static com.exercise.simplesearchengine.core.document.DocumentDTO.toDto;

@Service
@AllArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public List<DocumentDTO> getAllDocuments() {
        return documentRepository.findAll().stream()
                .map(DocumentDTO::toDto)
                .collect(Collectors.toList());
    }

    public DocumentDTO save(String content) {
        return save(null, content);
    }

    public DocumentDTO save(String id, String content) {
        Document document = new Document();
        if (id != null) {
            document.setDocId(id);
        }
        document.setContent(content);
        return toDto(documentRepository.save(document));
    }
}

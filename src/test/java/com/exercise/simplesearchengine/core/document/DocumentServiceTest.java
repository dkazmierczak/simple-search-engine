package com.exercise.simplesearchengine.core.document;

import com.exercise.simplesearchengine.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    private final TestData testData = new TestData();
    @Mock
    private DocumentRepository repository;
    @InjectMocks
    private DocumentService service;

    @Test
    void should_call_db_once() {
        //given
        List<Document> expected = testData.prepareListOfDocuments();
        when(repository.findAll()).thenReturn(expected);

        //when
        List<DocumentDTO> results = service.getAllDocuments();

        //then
        assertEquals(expected.get(0).getDocId(), results.get(0).getDocId());
        assertEquals(expected.get(0).getContent(), results.get(0).getContent());
        assertEquals(DocumentDTO.class, results.get(0).getClass());
        verify(repository, atMostOnce()).findAll();
    }

    @Test
    void should_call_repository_save() {
        //given
        String content = testData.prepareDocumentContent();
        when(repository.save(any())).thenReturn(new Document());

        //when
        service.save(content);

        //then
        verify(repository, atMostOnce()).save(any());
    }
}

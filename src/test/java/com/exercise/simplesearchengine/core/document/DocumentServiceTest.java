package com.exercise.simplesearchengine.core.document;

import com.exercise.simplesearchengine.Base;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository repository;
    @InjectMocks
    private DocumentService service;
    private final Base base = new Base();

    @Test
    void should_call_db_once() {
        service.getAllDocuments();
        verify(repository, atMostOnce()).findAll();
    }

    @Test
    void should_call_repository_save() {
        //given
        String content = base.prepareDocumentContent();
        when(repository.save(any())).thenReturn(new Document());

        //when
        service.save(content);

        //then
        verify(repository, atMostOnce()).save(any());
    }
}

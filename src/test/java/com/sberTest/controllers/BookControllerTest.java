package com.sberTest.controllers;

import com.sberTest.dto.dtoRepository;
import com.sberTest.services.BookService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

@RequiredArgsConstructor
class BookControllerTest {
    private final BookService bookService;
    private final BookController bookController;
    List<dtoRepository> allBooksR;

    @Test
    void getBookControllerTest() {

    }
}
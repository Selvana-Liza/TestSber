package com.sberTest.controllers;

import com.sberTest.dto.BookDto;
import com.sberTest.services.SimpleBookService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequiredArgsConstructor
class BookControllerTest {
    @Autowired
    private final SimpleBookService bookService;
    @Autowired
    private final BookController bookController;

    List<BookDto> allBooksR;

    @Test
    void getBookControllerTest() {

    }
}
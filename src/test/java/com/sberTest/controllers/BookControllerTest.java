package com.sberTest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sberTest.dto.BookDto;
import com.sberTest.dto.ResponseDto;
import com.sberTest.models.Book;
import com.sberTest.repositories.BookRepository;
import com.sberTest.services.SimpleBookService;
import com.sberTest.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Access;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class BookControllerTest {
    private SimpleBookService bookService;
    @Autowired
    private BookRepository bookRepository;
    private MappingUtils mappingUtils;
    private BookController bookController;

    private List<BookDto> bookDtoList;
    @BeforeEach
    void setUp() throws IOException {
        bookService = new SimpleBookService(bookRepository, mappingUtils);
        bookController = new BookController(bookService);

        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Book.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);
        List<Book> bookList = objectMapper.readValue(reader, listType);
        bookDtoList = bookService.mappingToBookDTO();
    }
    @Test
    void getBookControllerTest() {
        List<ResponseDto> responseDtoList = bookController.getBook(bookDtoList);

        assertNotNull(responseDtoList.get(0), "responseDtoList is null");
    }
}
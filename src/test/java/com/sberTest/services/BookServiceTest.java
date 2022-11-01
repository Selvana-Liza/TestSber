package com.sberTest.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sberTest.controllers.BookController;
import com.sberTest.dto.BookDto;
import com.sberTest.dto.ResponseDto;
import com.sberTest.models.Book;
import com.sberTest.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//как подключить ApplicationContext???
@RequiredArgsConstructor
class BookServiceTest {
    @Autowired
    private final SimpleBookService bookService;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final BookController bookController;
    List<Book> bookList;
    List<BookDto> bookDtoList;


    void setUp() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Book.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);
        bookList = objectMapper.readValue(reader, listType);
    }

    @Test
    void givenJsonTest() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Book.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);
        List<Book> bookList = objectMapper.readValue(reader, listType);

        assertEquals(3, bookList.size(), "BookList count is not matching");
        assertEquals(Book.class, bookList.get(0).getClass(), "Class objects in bookList is not matching");
    }

    @Test
    void mappingToBookDTOTest() {
        bookDtoList = bookService.mappingToBookDTO();

        assertEquals(bookList.size(), bookDtoList.size(), "bookDtoList count is not matching");
        bookDtoList.forEach(bookDto -> bookList.forEach(book -> {
                    if (book.getIdBook() == bookDto.getIdBook()) {
                        assertEquals(book.getNameBook(), bookDto.getNameBook(),"Name book is not matching");
                        assertEquals(book.getAuthorBook(), bookDto.getAuthorBook(), "Author book is not matching");
                    }
                }
        ));
    }

    @Test
    void mappingToResponseDTOTest() {
        List<ResponseDto> responseDtoList = bookController.getBook(bookDtoList);

        assertEquals(bookDtoList.size(), responseDtoList.size(), "responseDtoList count is not matching");
        responseDtoList.forEach(responseDto -> bookDtoList.forEach(bookDto -> {
                    if (bookDto.getIdBook() == responseDto.getIdBook()) {
                        assertEquals(bookDto.getNameBook(), responseDto.getNameBook(),"Name book is not matching");
                        assertEquals(bookDto.getAuthorBook(), responseDto.getAuthorBook(), "Author book is not matching");
                    }
                }
        ));
    }

    @Test
    void findAllTest() {
        List<Book> all = bookRepository.findAll();

        assertEquals(3, all.size(), "Count books is not matching");
    }

    @Test
    void saveTest() {
        Book book = new Book();
        book.setIdBook(0);
        book.setNameBook("Book0");
        book.setAuthorBook("Author0");
        bookService.save(book);

        Optional<Book> bookExpected = bookService.findById(0);

        assertNotNull(bookExpected.get().getNameBook(), "BookExpected is null");
        assertEquals(book.getNameBook(), bookExpected.get().getNameBook(), "Name book is not matching");
    }

    @Test
    void saveAllTest() {
        List<Book> books = new ArrayList<>(
                Arrays.asList(
                        new Book()
                                .setIdBook(777)
                                .setNameBook("Book777")
                                .setAuthorBook("Author777"),
                        new Book()
                                .setIdBook(888)
                                .setNameBook("Book888")
                                .setAuthorBook("Author888"),
                        new Book()
                                .setIdBook(999)
                                .setNameBook("Book999")
                                .setAuthorBook("Author999")

                )
        );

        List<Book> expectedBooks = bookService.findAll();

        assertEquals(3, expectedBooks.size(), "Count books is not matching");

        expectedBooks.forEach(book -> {
            if (777 == book.getIdBook()) {
                assertEquals("Book777", book.getNameBook());
                assertEquals("Author777", book.getAuthorBook());
            } else if (888 == book.getIdBook()) {
                assertEquals("Book888", book.getNameBook());
                assertEquals("Author888", book.getAuthorBook());
            }
            else if (999 == book.getIdBook()) {
                assertEquals("Book999", book.getNameBook());
                assertEquals("Author999", book.getAuthorBook());
            }
        });
    }

    @Test
    void getPlaceTest() {
    }

    @Test
    void getCountTest() {
    }
}
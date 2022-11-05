package com.sberTest.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sberTest.dto.BookDto;
import com.sberTest.dto.ResponseDto;
import com.sberTest.models.Book;
import com.sberTest.repositories.BookRepository;
import com.sberTest.utils.MappingUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class BookServiceTest {
    private SimpleBookService bookService;
    @Autowired
    private BookRepository bookRepository;
    private static List<Book> bookList;
    private List<BookDto> bookDtoList;

    @BeforeAll
    static void prepare() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/Book.json"))) {
            ObjectMapper objectMapper = new ObjectMapper();
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);
            bookList = objectMapper.readValue(reader, listType);
        }
    }

    @BeforeEach
    void init() {
        bookService = new SimpleBookService(bookRepository);
        bookDtoList = bookRepository.findAll().stream()
                .map(MappingUtils::mapToBookDto)
                .collect(Collectors.toList());

        bookRepository.deleteAll();
        bookRepository.saveAll(bookList);
    }

    @Test
    void getBooksFromJsonTest() {
        assertEquals(3, bookList.size(), "BookList count is not matching");
        assertEquals(Book.class, bookList.get(0).getClass(), "Class objects in bookList is not matching");
    }

    @Test
    void findAllBooksDtoTest() {
        bookDtoList = bookService.findAllBooksDTO();

        assertEquals(bookList.size(), bookDtoList.size(), "bookDtoList count is not matching");
        bookDtoList.forEach(bookDto ->
                bookList.forEach(book -> {
                            if (book.getIdBook() == bookDto.getIdBook()) {
                                assertEquals(book.getNameBook(), bookDto.getNameBook(), "Book name is not matching");
                                assertEquals(book.getAuthorBook(), bookDto.getAuthorBook(), "Book author is not matching");
                            }
                        }
                ));
    }

    @Test
    void convertToResponseDtoListTest() {
        List<ResponseDto> responseDtoList = bookService.convertToResponseDtoList(bookDtoList);

        assertEquals(bookDtoList.size(), responseDtoList.size(), "responseDtoList count is not matching");
        responseDtoList.forEach(responseDto ->
                bookDtoList.forEach(bookDto -> {
                            if (bookDto.getIdBook() == responseDto.getIdBook()) {
                                assertEquals(bookDto.getNameBook(), responseDto.getNameBook(), "Book name is not matching");
                                assertEquals(bookDto.getAuthorBook(), responseDto.getAuthorBook(), "Book author is not matching");
                            }
                        }
                ));
    }

    @Test
    void findAllTest() {
        List<Book> all = bookService.findAll();
        assertEquals(bookList.size(), all.size(), "Count books is not matching");
    }

    @Test
    void saveTest() {
        Book book = new Book()
                .setIdBook(0)
                .setNameBook("Book0")
                .setAuthorBook("Author0");
        bookService.save(book);

        Optional<Book> expectedBook = bookService.findById(0);

        assertNotNull(expectedBook.orElse(null), "ExpectedBook is null");
        assertNotNull(expectedBook.get().getNameBook(), "ExpectedBook is null");
        assertEquals(book.getNameBook(), expectedBook.get().getNameBook(), "Book name is not matching");
    }

    @Test
    void saveAllTest() {
        List<Book> books = Arrays.asList(
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
        );
        bookRepository.deleteAll();
        bookService.saveAll(books);

        List<Book> result = bookService.findAll();

        assertEquals(books.size(), result.size(), "Count books is not matching");

        result.forEach(book -> {
            if (777 == book.getIdBook()) {
                assertEquals("Book777", book.getNameBook());
                assertEquals("Author777", book.getAuthorBook());
            } else if (888 == book.getIdBook()) {
                assertEquals("Book888", book.getNameBook());
                assertEquals("Author888", book.getAuthorBook());
            } else if (999 == book.getIdBook()) {
                assertEquals("Book999", book.getNameBook());
                assertEquals("Author999", book.getAuthorBook());
            }
        });
    }
}
package com.sberTest.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sberTest.dto.BookDto;
import com.sberTest.dto.ResponseDto;
import com.sberTest.models.Book;
import com.sberTest.repositories.BookRepository;
import com.sberTest.utils.MappingUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.activation.DataSource;
import javax.persistence.EntityManager;
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

//как подключить ApplicationContext???

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class BookServiceTest {
    private SimpleBookService bookService;
    @Autowired
    private BookRepository bookRepository;
    private MappingUtils mappingUtils;

    private List<Book> bookList;
    private List<BookDto> bookDtoList;

    BookServiceTest() {
    }

    @BeforeEach
    void init() throws IOException {
        bookService = new SimpleBookService(bookRepository,mappingUtils);
        setUp();
    }

    void setUp() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Book.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);
        bookList = objectMapper.readValue(reader, listType);
//        bookDtoList = bookRepository.findAll().stream().map(MappingUtils::mapToBookDto).collect(Collectors.toList());
        bookRepository.deleteAll();
        bookRepository.saveAll(bookList);
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
        List<ResponseDto> responseDtoList = bookService.mappingToResponseDto(bookDtoList);

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
        List<Book> all = bookService.findAll();

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
        bookRepository.deleteAll();
        bookService.saveAll(books);


        List<Book> expectedBooks = bookService.findAll();

        assertEquals(3, expectedBooks.size(), "Count books is not matching");

        expectedBooks.forEach(book -> {
            if (777 == book.getIdBook()) {
                assertEquals("Book777", book.getNameBook(),"Not matching");
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
}
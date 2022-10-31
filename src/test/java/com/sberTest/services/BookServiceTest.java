package com.sberTest.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sberTest.controllers.BookController;
import com.sberTest.dto.dtoController;
import com.sberTest.dto.dtoRepository;
import com.sberTest.models.Book;
import com.sberTest.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


//как подключить ApplicationContext???
@ExtendWith(SpringExtension.class)
@ContextConfiguration("")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class BookServiceTest {
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final BookController bookController;
    List<Book> bookList;
    List<dtoRepository> allBooksR;


    void setUp() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Book.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);
        bookList = objectMapper.readValue(reader, listType);
    }

    @Test
    void givenJson() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Book.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);
        List<Book> bookList = objectMapper.readValue(reader, listType);

        assertEquals(3, bookList.size(), "BookList count is not matching");
        assertEquals(Book.class, bookList.get(0).getClass(), "Class objects in bookList is not matching");
    }

    @Test
    void findDtoRepositoryTest() throws IOException {
        set();
        allBooksR = bookService.findDtoRepository();

        assertEquals(bookList.size(), allBooksR.size(), "allBooksR count is not matching");
        allBooksR.forEach(bookR -> bookList.forEach(book -> {
                    if (book.getIdBook() == bookR.getIdBook()) {
                        assertEquals(book.getNameBook(), bookR.getNameBook(),"Name book is not matching");
                        assertEquals(book.getAuthorBook(), bookR.getAuthorBook(), "Author book is not matching");
                    }
                }
        ));
    }

    @Test
    void findDtoControllerTest() {
        List<dtoController> allBookC = bookController.getBook(allBooksR);

        assertEquals(allBooksR.size(), allBookC.size(), "allBookC count is not matching");
        allBookC.forEach(bookC -> allBooksR.forEach(bookR -> {
                    if (bookR.getIdBook() == bookC.getIdBook()) {
                        assertEquals(bookR.getNameBook(), bookC.getNameBook(),"Name book is not matching");
                        assertEquals(bookR.getAuthorBook(), bookC.getAuthorBook(), "Author book is not matching");
                    }
                }
        ));
    }

    @Test
    void findAll() {
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
    void saveAll() {
    }

    @Test
    void getPlace() {
    }

    @Test
    void getCount() {
    }
}
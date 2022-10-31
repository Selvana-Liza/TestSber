package com.sberTest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sberTest.controllers.BookController;
import com.sberTest.dto.dtoController;
import com.sberTest.dto.dtoRepository;
import com.sberTest.models.Book;
import com.sberTest.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitiateUtils implements CommandLineRunner {
    private final BookService bookService;
    private final BookController bookController;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("run");

        List<Book> bookList = bookService.givesJson();

        for(Book book : bookList) {
            List<Book> books = new ArrayList<>(
                    Arrays.asList(
                        new Book()
                                .setIdBook(book.getIdBook())
                                .setNameBook(book.getNameBook())
                                .setAuthorBook(book.getAuthorBook())
                    )
            );
            bookService.saveAll(books);
        }

        System.out.println("Книги из json");
        for (Book book : bookService.findAll()) {
            System.out.println(book);
        }
        System.out.println();

        List<dtoRepository> allBooksR = bookService.findDtoRepository();
        bookService.getCount(allBooksR);

        System.out.println("Книги из ДТО репозитория");
        for (dtoRepository book : allBooksR) {
            System.out.println(book);
        }
        System.out.println();

        System.out.println("Книги из ДТО контроллера");
        List<dtoController> allBookC = bookController.getBook(allBooksR);
        bookService.getPlace(allBookC);
        for (dtoController book : allBookC) {
            System.out.println(book);
        }
    }
}

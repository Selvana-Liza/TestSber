package com.sberTest.utils;

import com.sberTest.controllers.BookController;
import com.sberTest.dto.BookDto;
import com.sberTest.dto.ResponseDto;
import com.sberTest.models.Book;
import com.sberTest.services.SimpleBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InitiateUtils implements CommandLineRunner {
    private final SimpleBookService bookService;
    private final BookController bookController;

    @Override
    public void run(String... args) throws Exception {
        List<Book> bookJsonList = bookService.givesJson();

        for(Book bookJson : bookJsonList) {
            Book book = new Book()
                    .setIdBook(bookJson.getIdBook())
                    .setNameBook(bookJson.getNameBook())
                    .setAuthorBook(bookJson.getAuthorBook());
            bookService.save(book);
        }

        System.out.println("Книги из json");
        for (Book book : bookService.findAll()) {
            System.out.println(book);
        }
        System.out.println();

        List<BookDto> allBooksDto = bookService.mappingToBookDTO();
        bookService.getCount(allBooksDto);

        System.out.println("Книги ДТО репозитория");
        for (BookDto book : allBooksDto) {
            System.out.println(book);
        }
        System.out.println();


        List<ResponseDto> allBooksResponse = bookController.getBook(allBooksDto);
        bookService.getPlace(allBooksResponse);

        System.out.println("Книги ДТО контроллера");
        for (ResponseDto book : allBooksResponse) {
            System.out.println(book);
        }
    }
}

package com.sberTest.services;

import com.sberTest.controllers.BookController;
import com.sberTest.dto.BookDto;
import com.sberTest.dto.ResponseDto;
import com.sberTest.models.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InitiateService implements CommandLineRunner {
    private final SimpleBookService bookService;
    private final BookController bookController;

    @Override
    public void run(String... args) throws Exception {
        final List<Book> booksList = bookService.getBooksFromJson();

        for (Book book : booksList) {
            bookService.save(book);
        }

        System.out.println("Книги из json");
        for (Book book : bookService.findAll()) {
            System.out.println(book);
        }
        System.out.println();

        final List<BookDto> allBooksDto = bookService.findAllBooksDTO();
        bookService.getCount(allBooksDto);

        System.out.println("Книги ДТО репозитория");
        for (BookDto bookDto : allBooksDto) {
            System.out.println(bookDto);
        }
        System.out.println();

        final List<ResponseDto> allBooksResponseDto = bookController.getResponseDtoList(allBooksDto);
        bookService.getPlace(allBooksResponseDto);

        System.out.println("Книги ДТО контроллера");
        for (ResponseDto responseDto : allBooksResponseDto) {
            System.out.println(responseDto);
        }
    }
}

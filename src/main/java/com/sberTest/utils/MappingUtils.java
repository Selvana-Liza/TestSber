package com.sberTest.utils;

import com.sberTest.dto.BookDto;
import com.sberTest.dto.ResponseDto;
import com.sberTest.models.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MappingUtils {
    //из Book в BookDto
    public static BookDto mapToBookDto(Book book) {
        return BookDto.builder()
                .idBook(book.getIdBook())
                .nameBook(book.getNameBook())
                .authorBook(book.getAuthorBook())
                .build();
    }

    //из BookDto в ResponseDto
    public static ResponseDto mapToResponseDto(BookDto bookDto) {
        return ResponseDto.builder()
                .idBook(bookDto.getIdBook())
                .nameBook(bookDto.getNameBook())
                .authorBook(bookDto.getAuthorBook())
                .countBooks(bookDto.getCountBooks())
                .build();
    }
}

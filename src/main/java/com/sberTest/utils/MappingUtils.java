package com.sberTest.utils;

import com.sberTest.dto.ResponseDto;
import com.sberTest.dto.BookDto;
import com.sberTest.models.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MappingUtils {
    //из Book в BookDto
        public BookDto mapToBookDto(Book book) {
            return BookDto.builder()
                    .idBook(book.getIdBook())
                    .nameBook(book.getNameBook())
                    .authorBook(book.getAuthorBook())
                    .build();
        }

    //из BookDto в ResponseDto
    public ResponseDto mapToResponseDto(BookDto bookDto){
        return ResponseDto.builder()
                .idBook(bookDto.getIdBook())
                .nameBook(bookDto.getNameBook())
                .authorBook(bookDto.getAuthorBook())
                .countBooks(bookDto.getCountBooks())
                .build();
    }
}

package com.sberTest.controllers;

import com.sberTest.dto.ResponseDto;
import com.sberTest.dto.BookDto;
import com.sberTest.services.SimpleBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

//1.      Слой контроллеров. Требование к контроллеру:
//        a.      Контроллер, в качестве ответа, принимает собственное ДТО
//        b.      Контроллер работает только со слоем бизнес логики

@Controller
@RequiredArgsConstructor
public class BookController {
    private final SimpleBookService bookService;

    public List<ResponseDto> getResponseDtoList(List<BookDto> bookDtoList){
        return bookService.convertToResponseDtoList(bookDtoList);
    }
}

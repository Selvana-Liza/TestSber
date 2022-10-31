package com.sberTest.controllers;

import com.sberTest.dto.dtoController;
import com.sberTest.dto.dtoRepository;
import com.sberTest.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

//не уверена в функции контроллера
//1.      Слой контроллеров. Требование к контроллеру:
//        a.      Контроллер, в качестве ответа, принимает собственное ДТО
//        b.      Контроллер работает только со слоем бизнес логики

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    public List<dtoController> getBook(List<dtoRepository> dtoRep){
        return bookService.findDtoController(dtoRep);
    }
}

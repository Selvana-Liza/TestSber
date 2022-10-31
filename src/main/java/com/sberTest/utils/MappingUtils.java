package com.sberTest.utils;

import com.sberTest.dto.dtoController;
import com.sberTest.dto.dtoRepository;
import com.sberTest.models.Book;
import org.springframework.stereotype.Service;

import java.util.Random;

//не уверена в правильности в работе с ДТО, никогда их не делала
@Service
public class MappingUtils {
    //из Book в dtoRepository
    public dtoRepository mapToDtoRepository(Book book){
        dtoRepository dtoRepos = new dtoRepository();
        dtoRepos.setIdBook(book.getIdBook());
        dtoRepos.setNameBook(book.getNameBook());
        dtoRepos.setAuthorBook(book.getAuthorBook());
        return dtoRepos;
    }

    //из dtoRepository в dtoController
    public dtoController mapToDtoController(dtoRepository dtoRepository){
        dtoController dtoContr = new dtoController();
        dtoContr.setIdBook(dtoRepository.getIdBook());
        dtoContr.setNameBook(dtoRepository.getNameBook());
        dtoContr.setAuthorBook(dtoRepository.getAuthorBook());
        dtoContr.setCountBooks(dtoRepository.getCountBooks());
        return dtoContr;
    }
}

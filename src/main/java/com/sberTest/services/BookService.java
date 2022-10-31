package com.sberTest.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sberTest.dto.dtoController;
import com.sberTest.dto.dtoRepository;
import com.sberTest.models.Book;
import com.sberTest.repositories.BookRepository;
import com.sberTest.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

//3.      Слой бизнес логики. В этом слое надо реализовать следующие этапы работы:
//        a.      Получение запроса от контролера
//        b.      Обращение к репозиторию за необходимыми данными
//        c.      Обработка полученных данных, полученных от репозитория
//        d.      Преобразование этих данных в ДТО для контроллера и отправка результата контроллеру

//у меня здесь как то смешались и функции репозитория и бизнес логики, т.к. репозиторий у меня интерфейсом
//надо создать еще один класс отдельно для бизнес логики?

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final MappingUtils mappingUtils;

    public List<Book> givesJson() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Book.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);
        List<Book> bookList = objectMapper.readValue(reader, listType);
        return bookList;
    }


    //Не уверена в правильности работы с ДТО
    public List<dtoRepository> findDtoRepository() {
        return bookRepository.findAll().stream()
                .map(mappingUtils::mapToDtoRepository)
                .collect(Collectors.toList());
    }

    public List<dtoController> findDtoController(List<dtoRepository> dtoRep){
        return dtoRep.stream()
                .map(mappingUtils::mapToDtoController)
                .collect(Collectors.toList());
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Optional<Book> findById(int id){
        return bookRepository.findById(id);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void saveAll(List<Book> fruits) {
        bookRepository.saveAll(fruits);
    }

    public void getPlace( List<dtoController> list){
        list.forEach(dtoController ->
        {
            int number = new Random().nextInt(4);
            switch (number) {
                case 0: {
                    dtoController.setPlace("Place1");
                    break;
                }
                case 1: {
                    dtoController.setPlace("Place2");
                    break;
                }
                case 2: {
                    dtoController.setPlace("Place3");
                    break;
                }
                default:
                    dtoController.setPlace("none");
                    break;
            }
        });
    }

    public void getCount(List<dtoRepository> list) {
        list.forEach(dtoRepository ->
                dtoRepository.setCountBooks(new Random().nextInt(100)+1)
        );
    }
}

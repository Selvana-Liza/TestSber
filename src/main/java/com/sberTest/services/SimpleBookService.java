package com.sberTest.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sberTest.dto.ResponseDto;
import com.sberTest.dto.BookDto;
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

@Service
@RequiredArgsConstructor
public class SimpleBookService implements BookService {
    private final BookRepository bookRepository;
    private final MappingUtils mappingUtils;

    @Override
    public List<Book> givesJson() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Book.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);
        List<Book> bookList = objectMapper.readValue(reader, listType);
        return bookList;
    }

    public List<BookDto> mappingToBookDTO() {
        return bookRepository.findAll().stream()
                .map(mappingUtils::mapToBookDto)
                .collect(Collectors.toList());
    }

    public List<ResponseDto> mappingToResponseDto(List<BookDto> bookDtoList){
        return bookDtoList.stream()
                .map(mappingUtils::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(int id){
        return bookRepository.findById(id);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void saveAll(List<Book> books) {
        bookRepository.saveAll(books);
    }

    public void getPlace( List<ResponseDto> list){
        list.forEach(responseDto ->
        {
            int number = new Random().nextInt(4);
            switch (number) {
                case 0: {
                    responseDto.setPlace("Place1");
                    break;
                }
                case 1: {
                    responseDto.setPlace("Place2");
                    break;
                }
                case 2: {
                    responseDto.setPlace("Place3");
                    break;
                }
                default:
                    responseDto.setPlace("none");
                    break;
            }
        });
    }

    public void getCount(List<BookDto> list) {
        list.forEach(bookDto ->
                bookDto.setCountBooks(new Random().nextInt(100)+1)
        );
    }
}

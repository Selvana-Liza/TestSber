package com.sberTest.services;

import com.sberTest.models.Book;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> givesJson() throws IOException;
    List<Book> findAll();
    Optional<Book> findById(int id);
    void save(Book book);
    void saveAll(List<Book> books);

}

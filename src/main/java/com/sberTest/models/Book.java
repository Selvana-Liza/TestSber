package com.sberTest.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;


@Accessors(chain = true)
@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Book {
    @Id
    private int idBook;
    private String nameBook;
    private String authorBook;

    @Override
    public String toString() {
        return "Book {" + "id= " + idBook + ", name= '" + nameBook + '\'' + ", author= " + authorBook +'}';
    }
}

package com.sberTest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class dtoRepository {
    int idBook;
    String nameBook;
    String authorBook;
    int countBooks;

    @Override
    public String toString() {
        return "Book {" + "id= " + idBook + ", name= '" + nameBook + '\''
                + ", author=" + authorBook + ", count= " + countBooks  +'}';
    }
}

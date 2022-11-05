package com.sberTest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Builder
public class ResponseDto {
    private int idBook;
    private String nameBook;
    private String authorBook;
    private int countBooks;
    private String place;

    @Override
    public String toString() {
        return "Book {" + "id= " + idBook + ", name= '" + nameBook + '\'' + ", author= " + authorBook
                + ", count= " + countBooks + ", place='" + place +'}';
    }
}

package com.sberTest.repositories;

import com.sberTest.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//2.      Слой репозитория. Требование к репозиторию:
//        a.      Репозиторий отвечает за запросы собственным ДТО
//        b.      Репозиторий только обращается к источнику данных
//        c.      Репозиторий только конвертирует данные в собственный ДТО
//        d.      Репозиторий не проводит никаких бизнес операций с данными
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}

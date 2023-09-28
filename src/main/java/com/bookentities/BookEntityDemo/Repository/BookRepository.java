package com.bookentities.BookEntityDemo.Repository;

import com.bookentities.BookEntityDemo.Model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book save(Book book);

    void deleteById(Long id);
}
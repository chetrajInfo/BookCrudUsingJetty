package com.bookentities.BookEntityDemo.Service;

import com.bookentities.BookEntityDemo.Model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    Book saveBook(Book book);

    void deleteBook(Long id);
}
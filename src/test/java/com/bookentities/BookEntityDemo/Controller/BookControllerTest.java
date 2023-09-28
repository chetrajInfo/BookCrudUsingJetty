package com.bookentities.BookEntityDemo.Controller;

import com.bookentities.BookEntityDemo.Model.Book;
import com.bookentities.BookEntityDemo.Service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    public void getAllBooksTest() throws Exception {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book One");
        book1.setAuthor("Author One");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book Two");
        book2.setAuthor("Author Two");

        given(bookService.getAllBooks()).willReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id': 1,'title': 'Book One','author': 'Author One'},{'id': 2,'title': 'Book Two','author': 'Author Two'}]"));
    }

    @Test
    public void getBookByIdTest() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book One");
        book.setAuthor("Author One");

        given(bookService.getBookById(1L)).willReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1,'title': 'Book One','author': 'Author One'}"));
    }

    @Test
    public void saveBookTest() throws Exception {
        Book bookToSave = new Book();
        bookToSave.setTitle("New Book");
        bookToSave.setAuthor("New Author");

        Book savedBook = new Book();
        savedBook.setId(3L);
        savedBook.setTitle("New Book");
        savedBook.setAuthor("New Author");

        given(bookService.saveBook(any(Book.class))).willReturn(savedBook);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookToSave)))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 3,'title': 'New Book','author': 'New Author'}"));
    }

    //after passing -1 it still test is pass so to show different status code if the value does not exit we
    //have to change in Controller class delete by id method
    @Test
    public void deleteBookTest() throws Exception {
        mockMvc.perform(delete("/api/books/-1"))
                .andExpect(status().isOk());
    }
}

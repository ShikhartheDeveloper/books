package com.Books.books.Controller;

import com.Books.books.Entities.Book;
import com.Books.books.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/get/all")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/create/book")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }
}

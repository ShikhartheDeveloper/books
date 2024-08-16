package com.Books.books.Service;

import com.Books.books.Entities.Book;
import com.Books.books.Entities.User;
import com.Books.books.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class booksService {


    public booksService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private BookRepository bookRepository;

    List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book addBook(Book book){
        return this.bookRepository.save(book);
    }
}

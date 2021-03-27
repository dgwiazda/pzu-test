package com.dgwiazda.pzutask.rest;

import com.dgwiazda.pzutask.persistence.model.BooksEntity;
import com.dgwiazda.pzutask.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "*")
public class BooksController {

    private BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BooksEntity>> getAllBooks(
            @RequestParam(required = false) String param,
            @RequestParam(required = false) String sort) {

        List<BooksEntity> books = bookService.find(param, sort);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<?> getBookById(@PathVariable String isbn) {
        BooksEntity book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BooksEntity> addBook(@RequestBody BooksEntity booksEntity) {
        BooksEntity booksEntity1 = bookService.addBook(booksEntity);
        return ResponseEntity.ok(booksEntity1);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<?> editBook(@PathVariable String isbn, @RequestParam String title) {
        BooksEntity booksEntity = bookService.editBook(isbn, title);
        return ResponseEntity.ok(booksEntity);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
        return ResponseEntity.ok("Book deleted!");
    }
}

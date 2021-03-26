package com.dgwiazda.pzutask.rest;

import com.dgwiazda.pzutask.persistence.model.BooksEntity;
import com.dgwiazda.pzutask.services.repository.BooksRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BooksController {

    private BooksRepository booksRepository;

    public BooksController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @GetMapping
    public ResponseEntity<List<BooksEntity>> getALlBooks() {
        return ResponseEntity.ok(booksRepository.findAll());
    }

    @GetMapping("/sort-title-desc")
    public ResponseEntity<List<BooksEntity>> getALlBooksTitleDesc() {
        return ResponseEntity.ok(booksRepository.findByOrderByTitleDesc());
    }

    @GetMapping("/sort-title-asc")
    public ResponseEntity<List<BooksEntity>> getALlBooksTitleAsc() {
        return ResponseEntity.ok(booksRepository.findByOrderByTitleAsc());
    }

    @GetMapping("/sort-isbn-desc")
    public ResponseEntity<List<BooksEntity>> getALlBooksIsbnDesc() {
        return ResponseEntity.ok(booksRepository.findByOrderByIsbnDesc());
    }

    @GetMapping("/sort-isbn-asc")
    public ResponseEntity<List<BooksEntity>> getALlBooksIsbnAsc() {
        return ResponseEntity.ok(booksRepository.findByOrderByIsbnAsc());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<?> getBookById(@PathVariable String isbn) {
        if (!Pattern.matches("\\d-\\d{4}-\\d{5}", isbn)) {
            return ResponseEntity.badRequest().body("ISBN number is not valid! It should contains only digits and looks like x-xxxx-xxxxx");
        } else {
            if (booksRepository.findById(isbn).isEmpty()) {
                return ResponseEntity.badRequest().body("Book with this ISBN number doesn't exist!");
            } else {
                return ResponseEntity.ok(booksRepository.findById(isbn).get());
            }
        }
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody BooksEntity booksEntity) {
        if (!Pattern.matches("\\d-\\d{4}-\\d{5}", booksEntity.getIsbn())) {
            return ResponseEntity.badRequest().body("ISBN number is not valid! It should contains only digits and looks like x-xxxx-xxxxx");
        } else {
            if (booksRepository.findById(booksEntity.getIsbn()).isEmpty()) {
                return ResponseEntity.ok(booksRepository.save(booksEntity));
            } else {
                return ResponseEntity.badRequest().body("Book with this ISBN number already exist!");
            }
        }
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<?> editBook(@PathVariable String isbn, @RequestParam String title) {
        if (!Pattern.matches("\\d-\\d{4}-\\d{5}", isbn)) {
            return ResponseEntity.badRequest().body("ISBN number is not valid! It should contains only digits and looks like x-xxxx-xxxxx");
        } else {
            if (booksRepository.findById(isbn).isEmpty()) {
                return ResponseEntity.badRequest().body("Book with this ISBN number doesn't exist!");
            } else {
                BooksEntity booksEntity = booksRepository.findById(isbn).get();
                booksEntity.setTitle(title);
                return ResponseEntity.ok(booksRepository.save(booksEntity));
            }
        }
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) {
        if (!Pattern.matches("\\d-\\d{4}-\\d{5}", isbn)) {
            return ResponseEntity.badRequest().body("ISBN number is not valid! It should contains only digits and looks like x-xxxx-xxxxx");
        } else {
            if (booksRepository.findById(isbn).isEmpty()) {
                return ResponseEntity.badRequest().body("Book with this ISBN number doesn't exist!");
            } else {
                booksRepository.deleteById(isbn);
                return ResponseEntity.ok("Usunięto książkę o isbn: " + isbn);
            }
        }
    }
}

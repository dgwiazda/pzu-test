package com.dgwiazda.pzutask.service;

import com.dgwiazda.pzutask.persistence.model.BooksEntity;
import com.dgwiazda.pzutask.persistence.repository.BooksRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class BookService {

    private BooksRepository booksRepository;

    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<BooksEntity> find(String param, String sort) {
        if (sortParamExists(param, sort)) {
            return findWithSort(param, sort);
        }
        return findAll();
    }

    private boolean sortParamExists(String param, String sort) {
        return Objects.nonNull(param) && Objects.nonNull(sort);
    }

    private List<BooksEntity> findWithSort(String param, String sort) {
        if (sort.equals("asc")) {
            return booksRepository.findAll(Sort.by(Sort.Direction.ASC, param));
        } else {
            return booksRepository.findAll(Sort.by(Sort.Direction.DESC, param));
        }
    }

    private List<BooksEntity> findAll() {
        return booksRepository.findAll();
    }

    public BooksEntity getBookByIsbn(String isbn) {
        if (!Pattern.matches("\\d-\\d{4}-\\d{5}", isbn)) {
            throw new IllegalArgumentException("ISBN number is not valid! It should contains only digits and looks like x-xxxx-xxxxx");
        } else {
            return booksRepository.findById(isbn)
                    .orElseThrow(() -> new IllegalArgumentException("ISBN number doesn't exist!"));
        }
    }

    public BooksEntity addBook(BooksEntity booksEntity) {
        if (!Pattern.matches("\\d-\\d{4}-\\d{5}", booksEntity.getIsbn())) {
            throw new IllegalArgumentException("ISBN number is not valid! It should contains only digits and looks like x-xxxx-xxxxx");
        } else {
            if (booksRepository.existsById(booksEntity.getIsbn())) {
                throw new IllegalArgumentException("Book with this ISBN number already exist!");
            }
            return booksRepository.save(booksEntity);
        }
    }

    public BooksEntity editBook(String isbn, String title) {
        if (!Pattern.matches("\\d-\\d{4}-\\d{5}", isbn)) {
            throw new IllegalArgumentException("ISBN number is not valid! It should contains only digits and looks like x-xxxx-xxxxx");
        } else {
            if (!booksRepository.existsById(isbn)) {
                throw new IllegalArgumentException("Book with this ISBN number doesn't exist!");
            }
            BooksEntity booksEntity = booksRepository.findById(isbn).get();
            booksEntity.setTitle(title);
            return booksRepository.save(booksEntity);
        }
    }

    public void deleteBook(String isbn) {
        if (!Pattern.matches("\\d-\\d{4}-\\d{5}", isbn)) {
            throw new IllegalArgumentException("ISBN number is not valid! It should contains only digits and looks like x-xxxx-xxxxx");
        } else {
            if (!booksRepository.existsById(isbn)) {
                throw new IllegalArgumentException("Book with this ISBN number doesn't exist!");
            }
            booksRepository.deleteById(isbn);
        }

    }
}
package com.dgwiazda.pzutask.service;

import com.dgwiazda.pzutask.persistence.model.BooksEntity;
import com.dgwiazda.pzutask.persistence.repository.BooksRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private BooksRepository booksRepository;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        booksRepository = Mockito.mock(BooksRepository.class);
        bookService = new BookService(booksRepository);
    }

    @Test
    void shouldFindAll() {
        String param = null;
        String sort = null;
        when(booksRepository.findAll()).thenReturn(List.of());
        bookService.find(param, sort);
        Mockito.verify(booksRepository, times(1)).findAll();
    }

    @Test
    void shouldFindAllWithSort() {
        String param = "title";
        String sort = "asc";
        when(booksRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))).thenReturn(List.of());
        bookService.find(param, sort);
        Mockito.verify(booksRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, param));
    }

    @Test
    void shouldAddBook() {
        BooksEntity booksEntity = new BooksEntity("1-1111-11111", "Title");
        when(booksRepository.save(booksEntity)).thenReturn(new BooksEntity(booksEntity.getIsbn(), booksEntity.getTitle()));
        BooksEntity savedBook = bookService.addBook(booksEntity);
        Mockito.verify(booksRepository, times(1)).save(booksEntity);
        Assertions.assertEquals(savedBook.getIsbn(), booksEntity.getIsbn());
        Assertions.assertEquals(savedBook.getTitle(), booksEntity.getTitle());
    }

    @Test
    void shouldThrowExceptionDueToWrongIsbn() {
        BooksEntity booksEntity = new BooksEntity("abc", "Title");
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.addBook(booksEntity));
    }

    @Test
    void shouldEditBook() {
        String isbn = "1-1111-11111";
        String newTitle = "New";
        String oldTitle = "Old";
        BooksEntity bookToEdit = new BooksEntity(isbn, newTitle);
        BooksEntity oldBook = new BooksEntity(isbn, oldTitle);
        when(booksRepository.existsById(isbn)).thenReturn(true);
        when(booksRepository.findById(isbn)).thenReturn(Optional.of(oldBook));
        when(booksRepository.save(bookToEdit)).thenReturn(new BooksEntity(bookToEdit.getIsbn(), bookToEdit.getTitle()));
        BooksEntity editedBook = bookService.editBook(isbn, newTitle);
        Mockito.verify(booksRepository, times(1)).save(bookToEdit);
        Assertions.assertEquals(editedBook.getIsbn(), bookToEdit.getIsbn());
        Assertions.assertEquals(editedBook.getTitle(), bookToEdit.getTitle());
    }

    @Test
    void shouldDeleteBook() {
        String isbn = "1-1111-11111";
        Mockito.doNothing().when(booksRepository).deleteById(isbn);
        when(booksRepository.existsById(isbn)).thenReturn(true);
        bookService.deleteBook(isbn);
        Mockito.verify(booksRepository, times(1)).deleteById(isbn);
    }
}
package com.dgwiazda.pzutask.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_books")
public class BooksEntity {

    @Id
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "title")
    private String title;

    public BooksEntity(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooksEntity that = (BooksEntity) o;
        return Objects.equals(isbn, that.isbn) &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title);
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                '}';
    }
}

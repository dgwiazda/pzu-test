package com.dgwiazda.pzutask.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    public String toString() {
        return "BookEntity{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                '}';
    }
}

package com.dgwiazda.pzutask.services.repository;

import com.dgwiazda.pzutask.persistence.model.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, String> {

    List<BooksEntity> findByOrderByTitleDesc();
    List<BooksEntity> findByOrderByTitleAsc();
    List<BooksEntity> findByOrderByIsbnAsc();
    List<BooksEntity> findByOrderByIsbnDesc();
}

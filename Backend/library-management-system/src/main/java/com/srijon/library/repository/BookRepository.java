package com.srijon.library.repository;

import com.srijon.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    //sorting by title
    public Page<Book> findByTitleContainingIgnoreCase(
            String title ,Pageable pageable);
}

package com.mydiary.book.repository;


import com.mydiary.book.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByIdAndUserId(Long bookId, Long userId);
}

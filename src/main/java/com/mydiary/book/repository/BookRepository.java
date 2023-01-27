package com.mydiary.book.repository;


import com.mydiary.book.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByIdAndUserId(Long bookId, Long userId);

    Page<Book> findByUser_Id(Long userId, Pageable pageable);
}

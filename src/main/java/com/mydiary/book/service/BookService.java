package com.mydiary.book.service;

import com.mydiary.book.model.dto.BookDto;
import com.mydiary.book.model.entity.Book;
import com.mydiary.book.repository.BookRepository;
import com.mydiary.common.exception.BadRequestException;
import com.mydiary.common.exception.NotFoundException;
import com.mydiary.common.model.dto.ResponseDto;
import com.mydiary.user.model.entity.User;
import com.mydiary.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.util.Objects.isNull;

@Service
public class BookService {

    BookRepository bookRepository;
    UserRepository userRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<ResponseDto<String>> createBook(Long userId, BookDto.BookInfoDto bookInfoDto) {
        User user;
        try {
            user = userRepository.findById(userId).get();
        }
        catch(NoSuchElementException e) {
            throw new BadRequestException("존재하지 않는 유저입니다.");
        }
        bookRepository.save(bookInfoDto.toBookEntity(user));

        ResponseDto<String> responseDto = new ResponseDto<>("새로운 도서가 등록되었습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public BookDto.BookInfoDto getBook(Long bookId, Long userId) {
        Book book = bookRepository.findByIdAndUserId(bookId, userId);
        if(isNull(book)) {
            throw new NotFoundException("해당하는 도서가 존재하지 않습니다.");
        }

        return BookDto.BookInfoDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .field(book.getField())
                .purchaseYn(book.isPurchaseYn())
                .status(book.getStatus())
                .url(book.getUrl())
                .memo(book.getMemo())
                .build();
    }

    public ResponseEntity<ResponseDto<String>> deleteBook(Long bookId, Long userId) {
        Book book = bookRepository.findByIdAndUserId(bookId, userId);
        if(isNull(book)) {
            throw new NotFoundException("해당하는 도서가 존재하지 않습니다.");
        }
        book.setDeleteYn(true);
        bookRepository.save(book);

        ResponseDto<String> responseDto = new ResponseDto<>("도서가 삭제되었습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

package com.mydiary.book.controller;

import com.mydiary.book.model.dto.BookDto;
import com.mydiary.book.service.BookService;
import com.mydiary.common.model.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags={"02.Book"})
@RestController
@RequestMapping(path = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "도서 등록", notes = "도서를 등록합니다.\n")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto<String>> createBook(
            HttpServletRequest httpServletRequest, @RequestBody BookDto.BookInfoDto bookInfoDto) {
        Long userId = Long.valueOf(String.valueOf(httpServletRequest.getAttribute("id")));
        return bookService.createBook(userId, bookInfoDto);
    }

    @ApiOperation(value = "도서 상세조회", notes = "도서를 상세 조회합니다.\n")
    @GetMapping("/{bookId}")
    public BookDto.BookInfoDto getBook(
            HttpServletRequest httpServletRequest, @PathVariable Long bookId) {
        Long userId = Long.valueOf(String.valueOf(httpServletRequest.getAttribute("id")));

        return bookService.getBook(bookId, userId);
    }

    @ApiOperation(value = "도서 목록조회", notes = "도서 목록을 조회합니다.\n")
    @GetMapping("/list")
    public BookDto.BookListDto getBookList(
            HttpServletRequest httpServletRequest,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Long userId = Long.valueOf(String.valueOf(httpServletRequest.getAttribute("id")));
        page -= 1;
        return bookService.getBookList(userId, page, size);
    }

    @ApiOperation(value = "도서 정보 변경", notes = "도서 정보를 변경합니다.\n")
    @PatchMapping("/{bookId}")
    public ResponseEntity<ResponseDto<String>> update(
            HttpServletRequest httpServletRequest,
            @PathVariable Long bookId,
            @RequestBody BookDto.BookInfoDto bookInfoDto) {
        Long userId = Long.valueOf(String.valueOf(httpServletRequest.getAttribute("id")));

        return bookService.updateBook(userId, bookId, bookInfoDto);
    }

    @ApiOperation(value = "도서 삭제", notes = "도서를 삭제합니다.\n")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<ResponseDto<String>> deleteBook(
            HttpServletRequest httpServletRequest, @PathVariable Long bookId) {
        Long userId = Long.valueOf(String.valueOf(httpServletRequest.getAttribute("id")));

        return bookService.deleteBook(bookId, userId);
    }
}

package com.mydiary.book.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mydiary.book.model.entity.Book;
import com.mydiary.user.model.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    @Data
    @Builder
    public static class BookInfoDto {
        String title;
        String author;
        String field;
        boolean purchaseYn;
        String status;
        String url;
        String memo;

        public Book toBookEntity(User user) {
            return Book.builder()
                    .user(user)
                    .title(title)
                    .author(author)
                    .field(field)
                    .purchaseYn(purchaseYn)
                    .status(status)
                    .url(url)
                    .memo(memo)
                    .build();
        }
    }
}

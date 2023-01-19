package com.mydiary.book.model.entity;

import com.mydiary.common.model.entity.BaseTimeEntity;
import com.mydiary.user.model.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "book")
@Where(clause = "delete_yn = 0")
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String author;

    private String field;

    @Column(name = "purchase_yn")
    private boolean purchaseYn;

    private String status;

    private String url;

    private String memo;

    @Column(name = "delete_yn")
    private boolean deleteYn;
}

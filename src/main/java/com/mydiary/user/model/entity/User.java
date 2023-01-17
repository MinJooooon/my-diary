package com.mydiary.user.model.entity;


import com.mydiary.common.model.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user")
@Where(clause = "show_yn = 1")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "show_yn")
    private boolean showYn;
}

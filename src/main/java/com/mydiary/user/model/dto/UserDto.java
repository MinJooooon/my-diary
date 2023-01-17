package com.mydiary.user.model.dto;

import com.mydiary.user.model.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserDto {

    @Data
    @Builder
    public static class RoleDto {
        private Long id;
        private String password;
        private List<String> roles;
    }

    @Data
    @Builder
    public static class UserInfoDto {
        private Long id;
        private String username;
        private String name;
        private String phone;
        private LocalDate birthDate;
        private String role;
    }

    @Data
    @Builder
    public static class SignUpDto {
        private String username;
        private String password;
        private String name;
        private String phone;
        private LocalDate birthDate;

        public User toUserEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .phone(phone)
                    .birthDate(birthDate)
                    .userRole("USER")
                    .build();
        }
    }
}

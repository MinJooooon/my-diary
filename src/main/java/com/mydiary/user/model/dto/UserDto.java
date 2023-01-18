package com.mydiary.user.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @Data
    @Builder
    public static class SignInDto {
        private String username;
        private String password;
    }

    @Data
    @Builder
    public static class UserInfoDto {
        private String name;
        private String phone;
        private LocalDate birthDate;
    }

    @Data
    @Builder
    public static class ChangePasswordDto {
        private String currentPassword;
        private String newPassword;
    }

    @Data
    @Builder
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public static class Response {
        private Long id;
        private String username;
        private String name;
        private String phone;
        private LocalDate birthDate;
        private String role;
        private String accessToken;
    }
}

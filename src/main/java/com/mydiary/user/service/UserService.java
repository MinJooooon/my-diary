package com.mydiary.user.service;

import com.mydiary.common.model.dto.ResponseDto;
import com.mydiary.user.model.dto.UserDto;
import com.mydiary.user.model.entity.User;
import com.mydiary.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<ResponseDto<String>> SignUp(UserDto.SignUpDto signUpDto) {
        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        User user = userRepository.save(signUpDto.toUserEntity());
        ResponseDto<String> responseDto = new ResponseDto<>("회원가입이 완료되었습니다.");
        return new ResponseEntity<ResponseDto<String>>(responseDto, HttpStatus.OK);
    }

    public UserDto.UserInfoDto getUserInfoByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(isNull(user)) {
            return null;
        }
        return UserDto.UserInfoDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .role(user.getUserRole())
                .build();
    }
}

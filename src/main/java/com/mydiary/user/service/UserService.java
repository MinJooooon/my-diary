package com.mydiary.user.service;

import com.mydiary.common.config.security.JwtTokenProvider;
import com.mydiary.common.exception.BadRequestException;
import com.mydiary.common.exception.NotFoundException;
import com.mydiary.common.model.dto.ResponseDto;
import com.mydiary.user.model.dto.UserDto;
import com.mydiary.user.model.entity.User;
import com.mydiary.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static java.util.Objects.isNull;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<ResponseDto<String>> SignUp(UserDto.SignUpDto signUpDto) {
        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userRepository.save(signUpDto.toUserEntity());
        
        ResponseDto<String> responseDto = new ResponseDto<>("회원가입이 완료되었습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public UserDto.Response SignIn(Long userId) {
        User user;
        try {
            user = userRepository.findById(userId).get();
        }
        catch(NoSuchElementException e) {
            throw new NotFoundException("존재하지 않는 유저입니다.");
        }
        
        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), Arrays.asList(user.getUserRole()));

        return UserDto.Response.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .accessToken(accessToken)
                .build();
    }

    public UserDto.Response getUserInfo(Long userId) {
        User user;
        try {
            user = userRepository.findById(userId).get();
        }
        catch(NoSuchElementException e) {
            throw new NotFoundException("존재하지 않는 유저입니다.");
        }
        
        return UserDto.Response.builder()
                .username(user.getUsername())
                .name(user.getName())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .role(user.getUserRole())
                .build();
    }

    public ResponseEntity<ResponseDto<String>> changeUserInfo(Long userId, UserDto.UserInfoDto userInfoDto) {
        User user;
        try {
            user = userRepository.findById(userId).get();
        }
        catch(NoSuchElementException e) {
            throw new NotFoundException("존재하지 않는 유저입니다.");
        }
        user.setName(userInfoDto.getName());
        user.setPhone(userInfoDto.getPhone());
        user.setBirthDate(userInfoDto.getBirthDate());
        userRepository.save(user);
        
        ResponseDto<String> responseDto = new ResponseDto<>("회원정보가 변경되었습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto<String>> changePassword(Long userId, UserDto.ChangePasswordDto changePasswordDto) {
        User user;
        try {
            user = userRepository.findById(userId).get();
        }
        catch(NoSuchElementException e) {
            throw new NotFoundException("존재하지 않는 유저입니다.");
        }
        if(!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("현재 비밀번호가 틀립니다.");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);

        ResponseDto<String> responseDto = new ResponseDto<>("비밀번호가 변경되었습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
        
    }

    public ResponseEntity<ResponseDto<String>> deleteUser(Long userId) {
        User user;
        try {
            user = userRepository.findById(userId).get();
        }
        catch(NoSuchElementException e) {
            throw new NotFoundException("존재하지 않는 유저입니다.");
        }
        user.setDeleteYn(true);
        userRepository.save(user);
        ResponseDto<String> responseDto = new ResponseDto<>("회원탈퇴가 완료되었습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public Long checkUserId(Long userId) {
        try {
            userRepository.findById(userId).get();
        }
        catch(NoSuchElementException e) {
            return 0L;
        }
        return userId;
    }

    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(isNull(user)) {
            return 0L;
        }
        return user.getId();
    }

    public Long getUserIdByUsernameAndPassword(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(!isNull(user)) {
            if(!passwordEncoder.matches(password, user.getPassword())) {
                return 0L;
            }
            else {

                return user.getId();
            }
        }
        return 0L;
    }
}

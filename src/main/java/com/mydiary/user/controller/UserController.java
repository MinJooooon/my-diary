package com.mydiary.user.controller;

import com.mydiary.common.exception.BadRequestException;
import com.mydiary.common.model.dto.ResponseDto;
import com.mydiary.user.model.dto.UserDto;
import com.mydiary.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

@Api(tags={"01.User"})
@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.\n")
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> doSignUp(
            @RequestBody UserDto.SignUpDto signUpDto) {
        if(!isNull(userService.getUserInfoByUsername(signUpDto.getUsername()))) {
            throw new BadRequestException("이미 존재하는 ID 입니다.");
        }
        return userService.SignUp(signUpDto);
    }

}

package com.mydiary.user.controller;

import com.mydiary.common.exception.BadRequestException;
import com.mydiary.common.exception.NotFoundException;
import com.mydiary.common.model.dto.ResponseDto;
import com.mydiary.user.model.dto.UserDto;
import com.mydiary.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
        Long userId = userService.getUserIdByUsername(signUpDto.getUsername());
        if(!userId.equals(0L)) {
            throw new BadRequestException("이미 존재하는 ID 입니다.");
        }
        return userService.SignUp(signUpDto);
    }

    @ApiOperation(value = "로그인", notes = "로그인을 합니다.\n")
    @PostMapping("/signin")
    public UserDto.Response doSignIn(
            @RequestBody UserDto.SignInDto signInDto) {
        Long userId = userService.getUserIdByUsernameAndPassword(signInDto.getUsername(), signInDto.getPassword());
        if(userId.equals(0L)) {
            throw new BadRequestException("ID/비밀번호를 확인해주세요.");
        }
        return userService.SignIn(userId);
    }

    @ApiOperation(value = "회원 정보 조회", notes = "회원 정보를 조회합니다.\n")
    @GetMapping("/profile")
    public UserDto.Response getUserInfo(HttpServletRequest httpServletRequest) {
        Long id = Long.valueOf(String.valueOf(httpServletRequest.getAttribute("id")));
        Long userId = userService.checkUserId(id);
        if(userId.equals(0L)) {
            throw new NotFoundException("존재하지 않거나 탈퇴한 유저입니다.");
        }
        return userService.getUserInfo(userId);
    }

    @ApiOperation(value = "회원 정보 변경", notes = "회원 정보를 변경합니다.\n")
    @PatchMapping("/change-profile")
    public ResponseEntity<ResponseDto<String>> changeUserInfo(
            HttpServletRequest httpServletRequest,
            @RequestBody UserDto.UserInfoDto userInfoDto) {
        Long id = Long.valueOf(String.valueOf(httpServletRequest.getAttribute("id")));
        Long userId = userService.checkUserId(id);
        if(userId.equals(0L)) {
            throw new NotFoundException("존재하지 않거나 탈퇴한 유저입니다.");
        }

        return userService.changeUserInfo(userId, userInfoDto);
    }

    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호를 변경합니다.\n")
    @PatchMapping("change-password")
    public ResponseEntity<ResponseDto<String>> changePassword(
            HttpServletRequest httpServletRequest,
            @RequestBody UserDto.ChangePasswordDto changePasswordDto) {
        Long id = Long.valueOf(String.valueOf(httpServletRequest.getAttribute("id")));
        Long userId = userService.checkUserId(id);
        if(userId.equals(0L)) {
            throw new NotFoundException("존재하지 않거나 탈퇴한 유저입니다.");
        }
        return userService.changePassword(userId, changePasswordDto);
    }

    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴합니다.\n")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<String>> deleteUser(HttpServletRequest httpServletRequest) {
        Long id = Long.valueOf(String.valueOf(httpServletRequest.getAttribute("id")));
        Long userId = userService.checkUserId(id);
        if(userId.equals(0L)) {
            throw new NotFoundException("존재하지 않거나 탈퇴한 유저입니다.");
        }
        return userService.deleteUser(userId);
    }
}

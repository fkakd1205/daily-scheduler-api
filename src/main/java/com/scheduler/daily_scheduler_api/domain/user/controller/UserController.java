package com.scheduler.daily_scheduler_api.domain.user.controller;

import com.scheduler.daily_scheduler_api.aop.LoginCheck;
import com.scheduler.daily_scheduler_api.domain.message.Message;
import com.scheduler.daily_scheduler_api.domain.user.dto.UserPasswordUpdateReqDto;
import com.scheduler.daily_scheduler_api.domain.user.dto.UserSessionDto;
import com.scheduler.daily_scheduler_api.domain.user.dto.req.LoginReqDto;
import com.scheduler.daily_scheduler_api.domain.user.dto.req.UserDeleteReqDto;
import com.scheduler.daily_scheduler_api.domain.user.dto.req.UserDto;
import com.scheduler.daily_scheduler_api.domain.user.enums.UserStatus;
import com.scheduler.daily_scheduler_api.domain.user.service.UserServiceImpl;
import com.scheduler.daily_scheduler_api.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/users")
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        if (UserDto.hasNullDataBeforeSignUp(userDto)) {
            throw new NullPointerException("회원가입시 필수 데이터를 모두 입력해야 합니다.");
        }
        userService.register(userDto);

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> login(@RequestBody LoginReqDto loginRequest, HttpSession session) {
        String id = loginRequest.getUserId();
        String password = loginRequest.getPassword();
        UserDto userInfo = userService.login(id, password);
        UserSessionDto userSessionDto = new UserSessionDto(userInfo.getId(), userInfo.getUserId());

        if (userInfo.getStatus() == (UserStatus.ADMIN)) {
            SessionUtil.setLoginAdminId(session, userSessionDto);
        }
        else{
            SessionUtil.setLoginMemberId(session, userSessionDto);
        }

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("my-info")
    @LoginCheck
    public ResponseEntity<?> memberInfo(UserSessionDto userSession) {
        UserDto memberInfo = userService.getUserInfo(userSession.getUserId());

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .data(memberInfo)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    @PutMapping("logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    @LoginCheck
    public ResponseEntity<?> updateUserPassword(UserSessionDto userSession, @RequestBody UserPasswordUpdateReqDto userPwdReqDto) {
        String beforePassword = userPwdReqDto.getBeforePassword();
        String afterPassword = userPwdReqDto.getAfterPassword();

        userService.updatePassword(userSession.getUserId(), beforePassword, afterPassword);

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping
    @LoginCheck
    public ResponseEntity<?> deleteId(UserSessionDto userSession, @RequestBody UserDeleteReqDto userDeleteId) {
        userService.deleteId(userSession.getUserId(), userDeleteId.getPassword());

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }
}

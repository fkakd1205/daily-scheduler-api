package com.scheduler.daily_scheduler_api.domain.user.controller;

import com.scheduler.daily_scheduler_api.aop.login_check.LoginCheck;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/users")
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("sign-up")
    public void signUp(@RequestBody UserDto userDto) {
        if (UserDto.hasNullDataBeforeSignUp(userDto)) {
            throw new NullPointerException("회원가입시 필수 데이터를 모두 입력해야 합니다.");
        }
        userService.register(userDto);
    }

    @PostMapping("sign-in")
    public void login(@RequestBody LoginReqDto loginRequest, HttpSession session) {
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
    }

    @GetMapping("my-info")
    @LoginCheck
    public Object memberInfo(UserSessionDto userSession) {
        return userService.getUserInfo(userSession.getUserId());
    }

    @PutMapping("logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    @LoginCheck
    public void updateUserPassword(UserSessionDto userSession, @RequestBody UserPasswordUpdateReqDto userPwdReqDto) {
        String beforePassword = userPwdReqDto.getBeforePassword();
        String afterPassword = userPwdReqDto.getAfterPassword();

        userService.updatePassword(userSession.getUserId(), beforePassword, afterPassword);
    }

    @DeleteMapping
    @LoginCheck
    public void deleteId(UserSessionDto userSession, @RequestBody UserDeleteReqDto userDeleteId) {
        userService.deleteId(userSession.getUserId(), userDeleteId.getPassword());
    }
}

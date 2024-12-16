package com.scheduler.daily_scheduler_api.aop.login_check;

import com.scheduler.daily_scheduler_api.domain.user.dto.UserSessionDto;
import com.scheduler.daily_scheduler_api.exception.CustomInvalidUserException;
import com.scheduler.daily_scheduler_api.utils.SessionUtil;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
@Log4j2
public class LoginCheckAspect {

    // LoginCheck 어노테이션이 붙어있는 곳에서 aop를 실행
    @Around("@annotation(com.scheduler.daily_scheduler_api.aop.login_check.LoginCheck) && @ annotation(loginCheck)")
    public Object userLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable {
        HttpSession session = (HttpSession) ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        UserSessionDto userSession = null;
        int idIndex = 0;

        // member 확인
        userSession = SessionUtil.getLoginAdminId(session);

        // admin 확인
        if (userSession == null) {
            userSession = SessionUtil.getLoginMemberId(session);
        }

        if (userSession == null) {
            log.info(proceedingJoinPoint.toString() + "로그인 인증 실패");
            throw new CustomInvalidUserException("인증되지 않은 접근입니다.") {};
        }

        Object[] modifiedArgs = proceedingJoinPoint.getArgs();

        if(proceedingJoinPoint.getArgs() != null) {
            modifiedArgs[idIndex] = userSession;
        }

        // 실제 컨트롤러에게 값을 넘긴다
        return proceedingJoinPoint.proceed(modifiedArgs);
    }
}

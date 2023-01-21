package com.example.jpamvc.web.argumentResolver;

import com.example.jpamvc.domain.Member;
import com.example.jpamvc.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("LoginMemberArgumentResolver.supportsParameter");
        boolean annotationMatch  = parameter.hasParameterAnnotation(LoginMember.class);
        boolean assignableFromMember = Member.class.isAssignableFrom(parameter.getParameterType());
        return annotationMatch && assignableFromMember;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("LoginMemberArgumentResolver.resolveArgument");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        mavContainer.addAttribute("loginMember", loginMember);
        return loginMember;
    }
}

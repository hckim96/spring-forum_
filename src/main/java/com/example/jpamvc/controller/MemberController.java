package com.example.jpamvc.controller;

import com.example.jpamvc.domain.Comment;
import com.example.jpamvc.domain.Member;
import com.example.jpamvc.dto.MemberLoginDto;
import com.example.jpamvc.dto.MemberSignupDto;
import com.example.jpamvc.exception.UnauthorizedException;
import com.example.jpamvc.myUtil.ScriptUtils;
import com.example.jpamvc.service.MemberService;
import com.example.jpamvc.web.SessionConst;
import com.example.jpamvc.web.argumentResolver.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
//    web:
//    resources:
//    static-locations:
//    @Value("${spring.web.resources.static-locations}")
//    private String tttmp;
    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String redirectUrl, @RequestParam(required = false) String needLogin) {
        model.addAttribute("memberLoginDto", new MemberLoginDto());
//        log.info("need login");
        log.info("needLogin = " + needLogin);
        if (needLogin != null) {
//            log.info("need login");
            model.addAttribute("needLogin", true);
        }
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        request.getSession(false).invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute MemberLoginDto form,
                            BindingResult bindingResult,
                            @RequestParam(defaultValue = "/" ,name = "redirectUrl") String redirectUrl,
                            HttpServletRequest request
    ) {
        log.info("postLogin");
        Member member = memberService.login(form.getLoginId(), form.getPassword());
        if (member == null) {
            log.info("login fail");
            bindingResult.reject("login-error", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        log.info("redirectUrl = " + redirectUrl);
        return "redirect:" + redirectUrl;
    }


    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("memberSignupDto", new MemberSignupDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(@Validated @ModelAttribute MemberSignupDto form, BindingResult bindingResult, Model model) {
//        log.info("MemberController.postSignup");
//        log.info("form = " + form);
        if (bindingResult.hasErrors()) {
//            log.info("bindingResult = " + bindingResult);
            return "signup";
        }

        Member member = new Member();
        member.setLoginId(form.getLoginId());
        member.setPassword(form.getPassword());

        try {
            memberService.join(member);
        } catch (IllegalStateException e) {
            bindingResult.reject("signup-error", e.getMessage());
            return "signup";
        }
        return "redirect:/";
    }



    @GetMapping("/profile/{memberId}")
    public String profile(@LoginMember Member loginMember, @PathVariable String memberId, Model model) {
        Member profileMember = memberService.findByLoginId(memberId);
        model.addAttribute("profileMember", profileMember);
        return "profile";
    }


//    @PostMapping("/member/{memberLoginId}/set-thumbnail")
//    public void setThumbnail(@LoginMember Member member,
//                             @PathVariable String memberLoginId,
//                             @RequestParam MultipartFile memberThumbnail,
//                             HttpServletRequest request,
//                             HttpServletResponse response) throws IOException {
//        String fileDir = "/asset/memberThumbnail/";
//        log.info("MemberController.setThumbnail");
//
//        log.info("memberThumbnail = " + memberThumbnail);
//
//        String referer = request.getHeader("Referer");
//        String storeFileName = UUID.randomUUID().toString() + memberThumbnail.getOriginalFilename();
//        String filePath = fileDir + storeFileName;
//        String pathname = System.getProperty("user.dir") + "/src/main/resources/static" + filePath;
//        memberThumbnail.transferTo(new File(pathname));
//        memberService.updateUserThumbnail(memberLoginId, filePath);
//
//        ScriptUtils.alertAndMovePage(response ,"유저 이미지 업로드 성공", referer);
//    }
    @PostMapping("/member/{memberLoginId}/set-description")
    public String setDescription(@LoginMember Member member,
                             @PathVariable String memberLoginId,
                             @RequestParam String description,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        memberService.updateUserDescription(memberLoginId, description);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @PostMapping("/member/delete/{memberId}")
    public void post_deleteMember(@LoginMember Member loginMember, @PathVariable Long memberId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("MemberController.post_deleteMember");
        if (!loginMember.getId() .equals( memberId)) {
            // no authorized,,
            throw new UnauthorizedException();
        }
        Long deleteId = memberService.delete(memberId);
        log.info("deleteId = " + deleteId);
        log.info("delete success");
        request.getSession(false).invalidate();

        ScriptUtils.alertAndMovePage(response,"회원 탈퇴 성공", "/");
    //        return "redirect:/";
    }
}

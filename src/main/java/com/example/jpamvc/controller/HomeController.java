package com.example.jpamvc.controller;

import com.example.jpamvc.domain.Member;
import com.example.jpamvc.service.PostService;
import com.example.jpamvc.web.argumentResolver.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final PostService postService;

    @GetMapping("/")
    public String home(@LoginMember Member member,
                       Model model,
                       @RequestParam(required = false, defaultValue = "1") int page,
                       @RequestParam(required = false, defaultValue = "10") int limit,
                       @RequestParam(name = "q", required = false, defaultValue = "") String searchQuery
                       ) {
        log.info("HomeController.home");
        model.addAttribute("member", member);
        model.addAttribute("posts", postService.list(page, limit, searchQuery));
        long count = postService.list(searchQuery).stream().count();
        model.addAttribute("totalPageNum", count / limit + (count % limit != 0  ? 1 : 0));
        model.addAttribute("page", page);
        model.addAttribute("limit", limit);
        model.addAttribute("q", searchQuery);

        log.info("member = " + member + ", model = " + model + ", page = " + page + ", limit = " + limit);
        return "home";
    }

}

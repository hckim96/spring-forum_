package com.example.jpamvc.controller;

import com.example.jpamvc.domain.Member;
import com.example.jpamvc.domain.Post;
import com.example.jpamvc.dto.PostEditDto;
import com.example.jpamvc.dto.PostWriteDto;
import com.example.jpamvc.exception.BadRequestException;
import com.example.jpamvc.exception.UnauthorizedException;
import com.example.jpamvc.myUtil.ScriptUtils;
import com.example.jpamvc.service.PostService;
import com.example.jpamvc.web.argumentResolver.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;
    @GetMapping("/write")
    public String write(@LoginMember Member loginMember, Model model) {
        model.addAttribute("postWriteDto", new PostWriteDto());
        return "writePost";
    }

    @GetMapping("/{postId}")
    public String post(@LoginMember Member member, @PathVariable Long postId, Model model) {
        Post post = postService.viewOnePost(postId);
        if (post == null) {
            throw new BadRequestException();
        }
        Collections.reverse(post.getComments());
        model.addAttribute("post", post);
        return "postView";
    }
    @PostMapping("/delete/{postId}")
    public String post_deletePost(@LoginMember Member member, @PathVariable Long postId, Model model, HttpServletResponse response) throws IOException {
        log.info("PostController.post_deletePost");
        Post post = postService.findOneById(postId);
        if (!post.getAuthor().getId() .equals( member.getId())) {
            // no authorized,,
            log.error("!post.getAuthor().getId() .equals( member.getId())");
            throw new UnauthorizedException();
        }

        Long deleteId = postService.delete(postId);
        log.info("deleteId = " + deleteId);
        log.info("delete success");
        ScriptUtils.alertAndMovePage(response,"글 삭제 성공", "/");
        return "redirect:/";
    }

    @PostMapping("/write")
    public String post_write(@ModelAttribute PostWriteDto postWriteDto,
                             @LoginMember Member member,
                             HttpServletRequest request) {
        log.info("postWriteDto = " + postWriteDto + ", member = " + member + ", request = " + request);

        Long postId = postService.write(postWriteDto);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/edit/{postId}")
    public String edit(@LoginMember Member member, @PathVariable Long postId, Model model) {
        Post post = postService.findOneById(postId);
        if (!post.getAuthor().getId() .equals( member.getId())) {
            // no authorized,,
            log.error("!post.getAuthor().getId() .equals( member.getId())");
            throw new UnauthorizedException();
        }

        model.addAttribute("postEditDto", new PostEditDto(post.getTitle(), post.getBody()));
        return "editPost";
    }
    @PostMapping("/edit/{postId}")
    public String post_edit(@LoginMember Member member, @PathVariable Long postId, @Validated @ModelAttribute PostEditDto postEditDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editPost";
        }
        Post post = postService.findOneById(postId);
        if (post.getAuthor().getId() .equals( member.getId())) {
            // no authorized,,
            log.error("post.getAuthor().getId() != member.getId()");
            throw new UnauthorizedException();
        }

        Long editId = postService.editPost(postId, postEditDto);
        if (editId == null) {
            throw new RuntimeException("editId == null");
        }

        return "redirect:/post/" + postId;
    }
}

package com.example.jpamvc.controller;

import com.example.jpamvc.domain.Comment;
import com.example.jpamvc.domain.Member;
import com.example.jpamvc.domain.Post;
import com.example.jpamvc.dto.CommentWriteDto;
import com.example.jpamvc.exception.UnauthorizedException;
import com.example.jpamvc.myUtil.ScriptUtils;
import com.example.jpamvc.service.CommentService;
import com.example.jpamvc.web.argumentResolver.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
@Controller
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/write")
    public String commentWrite(@LoginMember LoginMember loginMember, @ModelAttribute CommentWriteDto commentWriteDto) {
        log.info("loginMember = " + loginMember + ", commentWriteDto = " + commentWriteDto);
        commentService.write(commentWriteDto);
        return "redirect:/post/" + commentWriteDto.getPostId();
    }

    @PostMapping("/delete/{commentId}")
    public void post_deleteComment(@LoginMember Member member, @PathVariable Long commentId, HttpServletResponse response) throws IOException {
        log.info("CommentController.post_deleteComment");

        Comment comment = commentService.findOneById(commentId);
        if (!comment.getAuthor().getId() .equals( member.getId())) {
            // no authorized,,
            log.error("!comment.getAuthor().getId() .equals( member.getId())");
            throw new UnauthorizedException();
        }

        Long deleteId = commentService.delete(commentId);
        log.info("deleteId = " + deleteId);
        log.info("delete success");
        ScriptUtils.alertAndMovePage(response,"댓글 삭제 성공", "/");
//        return "redirect:/";
    }
}

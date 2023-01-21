package com.example.jpamvc.service;

import com.example.jpamvc.domain.Comment;
import com.example.jpamvc.domain.Member;
import com.example.jpamvc.domain.Post;
import com.example.jpamvc.dto.CommentWriteDto;
import com.example.jpamvc.repository.CommentRepository;
import com.example.jpamvc.repository.MemberRepository;
import com.example.jpamvc.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void write(CommentWriteDto commentWriteDto) {

        Post post = postRepository.findById(commentWriteDto.getPostId());
        Member member = memberRepository.findById(commentWriteDto.getAuthorId());

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(member);
        comment.setBody(commentWriteDto.getBody());

        commentRepository.save(comment);
    }

    public Comment findOneById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public Long delete(Long commentId) {
        return commentRepository.delete(commentId);
    }
}

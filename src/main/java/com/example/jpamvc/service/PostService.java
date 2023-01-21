package com.example.jpamvc.service;

import com.example.jpamvc.domain.Member;
import com.example.jpamvc.domain.Post;
import com.example.jpamvc.dto.PostEditDto;
import com.example.jpamvc.dto.PostWriteDto;
import com.example.jpamvc.repository.MemberRepository;
import com.example.jpamvc.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostService {
   private final PostRepository postRepository;
   private final MemberRepository memberRepository;

   public List<Post> list() {
       return postRepository.findAll();
   }


    public Long writeNewPost(Post post) {
        log.info("PostService.writeNewPost");
        log.info("post = " + post);
        Long saveId = postRepository.save(post);
        log.info("saveId = " + saveId);
        return saveId;
    }

    public Post findOneById(Long postId) {
       return postRepository.findById(postId);
    }

    public Long editPost(Long postId, PostEditDto postEditDto) {
        Post post = postRepository.findById(postId);
        post.setTitle(postEditDto.getTitle());
        post.setBody((postEditDto.getBody()));
        return post.getId();
    }

    public Long delete(Long postId) {
        Post post = postRepository.findById(postId);
        if (post == null) return null;

        return postRepository.remove(post);
    }

    public List<Post> list(int page, int limit) {
       return postRepository.findWithPagination(page, limit);
    }


    // increase view count and get post
    public Post viewOnePost(Long postId) {
       return postRepository.findOneAndIncreaseView(postId);
    }

    public List<Post> list(int page, int limit, String searchQuery) {
        return postRepository.findWithPaginationAndQuery(page, limit, searchQuery);
    }

    public List<Post> list(String searchQuery) {
       return postRepository.findAllWithSearchQuery(searchQuery);
    }

    public Long write(PostWriteDto postWriteDto) {
        log.info("postWriteDto = " + postWriteDto);
        Member author = memberRepository.findById(postWriteDto.getAuthorId());
        Post post = new Post();
        post.setAuthor(author);
        post.setTitle(postWriteDto.getTitle());
        post.setBody(postWriteDto.getBody());
        return postRepository.save(post);
    }
}

package com.example.jpamvc.repository;

import com.example.jpamvc.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p").getResultList();
    }
    public List<Post> findAllFetch() {
        return em.createQuery("select p from Post p").getResultList();
    }

    public Post findById(Long postId) {
        return em.find(Post.class, postId);
    }

    public Long remove(Post post) {
        em.remove(post);
        return post.getId();
    }

    public List<Post> findWithPagination(int page, int limit) {
        return em.createQuery("select p from Post p order by p.createdAt desc")
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public Post findOneAndIncreaseView(Long postId) {
        Post post = em.find(Post.class, postId);
        if (post == null) return null;
        post.setView(post.getView() + 1);
        return post;
    }

    public List<Post> findWithPaginationAndQuery(int page, int limit, String searchQuery) {
        return em.createQuery("select p from Post p where p.title like :q order by p.createdAt desc")
                .setParameter("q", '%' + searchQuery + '%')
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Post> findAllWithSearchQuery(String searchQuery) {
        return em.createQuery("select p from Post p where p.title like :q")
                .setParameter("q", '%' + searchQuery + '%')
                .getResultList();
    }
}

package com.example.jpamvc.repository;

import com.example.jpamvc.domain.Member;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member) {
        member.setPassword(BCrypt.hashpw(member.getPassword(), BCrypt.gensalt()));
        em.persist(member);
        return member.getId();
    }

    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList()
                .stream()
                .findAny();
    }

    public void updateUserThumbnail(String memberLoginId, String filePath) {
        findByLoginId(memberLoginId).get().setThumbnail(filePath);
    }

    public void updateUserDescription(String memberLoginId, String description) {
        findByLoginId(memberLoginId).get().setDescription(description);
    }

    public Long delete(Long memberId) {
        Member member = findById(memberId);
        em.remove(member);
        return memberId;
    }
}

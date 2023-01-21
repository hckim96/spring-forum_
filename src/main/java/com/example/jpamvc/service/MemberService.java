package com.example.jpamvc.service;

import com.example.jpamvc.domain.Member;
import com.example.jpamvc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {
    public final MemberRepository memberRepository;

    public Long join(Member member) {

        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> byLoginId = memberRepository.findByLoginId(member.getLoginId());
        if (byLoginId.isPresent()) throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    public void login() {

    }

    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> BCrypt.checkpw(password, m.getPassword()))
                .orElse(null);
    }

    public Member findByLoginId(String memberId) {
        return memberRepository.findByLoginId(memberId).orElse(null);
    }

    public void updateUserThumbnail(String memberLoginId, String filePath) {
        memberRepository.updateUserThumbnail(memberLoginId, filePath);
    }

    public void updateUserDescription(String memberLoginId, String description) {
        memberRepository.updateUserDescription(memberLoginId, description);
    }

    public Member findOneById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Long delete(Long memberId) {
        return memberRepository.delete(memberId);
    }
}

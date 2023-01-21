//package com.example.jpamvc.domain;
//
//import com.example.jpamvc.repository.MemberRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
////@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class MemberRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    void save() {
//    }
//
//    @Test
//    @Transactional
//    @Rollback(false)
//    void find() {
//        Member member = new Member();
//        member.setUsername("hello1");
//        Long saveId = memberRepository.save(member);
//
//        Member findMember = memberRepository.findById(saveId);
//        Assertions.assertThat(findMember.getId()).isEqualTo(saveId);
//        Assertions.assertThat(findMember.getUsername()).isEqualTo("hello1");
//    }
//}
package com.example.jpamvc;


import com.example.jpamvc.domain.Member;
import com.example.jpamvc.domain.Post;
import com.example.jpamvc.dto.CommentWriteDto;
import com.example.jpamvc.dto.MemberSignupDto;
import com.example.jpamvc.dto.PostWriteDto;
import com.example.jpamvc.service.CommentService;
import com.example.jpamvc.service.MemberService;
import com.example.jpamvc.service.PostService;
import com.example.jpamvc.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitApp {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
//        initService.login();


        log.info("InitApp.init [DONE]");
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final MemberService memberService;
        private final PostService postService;
        private final CommentService commentService;

        public void login() {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            Member member = memberService.login("admin", "1234");
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        }
        public void dbInit() {
            Random random = new Random();
            random.setSeed(System.currentTimeMillis());

            List<MemberSignupDto> memberSignupDtos = List.of(
                    new MemberSignupDto("admin", "1234")
//                    ,new MemberSignupDto("Ander", "1234")
                    ,new MemberSignupDto("Breert", "1234")
                    ,new MemberSignupDto("Czaae", "1234")
                    ,new MemberSignupDto("Dealaty", "1234")
//                    ,new MemberSignupDto("Dutiera", "1234")
//                    ,new MemberSignupDto("HeShay", "1234")
//                    ,new MemberSignupDto("ernso", "1234")
//                    ,new MemberSignupDto("JoGet", "1234")
//                    ,new MemberSignupDto("Khme", "1234")
//                    ,new MemberSignupDto("Liash", "1234")
//                    ,new MemberSignupDto("NateD", "1234")
//                    ,new MemberSignupDto("Ople", "1234")
//                    ,new MemberSignupDto("Phmed", "1234")
//                    ,new MemberSignupDto("Rangic", "1234")
//                    ,new MemberSignupDto("Ser", "1234")
//                    ,new MemberSignupDto("Talll", "1234")
//                    ,new MemberSignupDto("Varoud", "1234")
//                    ,new MemberSignupDto("Wnost", "1234")
            );

            List<Long> memberIdList = new ArrayList<>();
            for (MemberSignupDto memberSignupDto : memberSignupDtos) {
                Member member = new Member();
                member.setLoginId(memberSignupDto.getLoginId());
                member.setPassword(memberSignupDto.getPassword());
                memberIdList.add(memberService.join(member));
            }

            List<PostWriteDto> postWriteDtos = List.of(
                    new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "11 Signs a Cat Revolution Is Coming", "Wondering How To Make Your CAT Rock? Read This!")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "Learn Exactly How I Improved CAT In 2 Days", "At Last, The Secret To CAT Is Revealed")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "Don't Fall For This CAT Scam", "At Last, The Secret To CAT Is Revealed")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "How To Handle Every CAT Challenge With Ease Using These Tips", "At Last, The Secret To CAT Is Revealed")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "Why My CAT Is Better Than Yours", "At Last, The Secret To CAT Is Revealed")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "How To Turn CAT Into Success", "At Last, The Secret To CAT Is Revealed")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "Now You Can Buy An App That is Really Made For CAT", "At Last, The Secret To CAT Is Revealed")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "Here Is What You Should Do For Your CAT", "At Last, The Secret To CAT Is Revealed")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "How I Improved My CAT In One Day", "At Last, The Secret To CAT Is Revealed")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "Learn Exactly How We Made CAT Last Month", "At Last, The Secret To CAT Is Revealed")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "Meet the Incredible, Unsung Heros of Cats", "At Last, The Secret To CAT Is Revealed")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "The History of Cats in Under 10 Minutes", "At Last, The Secret To CAT Is Revealed")
                    ,new PostWriteDto(memberIdList.get( random.nextInt(memberIdList.size())), "Cats Is Out. Here’s What’s In", "At Last, The Secret To CAT Is Revealed")
            );

            List<Long> postIdList = new ArrayList<>();
            for (PostWriteDto postWriteDto : postWriteDtos) {
                postIdList.add(postService.write(postWriteDto));
            }

            List<CommentWriteDto> commentWriteDtos = List.of(
                    new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "Learn Exactly How We Made CAT Last Month")
                    ,new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "Learn Exactly How We Made CAT Last Month")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "15 Lessons About CAT You Need To Learn To Succeed")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "15 Lessons About CAT You Need To Learn To Succeed")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "15 Lessons About CAT You Need To Learn To Succeed")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "15 Lessons About CAT You Need To Learn To Succeed")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
                    ,new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "Learn Exactly How We Made CAT Last Month")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "15 Lessons About CAT You Need To Learn To Succeed")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "15 Lessons About CAT You Need To Learn To Succeed")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "How You Can (Do) CAT Almost Instantly")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "7 Rules About CAT Meant To Be Broken")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "15 Lessons About CAT You Need To Learn To Succeed")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "15 Lessons About CAT You Need To Learn To Succeed")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
                    , new CommentWriteDto(postIdList.get(random.nextInt(postIdList.size())), memberIdList.get(random.nextInt(memberIdList.size())), "CAT Expert Interview")
            );

            for (CommentWriteDto commentWriteDto : commentWriteDtos) {
                commentService.write(commentWriteDto);
            }



//            List<Member> members = new ArrayList<>();
//            for (int i = 0; i < 2; i++) {
//                String username = "admin" + (i == 0 ? "" : i);
//                String password = "1234";
//                Member member = new Member();
//                member.setLoginId(username);
//                member.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
//                members.add(member);
//                em.persist(member);
//            }
//
//
//            List<Post> posts = new ArrayList<>();
//            int postCnt = 13;
//            for (int i = 0; i < postCnt; i++) {
//                Post post = new Post();
//                post.setAuthor(members.get(i % members.size()));
//                post.setTitle("auto generated post" + i);
//                post.setView(0);
//                post.setBody("auto generated post" + i  + " body");
//                posts.add(post);
//                em.persist(post);
//            }

        }

    }
}

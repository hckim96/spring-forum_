package com.example.jpamvc;

import com.example.jpamvc.domain.Member;
import com.example.jpamvc.domain.Post;
import com.example.jpamvc.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
@Slf4j
public class JpamvcApplication {
    private final EntityManager em;

    public static void main(String[] args) {
        SpringApplication.run(JpamvcApplication.class, args);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void initData() {
//        log.info("JpamvcApplication.initData()");
//
//        log.info("JpamvcApplication.initData() [DONE]");
//    }
}

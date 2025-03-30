package mjc.capstone.joinus.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDataService {

    private final EntityManager em;

    @Transactional
    public void init() {
        Member member = new Member();
        member.setNickname("홍길동");
        member.setMail("hong@test.com");
        member.setPassword("123456");
        member.setUsername("Hong");

        em.persist(member);
    }
}

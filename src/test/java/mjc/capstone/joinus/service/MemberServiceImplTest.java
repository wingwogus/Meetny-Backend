package mjc.capstone.joinus.service;

import mjc.capstone.joinus.domain.Address;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

//    @Autowired
//    MemberServiceImpl memberService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .username("wingwogus")
                .address(new Address("dd", "dd", "dd"))
                .nickname("거인이재현")
                .build();

        memberRepository.save(member);
    }
}
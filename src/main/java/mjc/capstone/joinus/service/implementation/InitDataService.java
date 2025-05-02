package mjc.capstone.joinus.service.implementation;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Address;
import mjc.capstone.joinus.domain.entity.Gender;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Role;
import mjc.capstone.joinus.domain.tags.*;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.repository.TagRepository;
import mjc.capstone.joinus.repository.UserTagRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDataService {

    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final UserTagRepository userTagRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .username("mih2001103")
                .nickname("monikhyun")
                .phone("010-0000-0000")
                .mail("mih2001103@gmail.com")
                .address(Address.builder()
                        .city("서울특별시")
                        .street("송파구")
                        .town("방이동")
                        .build())
                .gender(Gender.Male)
                .role(Role.USER)
                .profileImg("https://ui-avatars.com/api/?name=Jae+Hyun&background=random")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberRepository.save(member);

        List<Tag> tags = List.of(
                new Concert("락", "#FF5733"),
                new Concert("발라드", "#33C1FF"),
                new Concert("랩", "#FF33A8"),
                new Exhibition("전시회", "#9D33FF"),
                new Exhibition("박람회", "#33FFBD"),
                new Movie("공포", "#FF3333"),
                new Movie("사극", "#FFBD33"),
                new Sports("야구", "#33FF57"),
                new Sports("축구", "#335BFF"),
                new Sports("농구", "#FF8C33")
        );
        tagRepository.saveAll(tags);


        List<Tag> selectedTags = List.of(tags.get(0), tags.get(1), tags.get(3), tags.get(5), tags.get(7)); // 락, 발라드, 전시회, 공포, 야구
        for (Tag tag : selectedTags) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }
    }

}


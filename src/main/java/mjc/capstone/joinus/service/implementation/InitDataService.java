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
                .gender(Gender.MALE)
                .role(Role.USER)
                .profileImg("https://ui-avatars.com/api/?name=Jae+Hyun&background=random")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberRepository.save(member);

        List<Tag> selectedTags = List.of(new Concert("락", "#FF5733"),
                new Concert("발라드", "#33C1FF"),
                new Exhibition("전시회", "#9D33FF"),
                new Exhibition("박람회", "#33FFBD"),
                new Movie("공포", "#FF3333"),
                new Sports("농구", "#FF8C33")); // 락, 발라드, 전시회, 공포, 야구

        tagRepository.saveAll(selectedTags);

        for (Tag tag : selectedTags) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }

        Member member1 = Member.builder()
                .username("user1")
                .nickname("user1")
                .phone("010-0000-0000")
                .mail("user1@gmail.com")
                .address(Address.builder()
                        .city("서울특별시")
                        .street("송파구")
                        .town("방이동")
                        .build())
                .gender(Gender.MALE)
                .role(Role.USER)
                .profileImg("https://ui-avatars.com/api/?name=Jae+Hyun&background=random")
                .password(passwordEncoder.encode("1234"))
                .build();

        Member member2 = Member.builder()
                .username("user2")
                .nickname("user2")
                .phone("010-0000-0000")
                .mail("user2@gmail.com")
                .address(Address.builder()
                        .city("서울특별시")
                        .street("은평구")
                        .town("응암동")
                        .build())
                .gender(Gender.MALE)
                .role(Role.USER)
                .profileImg("https://ui-avatars.com/api/?name=Jae+Hyun&background=random")
                .password(passwordEncoder.encode("1234"))
                .build();

        Member member3 = Member.builder()
                .username("user3")
                .nickname("user3")
                .phone("010-0000-0000")
                .mail("user3@gmail.com")
                .address(Address.builder()
                        .city("서울특별시")
                        .street("서대문구")
                        .town("홍제동")
                        .build())
                .gender(Gender.MALE)
                .role(Role.USER)
                .profileImg("https://ui-avatars.com/api/?name=Jae+Hyun&background=random")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);


        List<Tag> tag1 = List.of(
                new Concert("락", "#FF5733"),
                new Exhibition("전시회", "#9D33FF"));

        tagRepository.saveAll(tag1);

        for (Tag tag : tag1) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member1);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }

        List<Tag> tag2 = List.of(
                new Movie("공포", "#FF3333"),
                new Sports("야구", "#33FF57"));

        tagRepository.saveAll(tag2);

        for (Tag tag : tag2) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member2);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }

        List<Tag> tag3 = List.of(
                new Concert("랩", "#FF33A8"),
                new Sports("축구", "#335BFF"));

        tagRepository.saveAll(tag3);

        for (Tag tag : tag3) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member3);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }

// Sample Tag
/*
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
    } */
    }
}


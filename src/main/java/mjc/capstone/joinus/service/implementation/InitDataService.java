package mjc.capstone.joinus.service.implementation;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.*;
import mjc.capstone.joinus.domain.review.ReviewTag;
import mjc.capstone.joinus.domain.review.ReviewTagType;
import mjc.capstone.joinus.domain.tags.*;
import mjc.capstone.joinus.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDataService {
    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final UserTagRepository userTagRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReviewTagRepository reviewTagRepository;
    private final PostRepository postRepository;

    @PostConstruct
    public void init() {
        ReviewTag goodManner = new ReviewTag("친절하고 매너가 좋아요", ReviewTagType.POSITIVE);
        ReviewTag punctual = new ReviewTag("시간 약속을 잘 지켜요", ReviewTagType.POSITIVE);
        ReviewTag goodCommunication = new ReviewTag("소통이 원활해요", ReviewTagType.POSITIVE);
        ReviewTag considerate = new ReviewTag("배려심이 깊어요", ReviewTagType.POSITIVE);

        ReviewTag unpunctual = new ReviewTag("시간 약속을 지키지 않아 아쉬웠어요", ReviewTagType.NEGATIVE);
        ReviewTag badCommunication = new ReviewTag("소통이 원활하지 않았어요", ReviewTagType.NEGATIVE);
        ReviewTag verbalAbuse = new ReviewTag("폭언 및 욕설을 해요", ReviewTagType.NEGATIVE);
        ReviewTag inappropriateTalk = new ReviewTag("불편한 주제로 대화를 해요", ReviewTagType.NEGATIVE);

        List<ReviewTag> reviewTags = List.of(
                goodManner, punctual, goodCommunication, considerate, unpunctual, badCommunication, verbalAbuse, inappropriateTalk
        );

        for (
                ReviewTag reviewTag : reviewTags) {
            reviewTagRepository.save(reviewTag);
        }


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
                .credibility(45.0)
                .profileImg("https://ui-avatars.com/api/?name=Jae+Hyun&background=random")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberRepository.save(member);

        List<Tag> selectedTags = List.of(new Concert("락", "#FF5733"),
                new Concert("발라드", "#33C1FF"),
                new Culture("전시회", "#9D33FF"),
                new Culture("박람회", "#33FFBD"),
                new Movie("공포", "#FF3333"),
                new Sports("농구", "#FF8C33")); // 락, 발라드, 전시회, 공포, 야구

        tagRepository.saveAll(selectedTags);

        for (
                Tag tag : selectedTags) {
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
                .credibility(45.0)
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
                .credibility(45.0)
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
                .credibility(45.0)
                .profileImg("https://ui-avatars.com/api/?name=Jae+Hyun&background=random")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);


        List<Tag> tag1 = List.of(
                new Concert("락", "#FF5733"),
                new Culture("전시회", "#9D33FF"));

        tagRepository.saveAll(tag1);

        for (
                Tag tag : tag1) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member1);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }

        List<Tag> tag2 = List.of(
                new Movie("공포", "#FF3333"),
                new Sports("야구", "#33FF57"));

        tagRepository.saveAll(tag2);

        for (
                Tag tag : tag2) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member2);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }

        List<Tag> tag3 = List.of(
                new Concert("랩", "#FF33A8"),
                new Sports("축구", "#335BFF"));

        tagRepository.saveAll(tag3);

        for (
                Tag tag : tag3) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member3);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }

        Post post = Post.builder()
                .title("테스트 동행 모집")
                .content("테스트용으로 만든 게시글입니다. 시간 맞는 분 함께해요.")
                .photo(null)
                .meetingTime(LocalDateTime.now().plusDays(2))
                .address(member.getAddress())
                .tag(tag1.getFirst())
                .build();
        member2.setPosts(new ArrayList<>());
        post.setAuthor(member2); // 연관관계 메소드
        post.setParticipant(member1);
        postRepository.save(post);
    }
}
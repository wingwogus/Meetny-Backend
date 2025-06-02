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
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitDataService {
    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final UserTagRepository userTagRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    public void init() {

        Concert jpopTag = new Concert("J-POP", "#FF5733");
        Concert rockTag = new Concert("락", "#33C1FF");
        Concert balladeTag = new Concert("발라드", "#75FF33");
        Concert hiphopTag = new Concert("힙합", "#FF33EC");
        Concert kpopTag = new Concert("K-POP", "#FFBD33");
        Concert edmTag = new Concert("EDM", "#8D33FF");
        Concert popTag = new Concert("POP", "#33FFBD");
        Concert trotTag = new Concert("트로트", "#FF3333");
        Concert jazzTag = new Concert("JAZZ", "#33C1FF");

        Culture museumTag = new Culture("박물관", "#75FF33");
        Culture galleryTag = new Culture("미술관", "#FF33EC");
        Culture popupTag = new Culture("팝업", "#FFBD33");
        Culture expoTag = new Culture("박람회", "#8D33FF");
        Culture contestTag = new Culture("콘테스트", "#33FFBD");
        Culture exhibitionTag = new Culture("전시회", "#FF5733");
        Culture musicalTag = new Culture("뮤지컬", "#FF3333");
        Culture playTag = new Culture("연극", "#33C1FF");
        Culture comedyShowTag = new Culture("코미디 쇼", "#75FF33");

        Sports soccerTag = new Sports("축구", "#FF33EC");
        Sports baseballTag = new Sports("야구", "#FFBD33");
        Sports basketballTag = new Sports("농구", "#8D33FF");
        Sports volleyballTag = new Sports("배구", "#33FFBD");

        Movie actionTag = new Movie("액션", "#FF3333");
        Movie romanceTag = new Movie("로맨스", "#33C1FF");
        Movie comedyTag = new Movie("코미디", "#75FF33");
        Movie thrillerTag = new Movie("스릴러/호러", "#FF33EC");
        Movie fantasyTag = new Movie("판타지/SF", "#FFBD33");
        Movie documentaryTag = new Movie("다큐멘터리", "#8D33FF");
        Movie animationTag = new Movie("애니메이션", "#33FFBD");
        Movie musicalMovieTag = new Movie("뮤지컬", "#FF5733");
        Movie dramaTag = new Movie("드라마", "#FF3333");

        List<Tag> selectedTags = List.of(
                jpopTag, rockTag, balladeTag, hiphopTag, kpopTag, edmTag, popTag, trotTag, jazzTag,
                museumTag, galleryTag, popupTag, expoTag, contestTag, exhibitionTag, musicalTag, playTag, comedyShowTag,
                soccerTag, baseballTag, basketballTag, volleyballTag,
                actionTag, romanceTag, comedyTag, thrillerTag, fantasyTag, documentaryTag, animationTag, musicalMovieTag, dramaTag
        );

        tagRepository.saveAll(selectedTags);

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
                .username("mih2001103@naver.com")
                .nickname("monikhyun")
                .phone("010-0000-0000")
                .address(Address.builder()
                        .city("서울특별시")
                        .street("송파구")
                        .town("방이동")
                        .build())
                .gender(Gender.MALE)
                .credibility(45.0)
                .role(Role.USER)
                .profileImg("https://ui-avatars.com/api/?name=Jae+Hyun&background=random")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberRepository.save(member);


        for (Tag tag : selectedTags) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }

        Member member1 = Member.builder()
                .username("user1@naver.com")
                .nickname("user1")
                .phone("010-0000-0000")
                .address(Address.builder()
                        .city("서울특별시")
                        .street("송파구")
                        .town("방이동")
                        .build())
                .gender(Gender.MALE)
                .credibility(45.0)
                .role(Role.USER)
                .profileImg("https://ui-avatars.com/api/?name=Jae+Hyun&background=random")
                .password(passwordEncoder.encode("1234"))
                .build();

        Member member2 = Member.builder()
                .username("user2@naver.com")
                .nickname("user2")
                .phone("010-0000-0000")
                .address(Address.builder()
                        .city("서울특별시")
                        .street("은평구")
                        .town("응암동")
                        .build())
                .gender(Gender.MALE)
                .credibility(45.0)
                .role(Role.USER)
                .profileImg("https://ui-avatars.com/api/?name=Jae+Hyun&background=random")
                .password(passwordEncoder.encode("1234"))
                .build();

        Member member3 = Member.builder()
                .username("user3@naver.com")
                .nickname("user3")
                .phone("010-0000-0000")
                .address(Address.builder()
                        .city("서울특별시")
                        .street("서대문구")
                        .town("홍제동")
                        .build())
                .gender(Gender.MALE)
                .credibility(45.0)
                .role(Role.USER)
                .profileImg("https://ui-avatars.com/api/?name=Jae+Hyun&background=random")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);


        List<Tag> tag1 = List.of(
                rockTag,
                exhibitionTag);

        for (Tag tag : tag1) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member1);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }

        List<Tag> tag2 = List.of(
                hiphopTag,
                balladeTag);

        for (Tag tag : tag2) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member2);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }

        List<Tag> tag3 = List.of(
                musicalTag,
                soccerTag);

        for (Tag tag : tag3) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member3);
            memberTag.setTags(tag);
            userTagRepository.save(memberTag);
        }

        Post post1 = Post.builder()
                .author(member1)
                .title("토트넘 내한 동행 구인")
                .content("토트넘 내한 동행 구해용")
                .tag(soccerTag)
                .meetingTime(LocalDateTime.now().plusDays(3))
                .photo("url/dummyImg")
                .address(
                        Address.builder()
                                .city("서울시")
                                .town("마포구")
                                .street("월드컵로 240")
                                .build())
                .participant(member2)
                .build();

        postRepository.save(post1);

        Post post2 = Post.builder()
                .author(member2)
                .title("상상용 내한 동행 구함")
                .content("여자만 받아요")
                .tag(rockTag)
                .meetingTime(LocalDateTime.now().plusDays(10))
                .photo("url/dummyImg")
                .address(
                        Address.builder()
                                .city("서울시")
                                .town("마포구")
                                .street("월드컵로 240")
                                .build())
                .participant(member3)
                .build();

        postRepository.save(post2);

        ChatRoom room1 = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .post(post1)
                .member(member2)
                .build();

        ChatRoom room2 = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .post(post2)
                .member(member1)
                .build();

        chatRoomRepository.save(room1);
        chatRoomRepository.save(room2);
    }
}
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
    private final FollowRepository followRepository;

    @PostConstruct
    public void init() {

        Concert jpopTag = new Concert("J-POP", "#AED73D");
        Concert rockTag = new Concert("락", "#AED73D");
        Concert balladeTag = new Concert("발라드", "#AED73D");
        Concert hiphopTag = new Concert("힙합", "#AED73D");
        Concert kpopTag = new Concert("K-POP", "#AED73D");
        Concert edmTag = new Concert("EDM", "#AED73D");
        Concert popTag = new Concert("POP", "#AED73D");
        Concert trotTag = new Concert("트로트", "#AED73D");
        Concert jazzTag = new Concert("JAZZ", "#AED73D");

        Culture museumTag = new Culture("박물관", "#9D9DFF");
        Culture galleryTag = new Culture("미술관", "#9D9DFF");
        Culture popupTag = new Culture("팝업", "#9D9DFF");
        Culture expoTag = new Culture("박람회", "#9D9DFF");
        Culture contestTag = new Culture("콘테스트", "#9D9DFF");
        Culture exhibitionTag = new Culture("전시회", "#9D9DFF");
        Culture musicalTag = new Culture("뮤지컬", "#9D9DFF");
        Culture playTag = new Culture("연극", "#9D9DFF");
        Culture comedyShowTag = new Culture("코미디 쇼", "#9D9DFF");

        Sports soccerTag = new Sports("축구", "#F28A5D");
        Sports baseballTag = new Sports("야구", "#F28A5D");
        Sports basketballTag = new Sports("농구", "#F28A5D");
        Sports volleyballTag = new Sports("배구", "#F28A5D");

        Movie actionTag = new Movie("액션", "#FA76D0");
        Movie romanceTag = new Movie("로맨스", "#FA76D0");
        Movie comedyTag = new Movie("코미디", "#FA76D0");
        Movie thrillerTag = new Movie("스릴러/호러", "#FA76D0");
        Movie fantasyTag = new Movie("판타지/SF", "#FA76D0");
        Movie documentaryTag = new Movie("다큐멘터리", "#FA76D0");
        Movie animationTag = new Movie("애니메이션", "#FA76D0");
        Movie musicalMovieTag = new Movie("뮤지컬", "#FA76D0");
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

        ReviewTag unpunctual = new ReviewTag("시간 약속을 지키지 않아요", ReviewTagType.NEGATIVE);
        ReviewTag badCommunication = new ReviewTag("소통이 원활하지 않았어요", ReviewTagType.NEGATIVE);
        ReviewTag verbalAbuse = new ReviewTag("폭언 및 욕설을 해요", ReviewTagType.NEGATIVE);
        ReviewTag inappropriateTalk = new ReviewTag("불편한 주제로 대화를 해요", ReviewTagType.NEGATIVE);

        List<ReviewTag> reviewTags = List.of(
                goodManner, punctual, goodCommunication, considerate, unpunctual, badCommunication, verbalAbuse, inappropriateTalk
        );

        for (
                ReviewTag reviewTag : reviewTags) {
            if (!reviewTagRepository.existsByTagName(reviewTag.getTagName())) {
                reviewTagRepository.save(reviewTag);
            }
        }


        Member member = Member.builder()
                .username("mih200113@naver.com")
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
                .profileImg("https://picsum.photos/200/300")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberRepository.save(member);

// 이미 저장한 member 이후에 추가
// 카테고리별 2개씩 태그 연결
        List<Tag> memberTags = List.of(
                jpopTag, rockTag,         // Concert
                museumTag, galleryTag,    // Culture
                soccerTag, baseballTag,   // Sports
                actionTag, romanceTag     // Movie
        );

// MemberTag 저장
        for (Tag tag : memberTags) {
            MemberTag memberTag = new MemberTag();
            memberTag.setMember(member); // 위에서 저장한 member 사용
            memberTag.setTags(tag);      // 이미 saveAll된 tag 객체 사용
            userTagRepository.save(memberTag);
        }

        Member member1 = Member.builder()
                .username("user1@naver.com")
                .nickname("유저1")
                .phone("010-0000-0000")
                .address(Address.builder()
                        .city("서울특별시")
                        .street("송파구")
                        .town("방이동")
                        .build())
                .gender(Gender.MALE)
                .credibility(45.0)
                .role(Role.USER)
                .profileImg("https://picsum.photos/200/300")
                .password(passwordEncoder.encode("1234"))
                .build();

        Member member2 = Member.builder()
                .username("user2@naver.com")
                .nickname("유저2")
                .phone("010-0000-0000")
                .address(Address.builder()
                        .city("서울특별시")
                        .street("은평구")
                        .town("응암동")
                        .build())
                .gender(Gender.MALE)
                .credibility(45.0)
                .role(Role.USER)
                .profileImg("https://picsum.photos/200/300")
                .password(passwordEncoder.encode("1234"))
                .build();

        Member member3 = Member.builder()
                .username("user3@naver.com")
                .nickname("유저3")
                .phone("010-0000-0000")
                .address(Address.builder()
                        .city("서울특별시")
                        .street("서대문구")
                        .town("홍제동")
                        .build())
                .gender(Gender.MALE)
                .credibility(45.0)
                .role(Role.USER)
                .profileImg("https://picsum.photos/200/300")
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
                .content("쿠팡플레이 시리즈로 내한하는 토트넘 경기 함께 보러 가실 분 구합니다!\n" +
                        "날짜는 7월 XX일, 상암 월드컵 경기장이고, 티켓은 예매 완료된 상태입니다")
                .tag(soccerTag)
                .meetingTime(LocalDateTime.now().plusDays(3))
                .photo("https://picsum.photos/250/250")
                .address(
                        Address.builder()
                                .city("서울시")
                                .town("마포구")
                                .street("월드컵로 240")
                                .build())
                .status(PostStatus.RECRUITING)
                .build();

        postRepository.save(post1);

        Post post2 = Post.builder()
                .author(member2)
                .title("이번 주말 야구 보러 가실 분")
                .content("이번 주말 잠실구장 중립석 B1, B2 예매해뒀습니다!\n" +
                        "야구 좋아하시는 분, 응원 같이 하고 맥주 한잔 하실 분 구해요 :)\n")
                .tag(baseballTag)
                .meetingTime(LocalDateTime.now().plusDays(10))
                .photo("https://picsum.photos/250/250")
                .address(
                        Address.builder()
                                .city("서울시")
                                .town("마포구")
                                .street("월드컵로 240")
                                .build())
                .status(PostStatus.RECRUITING)
                .build();

        postRepository.save(post2);

        ChatRoom room1 = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .post(post1)
                .member(member2)
                .build();

        ChatRoom room2 = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .post(post1)
                .member(member3)
                .build();

        chatRoomRepository.save(room1);
        chatRoomRepository.save(room2);

        Follow follow = new Follow();

        follow.setToMember(member1);
        follow.setFromMember(member2);

        followRepository.save(follow);

    }
}
package mjc.capstone.joinus.service.implementation;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.review.ReviewPost;
import mjc.capstone.joinus.domain.review.ReviewPostTag;
import mjc.capstone.joinus.domain.review.ReviewTag;
import mjc.capstone.joinus.dto.review.*;
import mjc.capstone.joinus.domain.review.ReviewTagType;
import mjc.capstone.joinus.exception.*;
import mjc.capstone.joinus.repository.*;
import mjc.capstone.joinus.service.inf.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewPostRepository reviewPostRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final ReviewPostTagRepository reviewPostTagRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public ReviewResponseDto createReview(Long memberId, Long postId, ReviewRequestDto dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        if (reviewPostRepository.existsByPostId(postId)) {
            throw new DuplicateReviewException();
        }

        if (!post.getParticipant().equals(member)) {
            throw new ReviewAccessDeniedException("참여하지 않은 동행에 리뷰를 작성할 수 없습니다");
        }

        List<Long> tagIds = dto.getMannerTags();
        if (tagIds == null || tagIds.isEmpty()) {
            throw new MissingReviewTagException();
        }

        if (tagIds.stream().distinct().count() != tagIds.size()) {
            throw new DuplicateReviewTagException();
        }

        Map<Long, ReviewTag> reviewTagMap = tagIds.stream()
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        tagId -> reviewTagRepository.findById(tagId)
                                .orElseThrow(ReviewTagNotFoundException::new)
                ));

        ReviewPost review = dto.toEntity(member);
        review.setPost(post);

        review.setMannerTags(
                dto.getMannerTags().stream()
                        .map(tagId -> {
                            ReviewTag reviewTag = reviewTagRepository.findById(tagId)
                                    .orElseThrow(ReviewTagNotFoundException::new);
                            return ReviewPostTag.builder()
                                    .reviewPost(review)
                                    .reviewTag(reviewTag)
                                    .build();
                        })
                        .toList()
        );

        reviewPostRepository.save(review);

        List<ReviewTagType> tagTypes = reviewTagMap.values().stream()
                .map(ReviewTag::getType)
                .toList();

        calculateCredibility(post.getAuthor(), tagTypes);

        return ReviewResponseDto.from(review);
    }

    @Override
    public void calculateCredibility(Member author, List<ReviewTagType> tagTypes) {
        double value = tagTypes.stream()
                .mapToDouble(ReviewTagType::getValue)
                .sum();

        double newCred = author.getCredibility() + value;
        newCred = Math.clamp(newCred, 0.0, 100.0);

        author.setCredibility(newCred);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDto getReview(Long reviewId) {
        ReviewPost review = reviewPostRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
        return ReviewResponseDto.from(review);
    }

    @Override
    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto dto, Member reviewer) {
        ReviewPost review = reviewPostRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        if (!review.getReviewer().equals(reviewer)) {
            throw new ReviewAccessDeniedException("리뷰 수정 권한이 없습니다");
        }

        List<Long> tagIds = dto.getMannerTags();
        if (tagIds == null || tagIds.isEmpty()) {
            throw new MissingReviewTagException();
        }

        if (tagIds.stream().distinct().count() != tagIds.size()) {
            throw new DuplicateReviewTagException();
        }

        List<ReviewTagType> originalTagTypes = review.getMannerTags().stream()
                .map(tag -> tag.getReviewTag().getType())
                .toList();

        revertCredibility(review.getPost().getAuthor(), originalTagTypes);

        reviewPostTagRepository.deleteByReviewPostId(review.getId());
        review.getMannerTags().clear();

        List<ReviewPostTag> reviewTags = dto.getMannerTags().stream()
                .map(tagId -> {
                    ReviewTag reviewTag = reviewTagRepository.findById(tagId)
                            .orElseThrow(ReviewTagNotFoundException::new);
                    return ReviewPostTag.builder()
                            .reviewPost(review)
                            .reviewTag(reviewTag)
                            .build();
                })
                .toList();

        dto.updateReviewPost(review, reviewTags);
        reviewPostRepository.save(review);

        List<ReviewTagType> newTagTypes = reviewTags.stream()
                .map(tag -> tag.getReviewTag().getType())
                .toList();

        calculateCredibility(review.getPost().getAuthor(), newTagTypes);

        return ReviewResponseDto.from(review);
    }


    @Override
    public void revertCredibility(Member author, List<ReviewTagType> tagTypes) {
        double value = tagTypes.stream()
                .mapToDouble(ReviewTagType::getValue)
                .sum();

        double newCred = author.getCredibility() - value;
        newCred = Math.clamp(newCred, 0.0, 100.0);

        author.setCredibility(newCred);
    }

    @Override
    public void deleteReview(Long reviewId, Member reviewer) {
        ReviewPost review = reviewPostRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
        if (!review.getReviewer().equals(reviewer)) {
            throw new ReviewAccessDeniedException("리뷰 삭제 권한이 없습니다");
        }
        reviewPostRepository.delete(review);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getMannerTagCounts(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(NotFoundMemberException::new);

        return reviewPostRepository.countTagsByPostAuthorId(member.getId()).stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDto getPostReview(Long postId) {
        ReviewPost review = reviewPostRepository.findByPostId(postId)
                .orElseThrow(NotFoundPostException::new);
        return ReviewResponseDto.from(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getMemberReviews(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(NotFoundMemberException::new);
        List<ReviewPost> reviews = reviewPostRepository.findAllByReviewerId(member.getId());
        return reviews.stream()
                .map(ReviewResponseDto::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CredibilityResponseDto getCredibility(String nickname){
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(ReviewNotFoundException::new);

        return new CredibilityResponseDto(member.getCredibility());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewTagResponseDto> getTags(){
        List<ReviewTag> tags = reviewTagRepository.findAll();

        return tags.stream()
                .map(tag -> new ReviewTagResponseDto(tag.getId(), tag.getTagName(), tag.getType()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsAboutMe(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(NotFoundMemberException::new);

        List<ReviewPost> reviews = reviewPostRepository.findAllByToMemberId(member.getId());

        return reviews.stream()
                .map(ReviewResponseDto::from)
                .toList();
    }

    @Override
    public ReviewMetaResponseDto getReviewMeta(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);
        return ReviewMetaResponseDto.from(post);
    }
}

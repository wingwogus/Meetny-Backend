package mjc.capstone.joinus.service.implementation;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.review.ReviewPost;
import mjc.capstone.joinus.domain.review.ReviewPostTag;
import mjc.capstone.joinus.domain.review.ReviewTag;
import mjc.capstone.joinus.dto.ReviewRequestDto;
import mjc.capstone.joinus.dto.ReviewResponseDto;
import mjc.capstone.joinus.exception.*;
import mjc.capstone.joinus.repository.*;
import mjc.capstone.joinus.service.inf.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
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
                .orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (reviewPostRepository.existsByPostId(postId)) {
            throw new DuplicateReviewException();
        }

        if (!post.getParticipant().equals(member)) {
            throw new ReviewAccessDeniedException("참여하지 않은 동행에 리뷰를 작성할 수 없습니다");
        }

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

        return ReviewResponseDto.from(review);
    }

    @Override
    public ReviewResponseDto getReview(Long reviewId) {
        ReviewPost review = reviewPostRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
        return ReviewResponseDto.from(review);
    }

    @Override
    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto dto, Member reviewer) {
        ReviewPost review = reviewPostRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        if (!review.getReviewer().equals(reviewer)) {
            throw new ReviewAccessDeniedException("리뷰 수정 권한이 없습니다");
        }

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

        return ReviewResponseDto.from(review);
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
    public Map<String, Long> getMannerTagCounts(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        List<ReviewPostTag> allTags = reviewPostRepository.findAll().stream()
                .filter(review -> review.getPost().getAuthor().equals(member))
                .flatMap(review -> review.getMannerTags().stream())
                .toList();

        return allTags.stream()
                .collect(Collectors.groupingBy(
                        tag -> tag.getReviewTag().getTagName(),
                        Collectors.counting()
                ));
    }

    @Override
    public ReviewResponseDto getPostReview(Long postId) {
        ReviewPost review = reviewPostRepository.findByPostId(postId)
                .orElseThrow(PostNotFoundException::new);
        return ReviewResponseDto.from(review);
    }

    @Override
    public List<ReviewResponseDto> getMemberReviews(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        List<ReviewPost> reviews = reviewPostRepository.findAllByReviewerId(memberId);
        return reviews.stream()
                .map(ReviewResponseDto::from)
                .toList();
    }
}

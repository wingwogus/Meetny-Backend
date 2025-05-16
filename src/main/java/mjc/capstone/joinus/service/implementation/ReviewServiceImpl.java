package mjc.capstone.joinus.service.implementation;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.review.ReviewPost;
import mjc.capstone.joinus.domain.review.ReviewPostTag;
import mjc.capstone.joinus.domain.review.ReviewTag;
import mjc.capstone.joinus.dto.ReviewRequestDto;
import mjc.capstone.joinus.dto.ReviewResponseDto;
import mjc.capstone.joinus.repository.*;
import mjc.capstone.joinus.service.inf.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewPostRepository reviewPostRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final ReviewPostTagRepository reviewPostTagRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public ReviewResponseDto createReview(Long memberId, Long postId, ReviewRequestDto dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

        ReviewPost review = dto.toEntity(member);
        review.setPost(post);

        review.setMannerTags(
                dto.getMannerTags().stream()
                        .map(tagId -> {
                            ReviewTag reviewTag = reviewTagRepository.findById(tagId)
                                    .orElseThrow(() -> new IllegalArgumentException("Invalid tag ID: " + tagId));
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
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return ReviewResponseDto.from(review);
    }

    @Override
    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto dto) {
        ReviewPost review = reviewPostRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with ID: " + reviewId));

        reviewPostTagRepository.deleteByReviewPostId(review.getId());
        review.getMannerTags().clear();

        List<ReviewPostTag> reviewTags = dto.getMannerTags().stream()
                .map(tagId -> {
                    ReviewTag reviewTag = reviewTagRepository.findById(tagId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid tag ID: " + tagId));
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
    public void deleteReview(Long reviewId) {
        reviewPostRepository.deleteById(reviewId);
    }

    @Override
    public Map<String, Long> getMannerTagCounts(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

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
}

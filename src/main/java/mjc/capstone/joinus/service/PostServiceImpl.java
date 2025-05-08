package mjc.capstone.joinus.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.Post;
import mjc.capstone.joinus.domain.PostLike;
import mjc.capstone.joinus.domain.PostView;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.PostLikeResponseDto;
import mjc.capstone.joinus.dto.PostRequestDto;
import mjc.capstone.joinus.dto.PostResponseDto;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.repository.PostLikeRepository;
import mjc.capstone.joinus.repository.PostRepository;
import mjc.capstone.joinus.repository.PostViewRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostViewRepository postViewRepository;

    @Override
    public void createPost(PostRequestDto dto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));

        postRepository.save(dto.toEntity(member));
    }

    @Override
    public void updatePost(Post post, PostRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));

        validateAuth(post, member);
        requestDto.updatePost(post);
    }

    @Override
    public void deletePost(Post post, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));

        validateAuth(post, member);
        postRepository.delete(post);
    }

    @Override
    public PostResponseDto getPostDetail(Post post, Long memberId) {
        if (memberId != null) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));

            boolean alreadyViewed = postViewRepository.existsByPostAndMember(post, member);

            if (!alreadyViewed) {
                post.increaseViewCount(); // Post에 정의한 조회수 증가 메서드
                postViewRepository.save(new PostView(post, member));
            }

            return PostResponseDto.from(post, isPostLikedByMember(post, memberId));
        }

        return PostResponseDto.from(post, false);
    }

    @Override
    public List<PostResponseDto> getPostByTag(Tag tag, Long memberId) {
        return postRepository.findByTag(tag).stream()
                .map(post -> PostResponseDto.from(post, isPostLikedByMember(post,memberId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDto> getAllPosts(Long memberId) {
        return postRepository.findAll().stream()
                .map(post -> PostResponseDto.from(post, isPostLikedByMember(post,memberId)))
                .toList();
    }

    @Override
    public List<PostResponseDto> getPostsByDateRange(LocalDateTime from, LocalDateTime to, Long memberId) {
        return postRepository.findAllByCreatedAtBetween(from, to.plusDays(1)).stream()
                .map(post -> PostResponseDto.from(post, isPostLikedByMember(post,memberId)))
                .collect(Collectors.toList());
    }

    @Override
    public PostLikeResponseDto togglePostLike(Post post, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));

        boolean liked;
        if (postLikeRepository.existsByMemberAndPost(member, post)) {
            postLikeRepository.deleteByMemberAndPost(member, post);
            liked = false;
        } else {
            PostLike postLike = new PostLike(member, post);
            postLikeRepository.save(postLike);
            liked = true;
        }

        int likeCount = postLikeRepository.countByPost(post);

        return new PostLikeResponseDto(liked, likeCount);
    }

    @Override
    public void validateAuth(Post post, Member member) {
        if (!(post.getAuthor().equals(member))) {
            throw new AccessDeniedException("작성자만 수정/삭제할 수 있습니다.");
        }
    }

    // 사용자가 좋아요를 눌렀는지 안눌렀는지
    private boolean isPostLikedByMember(Post post, Long memberId) {
        if (memberId == null) return false;
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        return postLikeRepository.existsByMemberAndPost(member, post);
    }




//    @Override
//    public List<PostResponseDto> searchPosts(String keyword) {
//        return postRepository.findByTitleOrContentContaining(keyword).stream()
//                .map(PostResponseDto::from)
//                .collect(Collectors.toList());
//    }
}

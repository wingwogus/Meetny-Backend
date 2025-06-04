package mjc.capstone.joinus.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.entity.PostLike;
import mjc.capstone.joinus.domain.entity.PostView;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.post.PostLikeResponseDto;
import mjc.capstone.joinus.dto.post.PostRequestDto;
import mjc.capstone.joinus.dto.post.PostResponseDto;
import mjc.capstone.joinus.exception.InvalidTokenException;
import mjc.capstone.joinus.exception.NotFoundMemberException;
import mjc.capstone.joinus.repository.*;
import mjc.capstone.joinus.service.inf.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final TagRepository tagRepository;

    @Override
    public void createPost(PostRequestDto dto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        Tag tag = tagRepository.findById(dto.getTagId())
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));

        Post post = dto.toEntity(member, tag);
        postRepository.save(post);
    }

    @Override
    public void updatePost(Post post, PostRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        validateAuth(post, member);
        Tag tag = tagRepository.findById(requestDto.getTagId())
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));

        requestDto.updatePost(post, tag);
    }

    @Override
    public void deletePost(Post post, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        validateAuth(post, member);
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponseDto getPostDetail(Post post, Long memberId) {
        if (memberId != null) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(NotFoundMemberException::new);

            boolean alreadyViewed = postViewRepository.existsByPostAndMember(post, member);

            if (!alreadyViewed) {
                post.increaseViewCount(); // Post에 정의한 조회수 증가 메서드
                postViewRepository.save(new PostView(post, member));
            }



            return PostResponseDto.from(post, isPostLikedByMember(post, memberId));
        }

        return PostResponseDto.from(post,false);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponseDto> getPostByTag(Tag tag, Long memberId) {
        return postRepository.findByTag(tag).stream()
                .map(post -> PostResponseDto.from(post, isPostLikedByMember(post,memberId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDto> getLikedPost(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        return postLikeRepository.findByMember(member).stream()
                .map(postLike -> PostResponseDto.from(postLike.orElseThrow().getPost(), true))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponseDto> getAllPosts(Long memberId) {
        return postRepository.findAll().stream()
                .map(post -> PostResponseDto.from(post, isPostLikedByMember(post,memberId)))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponseDto> getPostsByDateRange(LocalDateTime from, LocalDateTime to, Long memberId) {
        return postRepository.findAllByCreatedAtBetween(from, to.plusDays(1)).stream()
                .map(post -> PostResponseDto.from(post, isPostLikedByMember(post,memberId)))
                .collect(Collectors.toList());
    }

    @Override
    public PostLikeResponseDto togglePostLike(Post post, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

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

    @Transactional(readOnly = true)
    @Override
    public List<PostResponseDto> getPostsByMember(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(NotFoundMemberException::new);

        return postRepository.findByAuthor(member).stream()
                .map(post -> PostResponseDto.from(post, isPostLikedByMember(post, member.getId())))
                .toList();
    }

    @Override
    public void validateAuth(Post post, Member member) {
        if (!(post.getAuthor().equals(member))) {
            throw new InvalidTokenException("작성자만 수정/삭제할 수 있습니다.");
        }
    }

    // 사용자가 좋아요를 눌렀는지 안눌렀는지
    private boolean isPostLikedByMember(Post post, Long memberId) {
        if (memberId == null) return false;
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        return postLikeRepository.existsByMemberAndPost(member, post);
    }

    @Override
    public void addParticipant(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (post.getParticipant() != null){
            throw new IllegalArgumentException("This post already has a participant.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        post.setParticipant(member);
        postRepository.save(post);
    }


//    @Override
//    public List<PostResponseDto> searchPosts(String keyword) {
//        return postRepository.findByTitleOrContentContaining(keyword).stream()
//                .map(PostResponseDto::from)
//                .collect(Collectors.toList());
//    }
}

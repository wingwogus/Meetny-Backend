package mjc.capstone.joinus.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.Post;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.PostRequestDto;
import mjc.capstone.joinus.dto.PostResponseDto;
import mjc.capstone.joinus.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public void createPost(PostRequestDto dto, Member member) {
        postRepository.save(dto.toEntity(member));
    }

    @Override
    public void updatePost(PostRequestDto post, Member member) {

    }

    @Override
    public void deletePost(PostRequestDto post, Member member) {

    }

    @Override
    public List<PostResponseDto> findPostByTag(Tag tag) {
        return List.of();
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::from)
                .toList();
    }

    @Override
    public void validateAuth(Post post, Member member) {
        if (!post.getAuthor().equals(member)) {
            throw new AccessDeniedException("작성자만 수정/삭제할 수 있습니다.");
        }
    }
}

package mjc.capstone.joinus.service;

import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.Post;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.PostRequestDto;
import mjc.capstone.joinus.dto.PostResponseDto;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public interface PostService {
    void createPost(PostRequestDto post, Member member);
    void updatePost(Post post, PostRequestDto requestDto, Member member);

    void deletePost(PostRequestDto post, Member member);
    List<PostResponseDto> findPostByTag(Tag tag);
    List<PostResponseDto> getAllPosts();

    void validateAuth(Post post, Member member);

}

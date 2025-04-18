package mjc.capstone.joinus.service;

import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.Post;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.PostRequestDto;
import mjc.capstone.joinus.dto.PostResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface PostService {
    void createPost(PostRequestDto post, Long memberId);

    void updatePost(Post post, PostRequestDto requestDto, Long memberId);

    void deletePost(Post post, Long memberId);

    List<PostResponseDto> getPostByTag(Tag tag);

    List<PostResponseDto> getAllPosts();

    void validateAuth(Post post, Member member);

    List<PostResponseDto> getPostsByDateRange(LocalDateTime from, LocalDateTime to);

//    List<PostResponseDto> searchPosts(String keyword);


}

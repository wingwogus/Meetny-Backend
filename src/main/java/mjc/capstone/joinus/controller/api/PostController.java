package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.*;
import mjc.capstone.joinus.dto.post.PostLikeResponseDto;
import mjc.capstone.joinus.dto.post.PostRequestDto;
import mjc.capstone.joinus.dto.post.PostResponseDto;
import mjc.capstone.joinus.service.inf.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity<List<PostResponseDto>> getAllPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails != null ? userDetails.getMember().getId() : null;

        return ResponseEntity.ok(postService.getAllPosts(memberId));
    }

    @PostMapping("/")
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.createPost(dto, userDetails.getMember().getId());
        return ResponseEntity.ok("Post created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable("id") Post post, @RequestBody PostRequestDto dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.updatePost(post, dto, userDetails.getMember().getId());
        return ResponseEntity.ok("Post updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Post post, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(post, userDetails.getMember().getId());
        return ResponseEntity.ok("Post deleted");
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostDetail(@PathVariable("id") Post post, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails != null ? userDetails.getMember().getId() : null;

        return ResponseEntity.ok(postService.getPostDetail(post, memberId));
    }

    // 태그별 게시글 조회
    @GetMapping("/tag/{id}")
    public ResponseEntity<List<PostResponseDto>> getPostsByTag(@PathVariable("id") Tag tag, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails != null ? userDetails.getMember().getId() : null;

        return ResponseEntity.ok(postService.getPostByTag(tag, memberId));
    }

    // 날짜별 게시글 조회
    @GetMapping("/date")
    public ResponseEntity<List<PostResponseDto>> getPostsByDate(@RequestBody SearchRequest searchRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails != null ? userDetails.getMember().getId() : null;

        return ResponseEntity.ok(postService.getPostsByDateRange(searchRequest.getFrom(), searchRequest.getTo(), memberId));
    }

    // 포스트 좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<PostLikeResponseDto> togglePostLike(@PathVariable("id") Post post,
                                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostLikeResponseDto response =
                postService.togglePostLike(post, userDetails.getMember().getId());
        return ResponseEntity.ok(response);
    }



//    // 게시글 키워드 검색
//    @GetMapping("/search")
//    public List<PostResponseDto> searchPosts(@RequestParam("keyword") String keyword) {
//        return postService.searchPosts(keyword);
//    }



}

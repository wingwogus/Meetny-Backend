package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.Post;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.CustomUserDetails;
import mjc.capstone.joinus.dto.PostRequestDto;
import mjc.capstone.joinus.dto.PostResponseDto;
import mjc.capstone.joinus.dto.SearchRequest;
import mjc.capstone.joinus.service.PostService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @GetMapping
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.createPost(dto, userDetails.getMember());
        return ResponseEntity.ok("Post created");
    }
//
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable("id") Post post, @RequestBody PostRequestDto dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.updatePost(post, dto, userDetails.getMember());
        return ResponseEntity.ok("Post updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Post post, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(post, userDetails.getMember());
        return ResponseEntity.ok("Post deleted");
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable("id") Post post) {
        return PostResponseDto.from(post);
    }

    // 태그별 게시글 조회
    @GetMapping("/tag/{id}")
    public List<PostResponseDto> getPostsByTag(@PathVariable("id") Tag tag) {
        return postService.getPostByTag(tag);
    }

    // 날짜별 게시글 조회
    @GetMapping("/date")
    public List<PostResponseDto> getPostsByDate(
            @RequestBody SearchRequest searchRequest) {
        return postService.getPostsByDateRange(searchRequest.getFrom(), searchRequest.getTo());
    }

    // 게시글 키워드 검색
    @GetMapping("/search")
    public List<PostResponseDto> searchPosts(@RequestParam("keyword") String keyword) {
        return postService.searchPosts(keyword);
    }



}

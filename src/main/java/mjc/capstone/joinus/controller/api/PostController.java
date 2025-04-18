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
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping
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
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("id") Post post) {
        return ResponseEntity.ok(PostResponseDto.from(post));
    }

    // 태그별 게시글 조회
    @GetMapping("/tag/{id}")
    public ResponseEntity<List<PostResponseDto>> getPostsByTag(@PathVariable("id") Tag tag) {
        return ResponseEntity.ok(postService.getPostByTag(tag));
    }

    // 날짜별 게시글 조회
    @GetMapping("/date")
    public ResponseEntity<List<PostResponseDto>> getPostsByDate(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(postService.getPostsByDateRange(searchRequest.getFrom(), searchRequest.getTo()));
    }

//    // 게시글 키워드 검색
//    @GetMapping("/search")
//    public List<PostResponseDto> searchPosts(@RequestParam("keyword") String keyword) {
//        return postService.searchPosts(keyword);
//    }



}

package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.Post;
import mjc.capstone.joinus.dto.CustomUserDetails;
import mjc.capstone.joinus.dto.PostRequestDto;
import mjc.capstone.joinus.dto.PostResponseDto;
import mjc.capstone.joinus.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @GetMapping
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

//    @GetMapping("/{id}")
//    public PostResponseDto getPost(@PathVariable Long id) {
//        return postService.getPost(id);
//    }
//
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.createPost(dto, userDetails.getMember());
        return ResponseEntity.ok("Post created");
    }
//
    @PutMapping("/{id}")
    public void updatePost(@PathVariable("id") Post post, @RequestBody PostRequestDto dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.updatePost(post, dto, userDetails.getMember());
    }
//
//    @DeleteMapping("/{id}")
//    public void deletePost(@PathVariable Long id) {
//        postService.deletePost(id);
//    }
}

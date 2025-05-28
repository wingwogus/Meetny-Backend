package mjc.capstone.joinus.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.ApiResponse;
import mjc.capstone.joinus.dto.auth.CustomUserDetails;
import mjc.capstone.joinus.dto.post.PostLikeResponseDto;
import mjc.capstone.joinus.dto.post.PostRequestDto;
import mjc.capstone.joinus.dto.post.PostResponseDto;
import mjc.capstone.joinus.dto.post.SearchRequest;
import mjc.capstone.joinus.service.inf.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@io.swagger.v3.oas.annotations.tags.Tag(name = "Post", description = "포스트 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "모든 게시글 조회", description = "현재 생성된 모든 게시글을 조횝합니다")
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<PostResponseDto>>> getAllPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails != null ? userDetails.getMember().getId() : null;

        return ResponseEntity.ok(ApiResponse.success("모든 게시글 조회 성공", postService.getAllPosts(memberId)));
    }

    @Operation(summary = "게시글 생성", description = "게시글 하나를 생성합니다")
    @PostMapping("/")
    public ResponseEntity<ApiResponse<Void>> createPost(@RequestBody PostRequestDto dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.createPost(dto, userDetails.getMember().getId());
        return ResponseEntity.ok(ApiResponse.success("게시글 생성 성공", null));
    }

    @Operation(summary = "게시글 수정", description = "게시글 하나를 수정합니다")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable("id") Post post, @RequestBody PostRequestDto dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.updatePost(post, dto, userDetails.getMember().getId());
        return ResponseEntity.ok(ApiResponse.success("게시글 수정 성공", null));
    }

    @Operation(summary = "게시글 삭제", description = "게시글 하나를 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable("id") Post post, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(post, userDetails.getMember().getId());
        return ResponseEntity.ok(ApiResponse.success("게시글 삭제 성공", null));
    }

    // 게시글 단건 조회
    @Operation(summary = "게시글 조회", description = "게시글 하나를 조회합니다")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPostDetail(@PathVariable("id") Post post, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails != null ? userDetails.getMember().getId() : null;

        return ResponseEntity.ok(ApiResponse.success(
                "게시글 조회 성공",
                postService.getPostDetail(post, memberId)));
    }


    // 태그별 게시글 조회
    @Operation(summary = "태그별 게시글 조회", description = "원하는 태그의 모든 게시글을 조회합니다")
    @GetMapping("/tag/{id}")
    public ResponseEntity<ApiResponse<List<PostResponseDto>>> getPostsByTag(@PathVariable("id") Tag tag, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails != null ? userDetails.getMember().getId() : null;

        return ResponseEntity.ok(ApiResponse.success(
                "태그별 게시글 조회 성공",
                postService.getPostByTag(tag, memberId)));
    }

    // 날짜별 게시글 조회
    @Operation(summary = "날짜별 게시글 조회", description = "원하는 날짜의 모든 게시글을 조회합니다")
    @GetMapping("/date")
    public ResponseEntity<ApiResponse<List<PostResponseDto>>> getPostsByDate(@RequestBody SearchRequest searchRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails != null ? userDetails.getMember().getId() : null;

        List<PostResponseDto> posts = postService.getPostsByDateRange(
                searchRequest.getFrom(),
                searchRequest.getTo(),
                memberId);

        return ResponseEntity.ok(ApiResponse.success("날짜별 게시글 조회 성공", posts));
    }

    // 포스트 좋아요
    @Operation(summary = "게시글 좋아요", description = "게시글 하나에 좋아요을 누르거나 취소합니다")
    @PostMapping("/{id}/like")
    public ResponseEntity<ApiResponse<PostLikeResponseDto>> togglePostLike(@PathVariable("id") Post post,
                                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostLikeResponseDto response =
                postService.togglePostLike(post, userDetails.getMember().getId());
        return ResponseEntity.ok(ApiResponse.success("좋아요/취소 성공", response));
    }

    // 동행 참여
    @PostMapping("/{postId}/participant")
    public ResponseEntity<String> addParticipant(@PathVariable Long postId, @RequestParam Long memberId){
        postService.addParticipant(postId, memberId);
        return ResponseEntity.ok("Participant added");
    }



//    // 게시글 키워드 검색
//    @GetMapping("/search")
//    public List<PostResponseDto> searchPosts(@RequestParam("keyword") String keyword) {
//        return postService.searchPosts(keyword);
//    }



}

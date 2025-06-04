package mjc.capstone.joinus.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.dto.ApiResponse;
import mjc.capstone.joinus.dto.review.ReviewResponseDto;
import mjc.capstone.joinus.dto.tag.TagResponseDto;
import mjc.capstone.joinus.service.inf.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Tag(name = "Tag API", description = "태그 조회 컨트롤러")
public class TagController {

    private final TagService tagService;

    //전체 태그 조회
    @GetMapping("/all")
    @Operation(summary = "전체 태그 조회", description = "전체 태그 조회 API 입니다.")
    public ResponseEntity<ApiResponse<List<TagResponseDto>>> findAllTags(){
        List<TagResponseDto> tagResponseDtos = tagService.findAllTags();
        return ResponseEntity.ok(ApiResponse.success(tagResponseDtos));
    }

    // 카테고리 별 태그 조회
    @GetMapping("/{category}")
    @Operation(summary = "카테고리 별 태그 조회", description = "카테고리별  태그 조회 API 입니다.")
    public ResponseEntity<ApiResponse<List<TagResponseDto>>> findAllTagsByCategory(@PathVariable String category){
        List<TagResponseDto> tagResponseDtos = tagService.findTagsByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(tagResponseDtos));
    }
}

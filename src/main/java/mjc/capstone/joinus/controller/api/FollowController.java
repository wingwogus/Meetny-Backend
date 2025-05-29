package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.dto.mypage.FollowDto;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.service.inf.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final MemberRepository memberRepository;

    @PostMapping("/follow/{toId}")
    public ResponseEntity<String> follow(@PathVariable("toId") Long toId,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        Member fromMember = memberRepository.findByUsernameWithMemberTag(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid logged-in user"));
        Member toMember = memberRepository.findById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid toUser ID"));
        followService.follow(fromMember, toMember);
        return ResponseEntity.ok("팔로우 성공");
    }

    @DeleteMapping("/unfollow/{toId}")
    public ResponseEntity<String> unfollow(@PathVariable("toId") Long toId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        Member fromMember = memberRepository.findByUsernameWithMemberTag(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid logged-in user"));
        Member toMember = memberRepository.findById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid toUser ID"));
        followService.unfollow(fromMember, toMember);
        return ResponseEntity.ok("언팔로우 성공");
    }

    @GetMapping("/{userId}/followings")
    public List<FollowDto> getFollowings(@PathVariable("userId") Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return followService.getFollowings(member).stream()
                .map(FollowDto::from)
                .toList();
    }

    @GetMapping("/{userId}/followers")
    public List<FollowDto> getFollowers(@PathVariable("userId") Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return followService.getFollowers(member).stream()
                .map(FollowDto::from)
                .toList();
    }

    @GetMapping("/{userId}/followings/count")
    public ResponseEntity<Long> countFollowings(@PathVariable("userId") Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return ResponseEntity.ok(followService.countFollowings(member));
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<Long> countFollowers(@PathVariable("userId") Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return ResponseEntity.ok(followService.countFollowers(member));
    }
}

package mjc.capstone.joinus.controllers;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final MemberRepository memberRepository;

    @PostMapping("/{fromId}/follow/{toId}")
    public ResponseEntity<String> follow(@PathVariable Long fromId, @PathVariable Long toId) {
        Member fromUser = memberRepository.findById(String.valueOf(fromId)).orElseThrow();
        Member toUser = memberRepository.findById(String.valueOf(toId)).orElseThrow();
        followService.follow(fromUser, toUser);
        return ResponseEntity.ok("팔로우 성공");
    }

    @DeleteMapping("/{fromId}/unfollow/{toId}")
    public ResponseEntity<String> unfollow(@PathVariable Long fromId, @PathVariable Long toId) {
        Member fromUser = memberRepository.findById(String.valueOf(fromId)).orElseThrow();
        Member toUser = memberRepository.findById(String.valueOf(toId)).orElseThrow();
        followService.unfollow(fromUser, toUser);
        return ResponseEntity.ok("언팔로우 성공");
    }

    @GetMapping("/{userId}/followings")
    public List<Member> getFollowings(@PathVariable Long userId) {
        Member user = memberRepository.findById(String.valueOf(userId)).orElseThrow();
        return followService.getFollowings(user);
    }

    @GetMapping("/{userId}/followers")
    public List<Member> getFollowers(@PathVariable Long userId) {
        Member user = memberRepository.findById(String.valueOf(userId)).orElseThrow();
        return followService.getFollowers(user);
    }

    @GetMapping("/{userId}/followings/count")
    public long countFollowings(@PathVariable Long userId) {
        Member user = memberRepository.findById(String.valueOf(userId)).orElseThrow();
        return followService.countFollowings(user);
    }

    @GetMapping("/{userId}/followers/count")
    public long countFollowers(@PathVariable Long userId) {
        Member user = memberRepository.findById(String.valueOf(userId)).orElseThrow();
        return followService.countFollowers(user);
    }
}

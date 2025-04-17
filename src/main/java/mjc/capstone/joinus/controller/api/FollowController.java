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
    public ResponseEntity<String> follow(@PathVariable("fromId") Member fromMember, @PathVariable("toId") Member toMember) {
        followService.follow(fromMember, toMember);
        return ResponseEntity.ok("팔로우 성공");
    }

    @DeleteMapping("/{fromId}/unfollow/{toId}")
    public ResponseEntity<String> unfollow(@PathVariable("fromId") Member fromMember, @PathVariable("toId") Member toMember) {
        followService.unfollow(fromMember, toMember);
        return ResponseEntity.ok("언팔로우 성공");
    }

    @GetMapping("/{userId}/followings")
    public List<Member> getFollowings(@PathVariable("memberId") Member member) {
        return followService.getFollowings(member);
    }

    @GetMapping("/{userId}/followers")
    public List<Member> getFollowers(@PathVariable("memberId") Member member) {
        return followService.getFollowers(member);
    }

    @GetMapping("/{userId}/followings/count")
    public int countFollowings(@PathVariable("memberId") Member member) {
        return (int) followService.countFollowings(member);
    }

    @GetMapping("/{userId}/followers/count")
    public int countFollowers(@PathVariable("memberId") Member member) {
        return (int) followService.countFollowers(member);
    }
}

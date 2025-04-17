package mjc.capstone.joinus.service;

import mjc.capstone.joinus.domain.Member;

import java.util.List;

public interface FollowService {
    void follow(Member fromUser, Member toUser);
    void unfollow(Member fromUser, Member toUser);
    List<Member> getFollowings(Member user);
    List<Member> getFollowers(Member user);
    long countFollowings(Member user);
    long countFollowers(Member user);
}

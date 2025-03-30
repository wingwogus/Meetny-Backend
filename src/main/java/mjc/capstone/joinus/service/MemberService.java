package mjc.capstone.joinus.service;

import mjc.capstone.joinus.domain.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findUserById(String id);
    Member resisterUser(Member user);
    boolean validateId(Long id);
}
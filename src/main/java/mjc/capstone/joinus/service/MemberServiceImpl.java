/*
package mjc.capstone.joinus.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.exception.IdValidationException;
import mjc.capstone.joinus.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;


    @Override
    @Transactional
    public Optional<Member> findUserById(String id){
        return memberRepository.findById(id);
    }

    @Override
    @Transactional
    public Member resisterUser(Member user){
        user.setRoles(List.of("ROLE_USER"));
        return memberRepository.save(user);
    }

    @Override
    @Transactional
    public boolean validateId(Long id){
        if(memberRepository.findUserById(id).isPresent()){
            throw new IdValidationException("이미 존재하는 아이디입니다.");
        }
        return true;
    }
}

*/

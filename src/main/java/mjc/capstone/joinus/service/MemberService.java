package mjc.capstone.joinus.service;

import mjc.capstone.joinus.dto.MemberDto;
import mjc.capstone.joinus.jwt.JwtToken;
import mjc.capstone.joinus.jwt.MemberLoginRequestDto;
import mjc.capstone.joinus.jwt.RegisterDto;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

    @Transactional
    JwtToken login(MemberLoginRequestDto memberLoginRequestDto);


    @Transactional
    MemberDto register(RegisterDto registerDto);
}

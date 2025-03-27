package mjc.capstone.joinus.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.User;
import mjc.capstone.joinus.exception.IdValidationException;
import mjc.capstone.joinus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public Optional<User> findUserById(String id){
        return userRepository.findById(id);
    }

    // 회원가입 저장
    @Transactional
    public User resisterUser(User user){
        return userRepository.save(user);
    }

    // 아이디 중복검사
    @Transactional
    public boolean validateId(String id){
        if(userRepository.findUserById(id).isPresent()){
            throw new IdValidationException("이미 존재하는 아이디입니다.");
        }
        else {
            return true;
        }
    }
}

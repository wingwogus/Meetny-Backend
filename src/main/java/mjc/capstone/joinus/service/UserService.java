package mjc.capstone.joinus.service;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

}

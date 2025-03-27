package mjc.capstone.joinus.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import mjc.capstone.joinus.repository.UserRepository;
import org.hibernate.annotations.ConcreteProxy;
import org.springframework.stereotype.Controller;


@Controller
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

}

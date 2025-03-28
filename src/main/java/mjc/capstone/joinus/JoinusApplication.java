package mjc.capstone.joinus;

import mjc.capstone.joinus.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

@SpringBootApplication
public class JoinusApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoinusApplication.class, args);
	}

}

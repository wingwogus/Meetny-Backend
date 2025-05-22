package mjc.capstone.joinus;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class JoinusApplication {
	public static void main(String[] args) {
		SpringApplication.run(JoinusApplication.class, args);
	}

}

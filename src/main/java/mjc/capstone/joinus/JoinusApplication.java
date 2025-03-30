package mjc.capstone.joinus;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.service.InitDataService;
import org.springframework.boot.CommandLineRunner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import mjc.capstone.joinus.domain.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class JoinusApplication implements CommandLineRunner {

	private final InitDataService initDataService;

	public static void main(String[] args) {
		SpringApplication.run(JoinusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initDataService.init();
	}
}

package MJLee.onlineCourseService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class OnlineClassServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineClassServiceApplication.class, args);
	}

}

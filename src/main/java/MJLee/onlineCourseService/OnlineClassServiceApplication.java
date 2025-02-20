package MJLee.onlineCourseService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class OnlineClassServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineClassServiceApplication.class, args);
	}

}

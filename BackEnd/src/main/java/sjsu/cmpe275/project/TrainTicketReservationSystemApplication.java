package sjsu.cmpe275.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

//@EnableOAuth2Sso
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
public class TrainTicketReservationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainTicketReservationSystemApplication.class, args);
	}
}

package ke.natujenge.whatsappbot;

import ke.natujenge.whatsappbot.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class WhatsappbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatsappbotApplication.class, args);
	}

}

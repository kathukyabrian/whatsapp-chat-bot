package ke.natujenge.whatsappbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private String appId;
    private String appSecret;
    private String phoneNumberId;
    private String businessAccountId;
    private String accessToken;
    private String version;
    private String templateNamePrefix;
    private String verificationToken;
    private String baseUrl;
    private String phoneNumber;
    private String oauthToken;
    private String jumbaProductsUrl;
}

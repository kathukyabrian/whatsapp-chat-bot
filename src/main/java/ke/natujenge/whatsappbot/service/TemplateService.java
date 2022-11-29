package ke.natujenge.whatsappbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ke.natujenge.whatsappbot.config.ApplicationProperties;
import ke.natujenge.whatsappbot.dto.Data;
import ke.natujenge.whatsappbot.dto.Template;
import ke.natujenge.whatsappbot.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class TemplateService {
    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TemplateService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public List<Template> getTemplates() throws IOException {
        String businessAccountId = applicationProperties.getBusinessAccountId();
        String accessToken = applicationProperties.getAccessToken();
        String version = applicationProperties.getVersion();
        String limit = "1000";
        String baseUrl = applicationProperties.getBaseUrl();

        String url = baseUrl
                + version
                + "/"
                + businessAccountId
                + "/message_templates?limit="
                + limit
                + "&access_token=" + accessToken;
        log.debug("about to send request to url : {}", url);

        String response = HttpUtil.get(url);
        log.debug("Got response : {}", response);

        Data data = objectMapper.readValue(response, Data.class);

        return data.getData();
    }
}

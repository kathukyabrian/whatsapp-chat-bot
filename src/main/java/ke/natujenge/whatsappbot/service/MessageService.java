package ke.natujenge.whatsappbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ke.natujenge.whatsappbot.config.ApplicationProperties;
import ke.natujenge.whatsappbot.dto.Message;
import ke.natujenge.whatsappbot.dto.Template;
import ke.natujenge.whatsappbot.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MessageService {

    private final ApplicationProperties applicationProperties;
    private final TemplateService templateService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MessageService(ApplicationProperties applicationProperties, TemplateService templateService) {
        this.applicationProperties = applicationProperties;
        this.templateService = templateService;
    }

    public void processMessages(List<Message> messages) {
        log.debug("about to process {} messages", messages.size());

        for(Message message : messages){
            // get the textMessage
            String textMessage = message.getText().getBody();

            // resolve template
            String templateName = resolveTemplate(textMessage);

            // resolve phoneNumber
            String phoneNumber = message.getFrom();

            try {
                reply(phoneNumber, templateName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void reply(String phoneNumber, String templateName) throws IOException {
        List<Template> templates = templateService.getTemplates();

        Template resolvedTemplate = null;
        for(Template template : templates){
            if(template.getName().equals(templateName)){
                resolvedTemplate = template;
                break;
            }
        }

        // prepare message
        Map<String, Object> message = new HashMap<>();
        message.put("messaging_product", "whatsapp");
        message.put("recipient_type", "individual");
        message.put("to",phoneNumber);
        message.put("type", "template");

        // template stuff
        Map<String, Object> template = new HashMap<>();
        template.put("name", resolvedTemplate.getName());
        Map<String, String> languageMap = new HashMap<>();
        languageMap.put("code", resolvedTemplate.getLanguage());
        template.put("language", languageMap);

//        if(resolvedTemplate!= null && !resolvedTemplate.getComponents().isEmpty()){
//            template.put("components", resolvedTemplate.getComponents());
//        }

        message.put("template", template);

        String endpoint = applicationProperties.getBaseUrl()
                + applicationProperties.getVersion() + "/"
                + applicationProperties.getPhoneNumberId()
                + "/messages";

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + applicationProperties.getAccessToken());

        log.debug("about to send message to endpoint : {} message : {} with headers : {}",endpoint, message, headerMap);

        String request = objectMapper.writeValueAsString(message);

        String response = HttpUtil.post(endpoint, request, headerMap, MediaType.get("application/json; charset=utf-8"));
        log.debug("Got response : {}", response);
    }


    public String resolveTemplate(String textMessage) {
        if (textMessage.contains("hi")) {
            return "hello_world";
        } else if (textMessage.contains("jumba")) {
            return "hello_jumba";
        } else if (textMessage.contains("flight")) {
            return "sample_flight_confirmation";
        } else if (textMessage.contains("issue")) {
            return "sample_issue_resolution";
        } else if (textMessage.contains("purchase")) {
            return "sample_purchase_feedback";
        } else if (textMessage.contains("shipping")) {
            return "sample_shipping_confirmation";
        }

        return "hello_world";
    }
}

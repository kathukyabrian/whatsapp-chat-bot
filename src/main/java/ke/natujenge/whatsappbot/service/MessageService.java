package ke.natujenge.whatsappbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ke.natujenge.whatsappbot.config.ApplicationProperties;
import ke.natujenge.whatsappbot.dto.Message;
import ke.natujenge.whatsappbot.dto.ProductItem;
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
    private final CatalogService catalogService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MessageService(ApplicationProperties applicationProperties, TemplateService templateService, CatalogService catalogService) {
        this.applicationProperties = applicationProperties;
        this.templateService = templateService;
        this.catalogService = catalogService;
    }

    public void processMessages(List<Message> messages) {
        log.debug("about to process {} messages", messages.size());

        for (Message message : messages) {
            // get the textMessage
            String textMessage = message.getText().getBody();

            // resolve template
//            String templateName = resolveTemplate(textMessage);

            // resolve phoneNumber
            String phoneNumber = message.getFrom();

            try {
                replyPlainText(textMessage, phoneNumber);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void replyPlainText(String textMessage, String phoneNumber) throws IOException {
        Map<String, Object> message = prepareMessage(textMessage, phoneNumber);

        String endpoint = applicationProperties.getBaseUrl()
                + applicationProperties.getVersion() + "/"
                + applicationProperties.getPhoneNumberId()
                + "/messages";

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + applicationProperties.getAccessToken());

        log.debug("about to send message to endpoint : {} message : {} with headers : {}", endpoint, message, headerMap);

        String request = objectMapper.writeValueAsString(message);

        String response = HttpUtil.post(endpoint, request, headerMap, MediaType.get("application/json; charset=utf-8"));
        log.debug("Got response : {}", response);
    }

    private void reply(String phoneNumber, String templateName) throws IOException {
        List<Template> templates = templateService.getTemplates();

        Template resolvedTemplate = null;
        for (Template template : templates) {
            if (template.getName().equals(templateName)) {
                resolvedTemplate = template;
                break;
            }
        }

        // prepare message
        Map<String, Object> message = new HashMap<>();
        message.put("messaging_product", "whatsapp");
        message.put("recipient_type", "individual");
        message.put("to", phoneNumber);
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

        log.debug("about to send message to endpoint : {} message : {} with headers : {}", endpoint, message, headerMap);

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

    public Map<String, Object> prepareMessage(String textMessage, String phoneNumber) {
        Map<String, Object> message = new HashMap<>();
        message.put("messaging_product", "whatsapp");
        message.put("recipient_type", "individual");
        message.put("to", phoneNumber);
        message.put("type", "text");


        if (textMessage != null) {
            textMessage = textMessage.toLowerCase();
        }

        String body = "";
        if (textMessage.contains("jumba") || textMessage.contains("hello") || textMessage.contains("hi")) {
            body = "```Thank you for contacting us. How may we be of service?```";
        } else if (textMessage.contains("payment")) {
            body = "Payment Info\n\n" +
                    "Mpesa Paybill: 4096829\n\n" +
                    "Account Number: (Your jumba order number)\n\n" +
                    "Equity Bank: Account Number - 0550283014032, Branch - Westlands\n\n" +
                    "Stanbic Bank: Account Number - 0100009561603, Branch - Westlands\n\n" +
                    "DTB: Account Number - 0764385001, Branch - Ronald Ngala\n\n" +
                    "Reference(Your Jumba Order Number)";
        } else if (textMessage.contains("exit")) {
            body = "Thank you for reaching out. We shall get back to you within 12 hours.\n" +
                    "For more, please call +254 797 749 757 or email : info@thejumba.com";
        } else if (textMessage.contains("catalog")) {
            List<ProductItem> products = catalogService.getCatalog();

            for (ProductItem product : products) {
                body += product.getId() + "." + " " + product.getProductName() + "\n";
            }
        } else if (textMessage.contains("help")) {
            body = "*jumba* - greetings\n" +
                    "*hi* - greetings\n" +
                    "*hello* - greetings\n" +
                    "*help* - get command list\n" +
                    "*payment* - to get payment info\n" +
                    "*catalog* - to view available products\n" +
                    "*exit* - to end the conversation";
        } else {
            body = "That's new to me, I only recognize one of the following commands.\n" +
                    "*jumba* - greetings\n" +
                    "*hi* - greetings\n" +
                    "*hello* - greetings\n" +
                    "*help* - get command list\n" +
                    "*payment* - to get payment info\n" +
                    "*catalog* - to view available products\n" +
                    "*exit* - to end the conversation";
        }


        // template stuff
        Map<String, Object> text = new HashMap<>();
        text.put("body", body);

        message.put("text", text);

        return message;
    }
}

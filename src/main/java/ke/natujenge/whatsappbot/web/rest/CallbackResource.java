package ke.natujenge.whatsappbot.web.rest;

import ke.natujenge.whatsappbot.config.ApplicationProperties;
import ke.natujenge.whatsappbot.dto.CallbackDTO;
import ke.natujenge.whatsappbot.dto.Message;
import ke.natujenge.whatsappbot.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/webhook")
public class CallbackResource {

    private final ApplicationProperties applicationProperties;

    private final MessageService messageService;

    public CallbackResource(ApplicationProperties applicationProperties, MessageService messageService) {
        this.applicationProperties = applicationProperties;
        this.messageService = messageService;
    }


    @PostMapping
    public void processCallback(@RequestBody CallbackDTO callbackDTO) {
        log.debug("Received callback from whatsapp cloud : {}", callbackDTO);

        // retrieve messages
        List<Message> messages = callbackDTO.getEntry().get(0).getChanges().get(0).getValue().getMessages();

        // schedule it to service for processing
        messageService.processMessages(messages);

    }

    @GetMapping
    public String validate(@RequestParam(name = "hub.mode") String mode, @RequestParam(name = "hub.verify_token") String verifyToken, @RequestParam("hub.challenge") String challenge) {
        log.debug("mode = {}, verifyToken = {}, challenge = {}", mode, verifyToken, challenge);
        String resolvedChallenge = "";
//        String verificationToken = applicationProperties.getVerificationToken();
        if (mode != null && verifyToken != null) {
            if (mode.equals("subscribe") && verifyToken.equals(applicationProperties.getVerificationToken())) {
                log.debug("Webhook verified");
                resolvedChallenge = challenge;
            }
        }

        return resolvedChallenge;
    }
}

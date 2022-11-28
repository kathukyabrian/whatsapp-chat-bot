package ke.natujenge.whatsappbot.web.rest;

import ke.natujenge.whatsappbot.config.ApplicationProperties;
import ke.natujenge.whatsappbot.dto.CallbackDTO;
import ke.natujenge.whatsappbot.dto.Change;
import ke.natujenge.whatsappbot.dto.Message;
import ke.natujenge.whatsappbot.dto.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/webhook")
public class CallbackResource {

    private ApplicationProperties applicationProperties;

    @PostMapping
    public void processCallback(@RequestBody Map<Object, Object> callbackDTO) {
        log.debug("Received callback from whatsapp cloud : {}", callbackDTO);


        // resolve phone number (sender)
        Change change = ((List<Change>) callbackDTO.get("entry")).get(0);

        Value value = change.getValue();

        List<Message> messages = value.getMessages();
        log.debug("Retrieved messages : {}", messages);
        // eg hi, a way of responding

        // push to service for response creation


    }

    @GetMapping
    public String validate(@RequestParam(name = "hub.mode") String mode, @RequestParam(name = "hub.verify_token") String verifyToken, @RequestParam("hub.challenge") String challenge) {
        log.debug("mode = {}, verifyToken = {}, challenge = {}", mode, verifyToken, challenge);
        String resolvedChallenge = "";
//        String verificationToken = applicationProperties.getVerificationToken();
        if (mode != null && verifyToken != null) {
            if (mode.equals("subscribe") && verifyToken.equals("thisisjustatest")) {
                log.debug("Webhook verified");
                resolvedChallenge = challenge;
            }
        }

        return resolvedChallenge;
    }
}

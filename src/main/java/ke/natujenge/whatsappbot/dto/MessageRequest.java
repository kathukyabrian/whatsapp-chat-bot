package ke.natujenge.whatsappbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageRequest {
    @JsonProperty("messaging_product")
    private String messagingProduct;

    private String to;

    private String type;

    private Template template;

}

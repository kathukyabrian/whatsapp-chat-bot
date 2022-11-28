package ke.natujenge.whatsappbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.List;

@Data
public class Value {
    @JsonProperty("messaging_product")
    private String messagingProduct;

    private Metadata metadata;

    private List<Contact> contacts;

    private List<Message> messages;


}

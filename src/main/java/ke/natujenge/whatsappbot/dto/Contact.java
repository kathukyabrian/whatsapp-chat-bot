package ke.natujenge.whatsappbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

@Data
public class Contact {
    private Profile profile;

    @JsonProperty("wa_id")
    private String waId;
}

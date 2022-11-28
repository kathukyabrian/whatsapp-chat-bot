package ke.natujenge.whatsappbot.dto;

import lombok.Data;

import java.util.List;

@Data
public class CallbackDTO {
    private String object;

    private List<Entry> entry;
}

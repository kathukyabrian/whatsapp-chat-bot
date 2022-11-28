package ke.natujenge.whatsappbot.dto;

import lombok.Data;

import java.util.List;

@Data
public class Entry {
    private String id;

    private List<Change> changes;
}

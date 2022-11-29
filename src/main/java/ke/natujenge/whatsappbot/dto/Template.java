package ke.natujenge.whatsappbot.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Template {
    private String name;
    private List<Map<Object, Object>> components;
    private String language;
    private String status;
    private String category;
    private String id;
}

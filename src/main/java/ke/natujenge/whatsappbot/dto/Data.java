package ke.natujenge.whatsappbot.dto;

import java.util.List;
import java.util.Map;

@lombok.Data
public class Data {
    private List<Template> data;

    private Map<Object, Object> paging;
}

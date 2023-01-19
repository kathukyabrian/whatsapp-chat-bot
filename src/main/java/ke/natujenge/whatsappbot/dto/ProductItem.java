package ke.natujenge.whatsappbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductItem {
    private String id;
    @JsonProperty("product_name")
    private String productName;
    private String status;
}

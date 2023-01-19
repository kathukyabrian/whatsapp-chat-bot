package ke.natujenge.whatsappbot.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListProducts {
    private List<ProductItem> items;
}

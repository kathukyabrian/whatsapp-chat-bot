package ke.natujenge.whatsappbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProfile {
    private String userId;

    private String phoneNo;

    private String name;

    private String email;
}

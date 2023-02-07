package ke.natujenge.whatsappbot.service;

import ke.natujenge.whatsappbot.dto.CustomerProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthService {
    final Map<String, CustomerProfile> users = new HashMap<>();

    @PostConstruct
    void setup() {
        users.put("+254720076731", new CustomerProfile("1", "+254720076731", "Martin", "test@mnt.dev"));
        users.put("+254740272915", new CustomerProfile("2", "+254740272915", "Brian", "test@brian.dev"));
        users.put("+254703536688", new CustomerProfile("3", "+254703536688", "Waithaka", "test@weshy.dev"));
    }

    public CustomerProfile getUser(String phoneNumber) {
        // Invoke jumbaAPI to get user
        return users.get(phoneNumber);
    }
}

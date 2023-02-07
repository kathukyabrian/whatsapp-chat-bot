package ke.natujenge.whatsappbot.repository;


import ke.natujenge.whatsappbot.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByPhoneNumber(String phoneNumber);
}

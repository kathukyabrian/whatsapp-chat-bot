package ke.natujenge.whatsappbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ke.natujenge.whatsappbot.domain.Cart;
import ke.natujenge.whatsappbot.domain.CartItem;
import ke.natujenge.whatsappbot.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public Cart addToCart(String phoneNumber, CartItem item) {
        // Query cart by userId
        Optional<Cart> optionalCart = cartRepository.findByPhoneNumber(phoneNumber);
        List<CartItem> cartItems = new ArrayList<>();

        // Add item to cart
        Cart cart = null;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
            try {
                cartItems = objectMapper.readValue(cart.getItems(), new TypeReference<List<CartItem>>() {
                });
                cartItems.add(item);
                String cartItemsStr = objectMapper.writeValueAsString(cartItems);
                cart.setItems(cartItemsStr);
                cart.setUpdatedOn(System.currentTimeMillis());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Create cart
            cart = new Cart();
            cartItems.add(item);
            String cartItemsStr = null;
            try {
                cartItemsStr = objectMapper.writeValueAsString(cartItems);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            cart.setItems(cartItemsStr);
            cart.setPhoneNumber(phoneNumber);
            cart.setCreatedOn(System.currentTimeMillis());
        }
        return cartRepository.save(cart);
    }

    public Cart getCart(String phoneNumber) {
        Optional<Cart> optionalCart = cartRepository.findByPhoneNumber(phoneNumber);
        return optionalCart.orElse(null);
    }
}

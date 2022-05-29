package ltw.groupjava.app.service.bean;

import lombok.RequiredArgsConstructor;
import ltw.groupjava.app.entity.Cart;
import ltw.groupjava.app.entity.CartItem;
import ltw.groupjava.app.entity.Product;
import ltw.groupjava.app.repository.CartItemRepo;
import ltw.groupjava.app.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemServiceBean implements CartItemService {
    private final CartItemRepo cartItemRepo;
    @Override
    public void save(CartItem cartItem) {
        cartItemRepo.save(cartItem);
    }

    @Override
    public List<CartItem> findByCart(Cart cart) {
        return cartItemRepo.findByCart(cart);
    }

    @Override
    public CartItem findByID(String id) {
        UUID uuid = UUID.fromString(id);

        return cartItemRepo.findById(uuid).orElse(null);
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemRepo.delete(cartItem);
    }

    @Override
    public CartItem findByCartAndProduct(Cart cart, Product product) {
        return cartItemRepo.findByCartAndProduct(cart, product);
    }
}

package ltw.groupjava.app.service.bean;

import lombok.AllArgsConstructor;
import ltw.groupjava.app.entity.Cart;
import ltw.groupjava.app.entity.User;
import ltw.groupjava.app.repository.CartRepo;
import ltw.groupjava.app.service.CartService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceBean implements CartService {

    private final CartRepo cartRepo;

    @Override
    public void save(Cart cart) {
        cartRepo.save(cart);
    }

    @Override
    public void createNewCartWithUser(User user) {
        Cart cart = new Cart();
        cart.setOwner(user);

        cartRepo.save(cart);
    }

    @Override
    public Cart findByOwner(User user) {
        return cartRepo.findByOwner(user);
    }
}

package ltw.groupjava.app.service;

import ltw.groupjava.app.entity.Cart;
import ltw.groupjava.app.entity.User;

public interface CartService {
    void save(Cart cart);

    void createNewCartWithUser(User user);

    Cart findByOwner(User user);
}

package ltw.groupjava.app.service;

import ltw.groupjava.app.entity.Cart;
import ltw.groupjava.app.entity.CartItem;
import ltw.groupjava.app.entity.Product;

import java.util.List;

public interface CartItemService {
    void save(CartItem cartItem);

    List<CartItem> findByCart(Cart cart);

    CartItem findByID(String id);

    void delete(CartItem cartItem);

    CartItem findByCartAndProduct(Cart cart, Product product);
}

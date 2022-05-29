package ltw.groupjava.app.repository;

import ltw.groupjava.app.entity.Cart;
import ltw.groupjava.app.entity.CartItem;
import ltw.groupjava.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public interface CartItemRepo extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByCart(Cart cart);
    CartItem findByCartAndProduct(Cart cart, Product product);
}

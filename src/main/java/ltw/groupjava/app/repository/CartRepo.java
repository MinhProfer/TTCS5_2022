package ltw.groupjava.app.repository;

import ltw.groupjava.app.entity.Cart;
import ltw.groupjava.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepo extends JpaRepository<Cart, UUID> {
    Cart findByOwner(User user);
}

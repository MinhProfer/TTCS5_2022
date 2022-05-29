package ltw.groupjava.app.repository;

import ltw.groupjava.app.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public interface ProductRepo extends JpaRepository<Product, UUID> {
    List<Product> findByType(String type);
    List<Product> findByType(String type, Sort sort);
}

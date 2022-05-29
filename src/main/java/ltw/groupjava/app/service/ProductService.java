package ltw.groupjava.app.service;

import ltw.groupjava.app.entity.Product;
import ltw.groupjava.app.entity.ProductType;
import ltw.groupjava.app.entity.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    int VALID_INPUT = 0;
    List<Product> findAll();
    void saveAll(List<Product> products);

    int validate(ProductDto productDto);

    void saveDto(ProductDto productDto);

    Product findById(UUID id);

    ProductDto toDto(Product product);

    List<Product> findByType(ProductType type);

    List<Product> findAll(String sortCondition);

    List<Product> findByType(ProductType type, String sortCondition);

    void save(Product product);
}

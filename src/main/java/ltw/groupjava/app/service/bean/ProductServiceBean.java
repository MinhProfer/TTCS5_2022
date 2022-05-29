package ltw.groupjava.app.service.bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ltw.groupjava.app.entity.Product;
import ltw.groupjava.app.entity.ProductType;
import ltw.groupjava.app.entity.dto.ProductDto;
import ltw.groupjava.app.repository.ProductRepo;
import ltw.groupjava.app.service.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceBean implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public void saveAll(List<Product> products) {
        productRepo.saveAll(products);
    }

    @Override
    public void save(Product product) {
        productRepo.save(product);
    }
    @Override
    public int validate(ProductDto productDto) {
        return VALID_INPUT;
    }

    @Override
    public void saveDto(ProductDto productDto) {
        log.info(productDto.toString());
        Product product = new Product();
        UUID id = convertUuid(productDto.getId());
        if (id != null) {
            product.setId(id);
        }
        product.setName(productDto.getName());
        product.setPrice(Double.parseDouble(productDto.getPrice()));
        product.setRemaining(Integer.parseInt(productDto.getRemaining()));
        product.setType(ProductType.fromId(productDto.getType()));
        product.setDescription(productDto.getDescription());
        product.setImgId(productDto.getImgId());

        productRepo.save(product);
    }

    private UUID convertUuid(String idString) {
        try {
            return UUID.fromString(idString);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Product findById(UUID id) {
        return productRepo.findById(id).orElse(new Product());
    }

    @Override
    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId().toString())
                .name(product.getName())
                .price(product.getPrice().toString())
                .remaining(product.getRemaining().toString())
                .type(product.getType().getId())
                .description(product.getDescription())
                .build();

    }

    @Override
    public List<Product> findByType(ProductType type) {
        return productRepo.findByType(type.getId());
    }

    @Override
    public List<Product> findAll(String sortCondition) {
        Pair<String, Boolean> sort = parseSortCondition(sortCondition);
        if (sort == null) {
            return findAll();
        }
        return productRepo.findAll(Sort.by(
                sort.getSecond() ? Sort.Direction.DESC : Sort.Direction.ASC,
                sort.getFirst()));
    }

    @Override
    public List<Product> findByType(ProductType type, String sortCondition) {
        Pair<String, Boolean> sort = parseSortCondition(sortCondition);
        if (sort == null) {
            return findByType(type);
        }
        return productRepo.findByType(type.getId(), Sort.by(
                sort.getSecond() ? Sort.Direction.DESC : Sort.Direction.ASC,
                sort.getFirst()));
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    private Pair<String, Boolean> parseSortCondition(String sortCondition) {
        String[] words = sortCondition.split(",");
        if (words[0] == null || words[0].isEmpty()) {
            return null;
        }
        String sortProperty = words[0];
        boolean sortDir = false;
        if (words.length == 2) {
            sortDir = words[1].equalsIgnoreCase("desc");
        }
        return Pair.of(sortProperty, sortDir);
    }
}

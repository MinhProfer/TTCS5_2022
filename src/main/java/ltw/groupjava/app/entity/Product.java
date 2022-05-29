package ltw.groupjava.app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity(name = "app_product")
@Table(name = "app_product")
public class Product extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "remaining")
    private Integer remaining;
    @Column(name = "type")
    private String type;

    @Column(name = "img_id")
    private String imgId;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "sale_off")
    private Integer saleOff;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems = new ArrayList<>();

    public ProductType getType() {
        return ProductType.fromId(type);
    }

    public void setType(ProductType type) {
        this.type = type.getId();
    }
}

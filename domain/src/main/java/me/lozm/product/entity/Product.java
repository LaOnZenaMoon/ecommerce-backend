package me.lozm.product.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.lozm.common.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "PRODUCT")
@DynamicUpdate
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Version
    private Integer version;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "SALES_START_DATE")
    private LocalDateTime salesStartDateTime;

    @Column(name = "SALES_END_DATE")
    private LocalDateTime salesEndDateTime;


    public boolean canBeOrdered(Integer quantity) {
        Assert.notNull(quantity, "quantity cannot be null");
        Assert.isTrue(quantity > 0, "quantity cannot be less than 1");

        return this.quantity - quantity >= 0;
    }

    public void updateQuantity(Integer quantity) {
        Assert.notNull(quantity, "quantity cannot be null");

        this.quantity += quantity;
    }

}

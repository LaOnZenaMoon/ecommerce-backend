package me.lozm.order.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.lozm.common.entity.BaseEntity;
import me.lozm.product.dto.ProductOrderResponseDto;
import me.lozm.product.entity.Product;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "PRODUCT_ORDERS")
public class ProductOrders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ORDER_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "PRODUCT_ID", insertable = false, updatable = false)
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Orders orders;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;


    public ProductOrders(ProductOrderResponseDto product, Orders orders, Integer quantity, BigDecimal price) {
        this.productId = product.getId();
        this.orders = orders;
        this.quantity = quantity;
        this.price = price;
    }

}

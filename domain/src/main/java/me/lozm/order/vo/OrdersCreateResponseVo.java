package me.lozm.order.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.lozm.order.entity.Orders;
import me.lozm.order.entity.ProductOrders;
import me.lozm.order.entity.UserOrders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdersCreateResponseVo {

    private Long productOrdersId;
    private Integer productOrdersQuantity;
    private BigDecimal productOrdersPrice;

    private Long productId;
    private String productName;

    private Long ordersId;
    private BigDecimal ordersTotalPrice;
    private Long ordersCreatedBy;
    private LocalDateTime ordersCreatedDateTime;
    private Long ordersLastModifiedBy;
    private LocalDateTime ordersLastModifiedDateTime;

    private Long userId;
    private String userName;


    public OrdersCreateResponseVo(Orders orders, ProductOrders productOrders, UserOrders userOrders) {
        productOrdersId = productOrders.getId();
        productOrdersQuantity = productOrders.getQuantity();
        productOrdersPrice = productOrders.getPrice();

        productId = productOrders.getProduct().getId();
        productName = productOrders.getProduct().getName();

        ordersId = orders.getId();
        ordersTotalPrice = orders.getTotalPrice();
        ordersCreatedBy = orders.getCreatedBy();
        ordersCreatedDateTime = orders.getCreatedDateTime();
        ordersLastModifiedBy = orders.getLastModifiedBy();
        ordersLastModifiedDateTime = orders.getLastModifiedDateTime();

        userId = userOrders.getUser().getId();
        userName = userOrders.getUser().getName();
    }

}

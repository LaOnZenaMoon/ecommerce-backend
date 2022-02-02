package me.lozm.order.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.lozm.order.entity.Orders;
import me.lozm.order.entity.ProductOrders;
import me.lozm.product.dto.ProductOrderResponseDto;
import me.lozm.user.dto.UserInfoResponseDto;

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


    public OrdersCreateResponseVo(Orders orders, ProductOrders productOrders, UserInfoResponseDto userInfoResponseDto, ProductOrderResponseDto orderedProduct) {
        productOrdersId = productOrders.getId();
        productOrdersQuantity = productOrders.getQuantity();
        productOrdersPrice = productOrders.getPrice();

        productId = orderedProduct.getId();
        productName = orderedProduct.getName();

        ordersId = orders.getId();
        ordersTotalPrice = orders.getTotalPrice();
        ordersCreatedBy = orders.getCreatedBy();
        ordersCreatedDateTime = orders.getCreatedDateTime();
        ordersLastModifiedBy = orders.getLastModifiedBy();
        ordersLastModifiedDateTime = orders.getLastModifiedDateTime();

        userId = userInfoResponseDto.getId();
        userName = userInfoResponseDto.getName();
    }

}

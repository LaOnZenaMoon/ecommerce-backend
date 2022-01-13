package me.lozm.order.vo;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrdersInfoVo {

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

}

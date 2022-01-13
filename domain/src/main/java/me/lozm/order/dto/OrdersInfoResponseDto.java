package me.lozm.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import me.lozm.common.dto.BaseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdersInfoResponseDto {

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

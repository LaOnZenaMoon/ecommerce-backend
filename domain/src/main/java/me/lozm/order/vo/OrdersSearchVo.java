package me.lozm.order.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrdersSearchVo {

    private Long productId;
    private Long orderId;
    private Long userId;

    public OrdersSearchVo(Long orderId) {
        this.orderId = orderId;
    }

}

package me.lozm.order.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.lozm.common.vo.BaseVo;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdersCreateRequestVo extends BaseVo {

    private Long userId;
    private Long productId;
    private Integer quantity;


    public OrdersCreateRequestVo(Long userId, Long productId, Integer quantity) {
        Assert.notNull(userId, "userId cannot be null");
        Assert.notNull(productId, "productId cannot be null");
        Assert.notNull(quantity, "quantity cannot be null");
        Assert.isTrue(quantity > 0, "quantity cannot be less than 1");

        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

}

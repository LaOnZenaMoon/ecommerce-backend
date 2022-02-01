package me.lozm.product.vo;

import lombok.Getter;
import me.lozm.common.vo.BaseVo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductOrderVo extends BaseVo {

    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime salesStartDateTime;
    private LocalDateTime salesEndDateTime;

}

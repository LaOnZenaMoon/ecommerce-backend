package me.lozm.product.dto;

import lombok.Getter;
import me.lozm.common.dto.BaseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductOrderResponseDto extends BaseDto {

    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime salesStartDateTime;
    private LocalDateTime salesEndDateTime;

}

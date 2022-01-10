package me.lozm.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import me.lozm.common.dto.BaseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoResponseDto extends BaseDto {

    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime salesStartDateTime;
    private LocalDateTime salesEndDateTime;

}

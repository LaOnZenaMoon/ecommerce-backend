package me.lozm.product.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductCreateRequestDto {

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than 2 characters")
    private String name;

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;

    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

    @NotNull(message = "SalesStartDateTime cannot be null")
    private LocalDateTime salesStartDateTime;

    @NotNull(message = "SalesEndDateTime cannot be null")
    private LocalDateTime salesEndDateTime;

}

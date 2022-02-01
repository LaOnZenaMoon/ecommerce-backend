package me.lozm.product.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class ProductOrderRequestDto {

    @NotNull(message = "Name cannot be null")
    private Long id;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "quantity cannot be less than 1")
    private Integer quantity;


    public ProductOrderRequestDto(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

}

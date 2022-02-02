package me.lozm.api.client;

import me.lozm.product.dto.ProductOrderRequestDto;
import me.lozm.product.dto.ProductOrderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("product-service-app")
public interface ProductServiceClient {

    @PutMapping("products/orders")
    ResponseEntity<ProductOrderResponseDto> orderProduct(ProductOrderRequestDto requestDto);

}

package me.lozm.api.client;

import feign.Response;
import me.lozm.product.dto.ProductOrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("product-service-app")
public interface ProductServiceClient {

    @PutMapping("products/orders")
    Response orderProduct(ProductOrderRequestDto requestDto);

}

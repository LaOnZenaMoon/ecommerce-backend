package me.lozm.product.service;

import lombok.RequiredArgsConstructor;
import me.lozm.product.entity.Product;
import me.lozm.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ProductHelperService {

    private final ProductRepository productRepository;


    public Optional<Product> findProduct(Long productId) {
        return productRepository.findById(productId);
    }

    public Product getProduct(Long productId) {
        return findProduct(productId).orElseThrow(() -> new IllegalArgumentException(format("상품 정보가 존재하지 않습니다. 상품 ID: %s", productId)));
    }

}

package me.lozm.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lozm.product.entity.Product;
import me.lozm.product.repository.ProductRepository;
import me.lozm.product.service.ProductHelperService;
import me.lozm.product.vo.ProductCreateVo;
import me.lozm.product.vo.ProductInfoVo;
import me.lozm.user.entity.User;
import me.lozm.user.vo.OrderInfoVo;
import me.lozm.user.vo.UserCreateVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static me.lozm.utils.MapperUtils.mapStrictly;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductHelperService productHelperService;


    @Override
    public List<ProductInfoVo> getProductList() {
        return productRepository.findAll()
                .stream()
                .map(product -> mapStrictly(product, ProductInfoVo.class))
                .collect(toList());
    }

    @Override
    public ProductInfoVo getProductDetail(Long productId) {
        Product product = productHelperService.getProduct(productId);
        return mapStrictly(product, ProductInfoVo.class);
    }

    @Override
    @Transactional
    public ProductCreateVo createProduct(ProductCreateVo productCreateVo) {
        Product product = mapStrictly(productCreateVo, Product.class);
        Product savedProduct = productRepository.save(product);
        return mapStrictly(savedProduct, ProductCreateVo.class);
    }

}

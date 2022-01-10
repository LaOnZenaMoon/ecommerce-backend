package me.lozm.api.service;

import me.lozm.product.vo.ProductCreateVo;
import me.lozm.product.vo.ProductInfoVo;
import me.lozm.user.vo.UserCreateVo;

import java.util.List;

public interface ProductService {

    List<ProductInfoVo> getProductList();

    ProductInfoVo getProductDetail(Long productId);

    ProductCreateVo createProduct(ProductCreateVo productCreateVo);

}

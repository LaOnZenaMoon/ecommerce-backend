package me.lozm.api.service;

import me.lozm.common.vo.PageVo;
import me.lozm.common.vo.SearchVo;
import me.lozm.product.vo.ProductCreateVo;
import me.lozm.product.vo.ProductInfoVo;
import me.lozm.product.vo.ProductOrderVo;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductInfoVo> getProductList(PageVo pageVo, SearchVo searchVo);

    ProductInfoVo getProductDetail(Long productId);

    ProductCreateVo createProduct(ProductCreateVo productCreateVo);

    ProductOrderVo orderProduct(ProductOrderVo productOrderVo);

}

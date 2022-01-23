package me.lozm.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lozm.common.vo.PageVo;
import me.lozm.common.vo.SearchVo;
import me.lozm.product.entity.Product;
import me.lozm.product.repository.ProductRepository;
import me.lozm.product.service.ProductHelperService;
import me.lozm.product.vo.ProductCreateVo;
import me.lozm.product.vo.ProductInfoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;
import static me.lozm.utils.MapperUtils.mapStrictly;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductHelperService productHelperService;


    @Override
    public Page<ProductInfoVo> getProductList(PageVo pageVo, SearchVo searchVo) {
        List<ProductInfoVo> productList = productRepository.getProductList(pageVo, searchVo);
        long totalCount = productRepository.getProductTotalCount(pageVo, searchVo);
        return new PageImpl<>(productList, pageVo.getPageRequest(), totalCount);
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

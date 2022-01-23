package me.lozm.product.repository;

import me.lozm.common.vo.PageVo;
import me.lozm.common.vo.SearchVo;
import me.lozm.product.vo.ProductInfoVo;

import java.util.List;

public interface ProductRepositoryCustom {

    List<ProductInfoVo> getProductList(PageVo pageVo, SearchVo searchVo);

    long getProductTotalCount(PageVo pageVo, SearchVo searchVo);

}

package me.lozm.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.lozm.common.vo.PageVo;
import me.lozm.common.vo.SearchVo;
import me.lozm.product.entity.QProduct;
import me.lozm.product.vo.ProductInfoVo;
import me.lozm.user.vo.UserInfoVo;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static me.lozm.product.entity.QProduct.product;


@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ProductInfoVo> getProductList(PageVo pageVo, SearchVo searchVo) {
        return jpaQueryFactory
                .select(Projections.fields(
                        ProductInfoVo.class,
                        product.id.as("id"),
                        product.name.as("name"),
                        product.quantity.as("quantity"),
                        product.price.as("price"),
                        product.salesStartDateTime.as("salesStartDateTime"),
                        product.salesEndDateTime.as("salesEndDateTime"),
                        product.createdBy.as("createdBy"),
                        product.lastModifiedBy.as("lastModifiedBy"),
                        product.use.as("use"),
                        product.createdDateTime.as("createdDateTime"),
                        product.lastModifiedDateTime.as("lastModifiedDateTime")
                ))
                .from(product)
                .where()
                .orderBy(product.id.desc())
                .offset(pageVo.getPageRequest().getOffset())
                .limit(pageVo.getPageRequest().getPageSize())
                .fetch();
    }

    @Override
    public long getProductTotalCount(PageVo pageVo, SearchVo searchVo) {
        return jpaQueryFactory
                .selectFrom(product)
                .where()
                .orderBy(product.id.desc())
                .offset(pageVo.getPageRequest().getOffset())
                .limit(pageVo.getPageRequest().getPageSize())
                .fetchCount();
    }

}

package me.lozm.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.lozm.common.vo.PageVo;
import me.lozm.common.vo.SearchVo;
import me.lozm.order.vo.OrdersInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.lozm.order.entity.QOrders.orders;
import static me.lozm.order.entity.QProductOrders.productOrders;
import static me.lozm.order.entity.QUserOrders.userOrders;
import static me.lozm.product.entity.QProduct.product;
import static me.lozm.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<OrdersInfoVo> getOrdersList(PageVo pageVo, SearchVo searchVo) {
        return jpaQueryFactory
                .select(Projections.fields(
                        OrdersInfoVo.class,
                        productOrders.id.as("productOrdersId"),
                        productOrders.quantity.as("productOrdersQuantity"),
                        productOrders.price.as("productOrdersPrice"),
                        product.id.as("productId"),
                        product.name.as("productName"),
                        orders.id.as("ordersId"),
                        orders.totalPrice.as("ordersTotalPrice"),
                        orders.createdBy.as("ordersCreatedBy"),
                        orders.createdDateTime.as("ordersCreatedDateTime"),
                        orders.lastModifiedBy.as("ordersLastModifiedBy"),
                        orders.lastModifiedDateTime.as("ordersLastModifiedDateTime"),
                        user.id.as("userId"),
                        user.name.as("userName")
                ))
                .from(orders)
                .innerJoin(userOrders).on(userOrders.orders.id.eq(orders.id))
                .innerJoin(user).on(user.id.eq(userOrders.user.id))
                .innerJoin(productOrders).on(productOrders.orders.id.eq(orders.id))
                .innerJoin(product).on(product.id.eq(productOrders.product.id))
                .orderBy(orders.id.desc())
                .offset(pageVo.getPageRequest().getOffset())
                .limit(pageVo.getPageRequest().getPageSize())
                .fetch();
    }

    @Override
    public long getUserTotalCount(PageVo pageVo, SearchVo searchVo) {
        return jpaQueryFactory
                .selectFrom(orders)
                .innerJoin(userOrders).on(userOrders.orders.id.eq(orders.id))
                .innerJoin(user).on(user.id.eq(userOrders.user.id))
                .innerJoin(productOrders).on(productOrders.orders.id.eq(orders.id))
                .innerJoin(product).on(product.id.eq(productOrders.product.id))
                .orderBy(orders.id.desc())
                .offset(pageVo.getPageRequest().getOffset())
                .limit(pageVo.getPageRequest().getPageSize())
                .fetchCount();
    }

}

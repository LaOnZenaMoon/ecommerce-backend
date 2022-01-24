package me.lozm.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lozm.common.vo.PageVo;
import me.lozm.common.vo.SearchVo;
import me.lozm.order.entity.Orders;
import me.lozm.order.entity.ProductOrders;
import me.lozm.order.entity.UserOrders;
import me.lozm.order.repository.OrdersRepository;
import me.lozm.order.repository.ProductOrdersRepository;
import me.lozm.order.repository.UserOrdersRepository;
import me.lozm.order.vo.OrdersCreateRequestVo;
import me.lozm.order.vo.OrdersCreateResponseVo;
import me.lozm.order.vo.OrdersInfoVo;
import me.lozm.product.entity.Product;
import me.lozm.product.service.ProductHelperService;
import me.lozm.user.entity.User;
import me.lozm.user.service.UserHelperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final ProductOrdersRepository productOrdersRepository;
    private final UserOrdersRepository userOrdersRepository;
    private final UserHelperService userHelperService;
    private final ProductHelperService productHelperService;


    @Override
    public Page<OrdersInfoVo> getOrdersList(PageVo pageVo, SearchVo searchVo) {
        List<OrdersInfoVo> ordersList = ordersRepository.getOrdersList(pageVo, searchVo);
        long totalCount = ordersRepository.getUserTotalCount(pageVo, searchVo);
        return new PageImpl<>(ordersList, pageVo.getPageRequest(), totalCount);
    }

    @Override
    @Transactional
    public OrdersCreateResponseVo createOrders(OrdersCreateRequestVo ordersCreateRequestVo) {
        final Long orderedProductId = ordersCreateRequestVo.getProductId();
        final Integer orderedQuantity = ordersCreateRequestVo.getQuantity();

        User user = userHelperService.getUser(ordersCreateRequestVo.getUserId());

        Product product = productHelperService.getProduct(orderedProductId);
        if (!product.canBeOrdered(orderedQuantity)) {
            throw new IllegalArgumentException(
                    format("주문하신 상품 수량만큼 상품을 주문할 수 없습니다. 상품 ID: %s, 주문한 상품 수량: %d, 남은 상품 수량: %d",
                    orderedProductId, orderedQuantity, product.getQuantity())
            );
        }
        product.updateQuantity(-orderedQuantity);

        final BigDecimal productPrice = product.getPrice();
        final BigDecimal orderedTotalPrice = productPrice.multiply(BigDecimal.valueOf(orderedQuantity));
        Orders orders = new Orders(orderedTotalPrice);
        ordersRepository.save(orders);

        ProductOrders productOrders = new ProductOrders(product, orders, orderedQuantity, productPrice);
        productOrdersRepository.save(productOrders);

        UserOrders userOrders = new UserOrders(user, orders);
        userOrdersRepository.save(userOrders);

        return new OrdersCreateResponseVo(orders, productOrders, userOrders);
    }

}

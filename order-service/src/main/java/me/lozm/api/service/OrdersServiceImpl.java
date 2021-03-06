package me.lozm.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lozm.api.client.AuthServiceClient;
import me.lozm.api.client.ProductServiceClient;
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
import me.lozm.product.dto.ProductOrderRequestDto;
import me.lozm.product.dto.ProductOrderResponseDto;
import me.lozm.user.dto.UserInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
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
    private final AuthServiceClient authServiceClient;
    private final ProductServiceClient productServiceClient;


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
        final Long userId = ordersCreateRequestVo.getUserId();

        ResponseEntity<UserInfoResponseDto> userDetailResponseEntity = authServiceClient.getUserDetail(userId);
        if (!userDetailResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException(format("?????? ?????? ????????? ?????????????????????.(Status code: %s) ?????? ID: %d",
                    userDetailResponseEntity.getStatusCode(), userId));
        }
        UserInfoResponseDto userDetail = userDetailResponseEntity.getBody();

        //TODO order-service & product-service ?????? ?????? ??? ???????????? ????????? Kafka ??????
        // 1. ?????? ??????
        // 2. ?????? ????????????
        ResponseEntity<ProductOrderResponseDto> orderedProductResponseEntity = productServiceClient.orderProduct(new ProductOrderRequestDto(orderedProductId, orderedQuantity));
        if (orderedProductResponseEntity.getStatusCode().is4xxClientError()) {
            throw new IllegalArgumentException(format("????????? ?????? ?????? ???????????????.(Status code: %s) > ?????? ID: %d",
                    orderedProductResponseEntity.getStatusCode(), orderedProductId));
        } else if (orderedProductResponseEntity.getStatusCode().is5xxServerError()) {
            throw new RuntimeException(format("?????? ????????? ?????????????????????.(Status code: %s) > ?????? ID: %d",
                    orderedProductResponseEntity.getStatusCode(), orderedProductId));
        }
        ProductOrderResponseDto orderedProduct = orderedProductResponseEntity.getBody();

        final BigDecimal productPrice = orderedProduct.getPrice();
        final BigDecimal orderedTotalPrice = productPrice.multiply(BigDecimal.valueOf(orderedQuantity));
        Orders orders = new Orders(orderedTotalPrice);
        ordersRepository.save(orders);

        ProductOrders productOrders = new ProductOrders(orderedProduct, orders, orderedQuantity, productPrice);
        productOrdersRepository.save(productOrders);

        UserOrders userOrders = new UserOrders(userDetail.getId(), orders);
        userOrdersRepository.save(userOrders);

        return new OrdersCreateResponseVo(orders, productOrders, userDetail, orderedProduct);
    }

}

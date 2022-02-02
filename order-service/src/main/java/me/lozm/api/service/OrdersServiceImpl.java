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
            throw new IllegalArgumentException(format("고객 정보 조회에 실패하였습니다.(Status code: %s) 고객 ID: %d",
                    userDetailResponseEntity.getStatusCode(), userId));
        }
        UserInfoResponseDto userDetail = userDetailResponseEntity.getBody();

        //TODO order-service & product-service 모듈 조회 및 업데이트 기능에 Kafka 적용
        // 1. 상품 조회
        // 2. 상품 업데이트
        ResponseEntity<ProductOrderResponseDto> orderedProductResponseEntity = productServiceClient.orderProduct(new ProductOrderRequestDto(orderedProductId, orderedQuantity));
        if (orderedProductResponseEntity.getStatusCode().is4xxClientError()) {
            throw new IllegalArgumentException(format("잘못된 상품 주문 요청입니다.(Status code: %s) > 상품 ID: %d",
                    orderedProductResponseEntity.getStatusCode(), orderedProductId));
        } else if (orderedProductResponseEntity.getStatusCode().is5xxServerError()) {
            throw new RuntimeException(format("상품 주문에 실패하였습니다.(Status code: %s) > 상품 ID: %d",
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

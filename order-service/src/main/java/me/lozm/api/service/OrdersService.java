package me.lozm.api.service;

import me.lozm.common.vo.PageVo;
import me.lozm.common.vo.SearchVo;
import me.lozm.order.vo.OrdersCreateRequestVo;
import me.lozm.order.vo.OrdersCreateResponseVo;
import me.lozm.order.vo.OrdersInfoVo;
import org.springframework.data.domain.Page;

public interface OrdersService {

    Page<OrdersInfoVo> getOrdersList(PageVo pageVo, SearchVo searchVo);

    OrdersCreateResponseVo createOrders(OrdersCreateRequestVo ordersCreateRequestVo);

}

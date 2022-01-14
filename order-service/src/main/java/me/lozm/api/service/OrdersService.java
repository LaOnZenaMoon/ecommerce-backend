package me.lozm.api.service;

import me.lozm.order.vo.OrdersCreateRequestVo;
import me.lozm.order.vo.OrdersCreateResponseVo;
import me.lozm.order.vo.OrdersInfoVo;
import me.lozm.order.vo.OrdersSearchVo;

import java.util.List;

public interface OrdersService {

    List<OrdersInfoVo> getOrdersList(OrdersSearchVo searchVo);

    OrdersCreateResponseVo createOrders(OrdersCreateRequestVo ordersCreateRequestVo);

}

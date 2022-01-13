package me.lozm.order.repository;

import me.lozm.order.vo.OrdersInfoVo;
import me.lozm.order.vo.OrdersSearchVo;

import java.util.List;

public interface OrdersRepositoryCustom {
    List<OrdersInfoVo> getOrderList(OrdersSearchVo searchVo);
}

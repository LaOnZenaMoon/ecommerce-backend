package me.lozm.order.repository;

import me.lozm.common.vo.PageVo;
import me.lozm.common.vo.SearchVo;
import me.lozm.order.vo.OrdersInfoVo;
import me.lozm.order.vo.OrdersSearchVo;

import java.util.List;

public interface OrdersRepositoryCustom {

    List<OrdersInfoVo> getOrdersList(PageVo pageVo, SearchVo searchVo);

    long getUserTotalCount(PageVo pageVo, SearchVo searchVo);

}

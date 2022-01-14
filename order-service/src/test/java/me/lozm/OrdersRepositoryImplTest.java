package me.lozm;

import me.lozm.order.repository.OrdersRepository;
import me.lozm.order.vo.OrdersInfoVo;
import me.lozm.order.vo.OrdersSearchVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OrdersRepositoryImplTest {

    @Autowired
    private OrdersRepository ordersRepository;


    @Test
    void getOrderListQueryTest() {
        // Given
        OrdersSearchVo searchVo = new OrdersSearchVo();

        // When
        List<OrdersInfoVo> orderList = ordersRepository.getOrderList(searchVo);

        // Then
        System.out.println("orderList = " + orderList);
    }

}
package me.lozm.order.repository;

import me.lozm.order.entity.UserOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrdersRepository extends JpaRepository<UserOrders, Long> {
}

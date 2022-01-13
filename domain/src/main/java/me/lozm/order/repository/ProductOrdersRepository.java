package me.lozm.order.repository;

import me.lozm.order.entity.ProductOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrdersRepository extends JpaRepository<ProductOrders, Long> {
}

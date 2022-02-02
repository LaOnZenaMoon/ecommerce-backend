package me.lozm.order.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.lozm.common.entity.BaseEntity;
import me.lozm.user.entity.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "USER_ORDERS")
public class UserOrders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ORDER_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "USER_ID", insertable = false, updatable = false)
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Orders orders;


    public UserOrders(User user, Orders orders) {
        this.user = user;
        this.orders = orders;
    }

    public UserOrders(Long userId, Orders orders) {
        this.userId = userId;
        this.orders = orders;
    }

}

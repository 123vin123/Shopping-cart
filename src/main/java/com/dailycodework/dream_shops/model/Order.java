package com.dailycodework.dream_shops.model;

import com.dailycodework.dream_shops.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
   private BigDecimal totalAmount;

    private LocalDate orderDate;
   @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

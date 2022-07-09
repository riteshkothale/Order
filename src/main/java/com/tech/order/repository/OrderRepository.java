package com.tech.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}

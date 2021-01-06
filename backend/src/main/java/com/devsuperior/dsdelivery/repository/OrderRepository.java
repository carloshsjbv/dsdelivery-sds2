package com.devsuperior.dsdelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsdelivery.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

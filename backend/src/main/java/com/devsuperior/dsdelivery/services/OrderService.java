package com.devsuperior.dsdelivery.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdelivery.dto.OrderDTO;
import com.devsuperior.dsdelivery.entities.Order;
import com.devsuperior.dsdelivery.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Transactional(readOnly = true) // readOnly = true evita lock no banco
	public List<OrderDTO> findAll() {

		List<Order> products = repository.findOrdersWithProducts();

		return products.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());

	}

}

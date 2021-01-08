package com.devsuperior.dsdelivery.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdelivery.dto.OrderDTO;
import com.devsuperior.dsdelivery.dto.ProductDTO;
import com.devsuperior.dsdelivery.entities.Order;
import com.devsuperior.dsdelivery.entities.OrderStatus;
import com.devsuperior.dsdelivery.entities.Product;
import com.devsuperior.dsdelivery.repository.OrderRepository;
import com.devsuperior.dsdelivery.repository.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true) // readOnly = true evita lock no banco
	public List<OrderDTO> findAll() {

		List<Order> products = orderRepository.findOrdersWithProducts();

		return products.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());

	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), Instant.now(),
				OrderStatus.PENDING);

		for (ProductDTO p : dto.getProducts()) {
			
			// Desta forma, o JPA cria uma entidade gerenciada por ele, para fins de criação do registro na tabela de associação.
			
			Product product = productRepository.getOne(p.getId());

			order.getProducts().add(product);
		}

		order = orderRepository.save(order);

		return new OrderDTO(order);
	}
	
	@Transactional
	public OrderDTO setDelivered(Long id) {
		
		// objeto gerenciado pelo JPA (não veio da base)
		Order order = orderRepository.getOne(id);
		order.setStatus(OrderStatus.DELIVERED);
		
		order = orderRepository.save(order);
		
		return new OrderDTO(order);
		
		
	}

}

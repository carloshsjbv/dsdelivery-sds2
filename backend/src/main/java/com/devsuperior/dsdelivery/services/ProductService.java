package com.devsuperior.dsdelivery.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdelivery.dto.ProductDTO;
import com.devsuperior.dsdelivery.entities.Product;
import com.devsuperior.dsdelivery.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;

	@Transactional(readOnly = true) // readOnly = true evita lock no banco  
	public List<ProductDTO> findAll()
	{
		
		List<Product> products = repository.findAllByOrderByNameAsc();
		
		return products.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
		
	}

}

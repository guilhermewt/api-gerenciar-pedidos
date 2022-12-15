package com.webserviceproject.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Product;
import com.webserviceproject.mapper.ProductMapper;
import com.webserviceproject.repository.ProductRepository;
import com.webserviceproject.request.ProductPostRequestBody;
import com.webserviceproject.request.ProductPutRequestBody;
import com.webserviceproject.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	private final CategoryService categoryService;
	
	public Page<Product> findAllPageable(Pageable pageable){
		return productRepository.findAll(pageable);
	}
	
	public List<Product> findAllNonPageable(){
		return productRepository.findAll();
	}
	
	public Product findById(long id) {
		return productRepository.findById(id).orElseThrow(() -> new BadRequestException("product not found"));
	}
	
	public Product save(ProductPostRequestBody productPostRequestBody,long categoryId) {
		Product product = ProductMapper.INSTANCE.toProduct(productPostRequestBody);
		product.getCategory().add(categoryService.findById(categoryId));
		return productRepository.save(product);
	}
	
	public void deleteProduct(long id) {
		try {
			productRepository.delete(findById(id));
		}
		catch(DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public void update (ProductPutRequestBody productPutRequestBody) {
		Product productSave = findById(productPutRequestBody.getId());
		
		ProductMapper.INSTANCE.updateProduct(productPutRequestBody, productSave);
		
		productRepository.save(productSave);
	}
}

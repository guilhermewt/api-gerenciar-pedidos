package com.webserviceproject.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Category;
import com.webserviceproject.mapper.CategoryMapper;
import com.webserviceproject.repository.CategoryRepository;
import com.webserviceproject.request.CategoryPostRequestBody;
import com.webserviceproject.request.CategoryPutRequestBody;
import com.webserviceproject.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	public Page<Category> findAllPageable(Pageable pageable){
		return categoryRepository.findAll(pageable);
	}
	
	public List<Category> findAllNonPageable(){
		return categoryRepository.findAll();
	}
	
	public Category findByIdOrElseThrowBadRequestException(long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new BadRequestException("category not found"));
	}
	
	public Category insert(CategoryPostRequestBody categoryPostRequestBody) {
		return categoryRepository.save(CategoryMapper.INSTANCE.toCategory(categoryPostRequestBody));
	}
	
	public void deleteCategory(long bookId) {
		try {
			categoryRepository.delete(findByIdOrElseThrowBadRequestException(bookId));
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public void updateCategory(CategoryPutRequestBody categoryPutRequestBody) {
		Category category = findByIdOrElseThrowBadRequestException(categoryPutRequestBody.getId());
				          
		CategoryMapper.INSTANCE.updateCategory(categoryPutRequestBody,category);
		categoryRepository.save(category);
	}
}

package com.catalog.Spring.services;

import com.catalog.Spring.dto.CategoryDTO;
import com.catalog.Spring.entities.Category;
import com.catalog.Spring.repositories.CategoryRepository;
import com.catalog.Spring.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list1 = repository.findAll();
        return list1.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
    Optional<Category> obj= repository.findById(id);
    Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    return new CategoryDTO(entity);
    }

}


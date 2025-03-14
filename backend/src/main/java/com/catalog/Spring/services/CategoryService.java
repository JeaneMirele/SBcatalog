package com.catalog.Spring.services;

import com.catalog.Spring.dto.CategoryDTO;
import com.catalog.Spring.entities.Category;
import com.catalog.Spring.repositories.CategoryRepository;
import com.catalog.Spring.services.exceptions.DatabaseException;
import com.catalog.Spring.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
        Page<Category> list1 = repository.findAll(pageRequest);
        return list1.map(x -> new CategoryDTO(x));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
    Optional<Category> obj= repository.findById(id);
    Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    return new CategoryDTO(entity);
    }
    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }
    @Transactional
    public CategoryDTO update(CategoryDTO dto, Long id) {
       try {
           Category entity = repository.getReferenceById(id);
           entity.setName(dto.getName());
           entity = repository.save(entity);
           return new CategoryDTO(entity);
       } catch (EntityNotFoundException e) {
           throw new ResourceNotFoundException("Id not found " + id);
       }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found");
        }
        try{
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}


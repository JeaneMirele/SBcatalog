package com.catalog.Spring.services;

import com.catalog.Spring.entities.Category;
import com.catalog.Spring.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll(){
    return repository.findAll();
    }
}

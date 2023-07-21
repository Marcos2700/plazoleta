package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Category;

import java.util.List;

public interface ICategoryServicePort {

    void saveCategory(Category category);

    List<Category> getAllCategory();

    Category getCategory(Long id);
}

package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Category;

import java.util.List;

public interface ICategoryPersistencePort {

    void saveCategory(Category category);

    List<Category> getAllCategory();
}

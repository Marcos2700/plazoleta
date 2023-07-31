package com.pragma.powerup.plateTests;

import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.jpa.adapter.CategoryJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class CategoryJpaAdapterTests {
    @InjectMocks
    CategoryJpaAdapter categoryJpaAdapter;

    @Mock
    ICategoryRepository categoryRepository;
    @Mock
    ICategoryEntityMapper categoryEntityMapper;

    @Test
    void saveCategory(){
        CategoryEntity categoryEntity = new CategoryEntity();
        Category category = new Category();

        Mockito.when(categoryEntityMapper.toCategoryEntity(category)).thenReturn(categoryEntity);

        try{
            categoryJpaAdapter.saveCategory(category);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getAllCategory(){
        CategoryEntity categoryEntity = new CategoryEntity();
        Category category = new Category();

        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(categoryEntity));
        Mockito.when(categoryEntityMapper.toCategoryList(List.of(categoryEntity)))
                .thenReturn(List.of(category));

        List<Category> result = categoryJpaAdapter.getAllCategory();

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getAllWithEmptyResultList(){
        Mockito.when(categoryRepository.findAll()).thenReturn(List.of());

        try {
            categoryJpaAdapter.getAllCategory();
        }
        catch (Exception e){
            Assertions.assertInstanceOf(NoDataFoundException.class, e);
        }
    }

    @Test
    void getCategory(){
        CategoryEntity categoryEntity = new CategoryEntity();
        Category category = new Category();

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        Mockito.when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        Category result = categoryJpaAdapter.getCategory(1L);

        Assertions.assertInstanceOf(Category.class, result);
    }

    @Test
    void getNoExistingCategory(){
        try {
            Category result = categoryJpaAdapter.getCategory(1L);
        }
        catch (Exception e){
            Assertions.assertInstanceOf(NoDataFoundException.class, e);
        }
    }
}

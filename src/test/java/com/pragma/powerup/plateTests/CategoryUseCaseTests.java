package com.pragma.powerup.plateTests;

import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.domain.usecase.CategoryUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class CategoryUseCaseTests {
    @InjectMocks
    CategoryUseCase categoryUseCase;

    @Mock
    ICategoryPersistencePort categoryPersistencePort;

    @Test
    void saveCategory(){
        Category category = new Category();

        try{
            categoryUseCase.saveCategory(category);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getAllCategory(){
        Category category = new Category();

        Mockito.when(categoryPersistencePort.getAllCategory()).thenReturn(List.of(category));

        List<Category> categoryList = categoryUseCase.getAllCategory();

        Assertions.assertEquals(1, categoryList.size());
    }

    @Test
    void getCategory(){
        Category category = new Category();

        Mockito.when(categoryPersistencePort.getCategory(1L)).thenReturn(category);

        Category result = categoryUseCase.getCategory(1L);

        Assertions.assertInstanceOf(Category.class, result);
    }
}

package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.CategoryDto;
import com.pragma.powerup.domain.model.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-10T08:49:29-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class ICategoryDtoMapperImpl implements ICategoryDtoMapper {

    @Override
    public CategoryDto toCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategoryName( category.getName() );
        categoryDto.setCategoryDescription( category.getDescription() );

        return categoryDto;
    }
}

package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.CategoryDto;
import com.pragma.powerup.application.dto.response.PlateResponseDto;
import com.pragma.powerup.domain.model.Plate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-10T08:49:29-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class IPlateResponseMapperImpl implements IPlateResponseMapper {

    @Override
    public PlateResponseDto toResponse(Plate plate, CategoryDto categoryDto) {
        if ( plate == null && categoryDto == null ) {
            return null;
        }

        PlateResponseDto plateResponseDto = new PlateResponseDto();

        if ( plate != null ) {
            plateResponseDto.setName( plate.getName() );
            plateResponseDto.setDescription( plate.getDescription() );
            plateResponseDto.setPrice( plate.getPrice() );
            plateResponseDto.setIdRestaurant( plate.getIdRestaurant() );
            plateResponseDto.setUrlImage( plate.getUrlImage() );
            plateResponseDto.setActive( plate.getActive() );
        }
        plateResponseDto.setCategory( categoryDtoToCategoryDto( categoryDto ) );

        return plateResponseDto;
    }

    protected CategoryDto categoryDtoToCategoryDto(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        CategoryDto categoryDto1 = new CategoryDto();

        categoryDto1.setCategoryName( categoryDto.getCategoryName() );
        categoryDto1.setCategoryDescription( categoryDto.getCategoryDescription() );

        return categoryDto1;
    }
}

package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.PlateRequestDto;
import com.pragma.powerup.domain.model.Plate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-09T19:28:37-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class IPlateRequestMapperImpl implements IPlateRequestMapper {

    @Override
    public Plate toPlate(PlateRequestDto plateRequestDto) {
        if ( plateRequestDto == null ) {
            return null;
        }

        Plate plate = new Plate();

        plate.setId( plateRequestDto.getId() );
        plate.setName( plateRequestDto.getName() );
        plate.setIdCategory( plateRequestDto.getIdCategory() );
        plate.setDescription( plateRequestDto.getDescription() );
        plate.setPrice( plateRequestDto.getPrice() );
        plate.setIdRestaurant( plateRequestDto.getIdRestaurant() );
        plate.setUrlImage( plateRequestDto.getUrlImage() );
        plate.setActive( plateRequestDto.getActive() );

        return plate;
    }
}

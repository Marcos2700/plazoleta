package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.CategoryDto;
import com.pragma.powerup.application.dto.response.PlateInfoResponseDto;
import com.pragma.powerup.application.dto.response.PlateResponseDto;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Plate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ICategoryDtoMapper.class})
public interface IPlateResponseMapper {

    ICategoryDtoMapper INSTANCE = Mappers.getMapper(ICategoryDtoMapper.class);

    @Mapping(target = "category.categoryName", source = "categoryDto.categoryName")
    @Mapping(target = "category.categoryDescription", source = "categoryDto.categoryDescription")
    PlateResponseDto toResponse(Plate plate, CategoryDto categoryDto);

    default List<PlateResponseDto> toResponseList(List<Plate> plateList, List<Category> categoryList){
        return plateList.stream()
                .map(plate -> {
                    PlateResponseDto plateResponse = new PlateResponseDto();
                    plateResponse.setName(plate.getName());
                    plateResponse.setDescription(plate.getDescription());
                    plateResponse.setPrice(plate.getPrice());
                    plateResponse.setUrlImage(plate.getUrlImage());
                    plateResponse.setActive(plate.getActive());
                    plateResponse.setIdRestaurant(plate.getIdRestaurant());
                    plateResponse.setCategory(INSTANCE.toCategoryDto(categoryList.stream().filter(category -> category.getId().equals(plate.getIdCategory())).findFirst().orElse(null)));
                    return plateResponse;
                }).collect(Collectors.toList());
    }

    default Page<PlateInfoResponseDto> toPlateResponsePage(Page<Plate> platePage){
        List<PlateInfoResponseDto> plateList = platePage.getContent()
                .stream()
                .map(plate -> {
                    PlateInfoResponseDto plateInfoResponseDto = new PlateInfoResponseDto();
                    plateInfoResponseDto.setName(plate.getName());
                    plateInfoResponseDto.setDescription(plate.getDescription());
                    plateInfoResponseDto.setPrice(plate.getPrice());
                    plateInfoResponseDto.setUrlImage(plate.getUrlImage());
                    plateInfoResponseDto.setActivate(plate.getActive());
                    return plateInfoResponseDto;
                }).collect(Collectors.toList());

        return new PageImpl<>(plateList, platePage.getPageable(), platePage.getSize());
    }

}

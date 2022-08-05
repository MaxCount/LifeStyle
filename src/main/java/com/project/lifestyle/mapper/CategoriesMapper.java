package com.project.lifestyle.mapper;

import com.project.lifestyle.dto.CategoriesRequest;
import com.project.lifestyle.model.Categories;
import com.project.lifestyle.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CategoriesMapper {
    @Mapping(target = "cloth", source = "categoriesRequest.cloth")
    @Mapping(target = "leisure", source = "categoriesRequest.leisure")
    @Mapping(target = "technique", source = "categoriesRequest.technique")
    @Mapping(target = "transport", source = "categoriesRequest.transport")
    @Mapping(target = "medicine", source = "categoriesRequest.medicine")
    @Mapping(target = "food", source = "categoriesRequest.food")
    @Mapping(target = "real_estate", source = "categoriesRequest.real_estate")
    @Mapping(target = "education", source = "categoriesRequest.education")
    @Mapping(target = "cryptocurrency", source = "categoriesRequest.cryptocurrency")
    @Mapping(target = "other", source = "categoriesRequest.other")
    @Mapping(target = "date", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    public abstract Categories map(CategoriesRequest categoriesRequest, User user);
}

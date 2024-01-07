package com.product02.model.mapper.mapperCategory;

import com.product02.model.entity.CategoryEntity;
import com.product02.model.mapper.IMapperGeneric;
import com.product02.payload.requet.CategoryRequest;
import com.product02.payload.requet.CategoryUpdateRequest;
import com.product02.payload.response.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements IMapperGeneric<CategoryEntity, CategoryRequest, CategoryResponse> {
    @Override
    public CategoryEntity requestToEntity(CategoryRequest categoryRequest) {
        return CategoryEntity.builder()
                .categoryName(categoryRequest.getCategoryName())
                .description(categoryRequest.getDescription())
                .status(true)
                .build();
    }

    @Override
    public CategoryResponse EntityToResponse(CategoryEntity categoryEntity) {
        return CategoryResponse.builder()
                .categoryId(categoryEntity.getCategoryId())
                .categoryName(categoryEntity.getCategoryName())
                .description(categoryEntity.getDescription())
                .status(categoryEntity.isStatus())
                .build();
    }
}

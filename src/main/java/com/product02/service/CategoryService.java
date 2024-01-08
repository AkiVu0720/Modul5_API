package com.product02.service;

import com.product02.model.entity.CategoryEntity;
import com.product02.payload.response.BasicResponse;
import com.product02.payload.requet.CategoryRequest;
import com.product02.payload.requet.CategoryUpdateRequest;
import com.product02.payload.response.CategoryResponse;
import com.product02.payload.response.ProductPermitResponse;
import org.springframework.data.domain.Page;

import java.util.List;
public interface CategoryService {
    Page<CategoryResponse> findAll(int page, int size, String nameDirection,String dayDirection);
    List<CategoryResponse> findAllByStatusIsTrue();
    CategoryResponse findById(long categoryId);
    CategoryEntity findEntityById(long categoryId);
    CategoryResponse create(CategoryRequest categoryRequest);
    CategoryResponse update(long categoryId, CategoryUpdateRequest categoryUpdateRequest);
    boolean delete(long categoryId);
    List<BasicResponse> revenueCategory();
}

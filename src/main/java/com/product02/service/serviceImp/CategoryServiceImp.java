package com.product02.service.serviceImp;

import com.product02.exception.CustomException;
import com.product02.model.entity.CategoryEntity;
import com.product02.model.entity.ProductEntity;
import com.product02.model.mapper.mapperCategory.CategoryMapper;
import com.product02.model.mapper.mapperProduct.ProductMapper;
import com.product02.payload.requet.CategoryRequest;
import com.product02.payload.requet.CategoryUpdateRequest;
import com.product02.payload.response.BasicResponse;
import com.product02.payload.response.CategoryResponse;
import com.product02.payload.response.ProductPermitResponse;
import com.product02.repository.CategoryRepository;
import com.product02.repository.ProductRepository;
import com.product02.service.CategoryService;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryMapper mapper;
    @Autowired
    @Lazy
    private ProductMapper productMapper;
    @Override
    public Page<CategoryResponse> findAll(int page, int size, String nameDirection) {
        try {
            Pageable pageable = pageableSort(page,size,nameDirection);
            Page<CategoryEntity> list= categoryRepository.findAll(pageable);
            List<CategoryResponse>categoryResponses= list.getContent()
                    .stream()
                    .map(categoryEntity -> mapper.EntityToResponse(categoryEntity)).collect(Collectors.toList());
            Page<CategoryResponse> listCategory = new PageImpl<>(categoryResponses, pageable, list.getTotalElements());

            return  listCategory;
        } catch (Exception e){
            throw new CustomException("Error find All Category: "+e.getMessage());
        }

    }
    public Pageable pageableSort(int page, int size, String nameDirection){
        List<Sort.Order> orderList = new ArrayList<>();
        Sort.Order orderName = sortCategory(nameDirection,"categoryName");
        orderList.add(orderName);
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderList));
        return pageable;
    }
    public Sort.Order sortCategory(String nameDirection, String nameSort){
        Sort.Order oder;
        if (nameDirection.equalsIgnoreCase("asc")){
            oder = new Sort.Order(Sort.Direction.ASC,nameSort);
        }else {
            oder = new Sort.Order(Sort.Direction.DESC,nameSort);
        }
        return oder;
    }

    @Override
    public List<CategoryResponse> findAllByStatusIsTrue() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAllByStatusIsTrue();
        return categoryEntityList.stream().map(categoryEntity ->
                        mapper.EntityToResponse(categoryEntity))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse findById(long categoryId) {
        try {
        return mapper.EntityToResponse(categoryRepository.findById(categoryId).get());
        } catch (Exception e){
            throw new CustomException("Category not Exist: "+ e.getMessage());
        }
    }

    /**
     * Tìm kiếm Category theo category
     * @param categoryId
     * @return
     */
    @Override
    public CategoryEntity findEntityById(long categoryId) {
        try {
            return categoryRepository.findById(categoryId).get();
        } catch (Exception e){
            throw new CustomException("Category not Exist");
        }
    }

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        try {
        return mapper.EntityToResponse(categoryRepository.save(mapper.requestToEntity(categoryRequest)));
        } catch (Exception e){
            throw new CustomException("Error create Category: "+e.getMessage());
        }
    }

    @Override
    public CategoryResponse update(long categoryId, CategoryUpdateRequest categoryUpdateRequest) {
        try {
            Optional<CategoryEntity> category = categoryRepository.findById(categoryId);
            if (category.isPresent()){
                category.get().setCategoryName(categoryUpdateRequest.getCategoryName());
                category.get().setDescription(categoryUpdateRequest.getDescription());
                category.get().setStatus(categoryUpdateRequest.isStatus());
                return mapper.EntityToResponse(categoryRepository.save(category.get()));
            }else {
                throw  new CustomException("Category not Exist");
            }
        } catch (Exception e){
            throw new CustomException("Error update Category: "+e.getMessage());
        }
    }

    @Override
    public boolean delete(long categoryId) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
        if (categoryEntity.isPresent()){
            categoryEntity.get().setStatus(false);
            categoryRepository.save(categoryEntity.get());
            return true;
        }else {
            throw  new CustomException("Category not Exist");
        }
    }



    @Override
    public List<BasicResponse> revenueCategory() {
        return categoryRepository.revenueCategory();
    }
}

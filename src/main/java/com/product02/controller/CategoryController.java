package com.product02.controller;

import com.product02.payload.requet.CategoryRequest;
import com.product02.payload.requet.CategoryUpdateRequest;
import com.product02.payload.response.BaseResponse;
import com.product02.payload.response.CategoryResponse;
import com.product02.payload.response.LoginResponse;
import com.product02.payload.response.ProductPermitResponse;
import com.product02.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${global.url}/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * Danh sách các Danh Mục được bán
     * @return
     */
    @GetMapping("catalogs")
    public ResponseEntity<?>findCategoryStatusIsTrue(){
        BaseResponse baseResponse = new BaseResponse();
        List<CategoryResponse> categoryResponse = categoryService.findAllByStatusIsTrue();
        baseResponse.setStatusCode(200);
        baseResponse.setData(categoryResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("admin/categories/{categoryId}")
    public ResponseEntity<?>findAllCategoryById(
            @PathVariable long categoryId
    ){
        BaseResponse baseResponse = new BaseResponse();
        CategoryResponse categoryResponse = categoryService.findById(categoryId);
        baseResponse.setStatusCode(200);
        baseResponse.setData(categoryResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("admin/categories")
    public ResponseEntity<?>findAllCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam (defaultValue = "ASC")String nameDirection
    ){
        BaseResponse baseResponse = new BaseResponse();
        Page<CategoryResponse> categoryResponse = categoryService.findAll(page,size,nameDirection);
        baseResponse.setStatusCode(200);
        baseResponse.setData(categoryResponse.getContent());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PostMapping("admin/categories")
    public ResponseEntity<?>createCategory(@RequestBody CategoryRequest categoryRequest){
        BaseResponse baseResponse = new BaseResponse();
        CategoryResponse categoryResponse = categoryService.create(categoryRequest);
        baseResponse.setStatusCode(200);
        baseResponse.setData(categoryResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PutMapping("admin/categories/{categoryId}")
    public ResponseEntity<?>updateCategory(@PathVariable long categoryId,@RequestBody CategoryUpdateRequest categoryRequest){
        BaseResponse baseResponse = new BaseResponse();
        CategoryResponse categoryResponse = categoryService.update(categoryId,categoryRequest);
        baseResponse.setStatusCode(200);
        baseResponse.setData(categoryResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @DeleteMapping("admin/categories/{categoryId}")
    public ResponseEntity<?>DeleteCategory(@PathVariable long categoryId){
        BaseResponse baseResponse = new BaseResponse();
        boolean isSuccess = categoryService.delete(categoryId);
        baseResponse.setStatusCode(200);
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }



}


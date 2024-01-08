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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>findAllCategoryById(
            @PathVariable long categoryId
    ){
        BaseResponse baseResponse = new BaseResponse();
        CategoryResponse categoryResponse = categoryService.findById(categoryId);
        baseResponse.setStatusCode(200);
        baseResponse.setData(categoryResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Danh sách Danh mục
     * @param page
     * @param size
     * @param nameDirection
     * @param idDirection
     * @return
     */
    @GetMapping("admin/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>findAllCategory(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam (defaultValue = "ASC")String nameDirection,
            @RequestParam (defaultValue = "ASC")String idDirection
    ){
        BaseResponse baseResponse = new BaseResponse();
        Page<CategoryResponse> categoryResponse = categoryService.findAll(page-1,size,nameDirection,idDirection);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Danh sách Danh mục");
        baseResponse.setData(categoryResponse.getContent());
        Map<String, Object> dataResponse = new HashMap<>();
        dataResponse.put("dataUsers", baseResponse);
        dataResponse.put("totalPage", categoryResponse.getTotalPages());
        dataResponse.put("totalUser", categoryResponse.getTotalElements());
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    /**
     * Tạo mới Danh mục
     * @param categoryRequest
     * @return
     */
    @PostMapping("admin/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>createCategory(@RequestBody CategoryRequest categoryRequest){
        BaseResponse baseResponse = new BaseResponse();
        CategoryResponse categoryResponse = categoryService.create(categoryRequest);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Tạo mới Danh mục");
        baseResponse.setData(categoryResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Cập nhật danh mục
     * @param categoryId
     * @param categoryRequest
     * @return
     */
    @PutMapping("admin/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>updateCategory(@PathVariable long categoryId,@RequestBody CategoryUpdateRequest categoryRequest){
        BaseResponse baseResponse = new BaseResponse();
        CategoryResponse categoryResponse = categoryService.update(categoryId,categoryRequest);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Cập nhật danh mục");
        baseResponse.setData(categoryResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Xoá danh mục
     * @param categoryId
     * @return
     */
    @DeleteMapping("admin/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>DeleteCategory(@PathVariable long categoryId){
        BaseResponse baseResponse = new BaseResponse();
        boolean isSuccess = categoryService.delete(categoryId);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Xoá danh mục(Đổi trạng thái)");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }



}


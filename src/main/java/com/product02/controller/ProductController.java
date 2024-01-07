package com.product02.controller;

import com.product02.model.entity.ProductEntity;
import com.product02.model.entity.WishListEntity;
import com.product02.payload.requet.CategoryRequest;
import com.product02.payload.requet.CategoryUpdateRequest;
import com.product02.payload.requet.ProductRequest;
import com.product02.payload.requet.ProductUpdateRequest;
import com.product02.payload.response.*;
import com.product02.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${global.url}/v1")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("admin/products")
    public ResponseEntity<?> createCategory(@RequestBody ProductRequest productRequest){
        BaseResponse baseResponse = new BaseResponse();
        ProductResponse productResponse = productService.create(productRequest);
        baseResponse.setStatusCode(200);
        baseResponse.setData(productResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("admin/products/{productId}")
    public ResponseEntity<?> findProductById(@PathVariable long productId){
        BaseResponse baseResponse = new BaseResponse();
        ProductResponse productResponse = productService.findById(productId);
        baseResponse.setStatusCode(200);
        baseResponse.setData(productResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Tìm  kiếm sản phâmt theo mã id
     * @param productId
     * @return
     */
    @GetMapping("products/{productId}")
    public ResponseEntity<?> findProductPermitById(@PathVariable long productId){
        BaseResponse baseResponse = new BaseResponse();
        ProductPermitResponse productResponse = productService.findProductPermitById(productId);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Chi tiết sản phẩm theo Id");
        baseResponse.setData(productResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("admin/products")
    public ResponseEntity<?>findAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam (defaultValue = "ASC")String nameDirection,
            @RequestParam (defaultValue = "ASC")String idDirection
    ){
        BaseResponse baseResponse = new BaseResponse();
        Page<ProductResponse> productResponses = productService.findPageAll(page,size,nameDirection,idDirection);
        baseResponse.setStatusCode(200);
        baseResponse.setData(productResponses.getContent());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PutMapping("admin/products/{productId}")
    public ResponseEntity<?>updateProduct(@PathVariable long productId,@RequestBody ProductUpdateRequest productUpdateRequest){
        BaseResponse baseResponse = new BaseResponse();
        ProductResponse productResponse = productService.update(productId,productUpdateRequest);
        baseResponse.setStatusCode(200);
        baseResponse.setData(productResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @DeleteMapping("admin/products/{productId}")
    public ResponseEntity<?>deleteProduct(@PathVariable long productId){
        BaseResponse baseResponse = new BaseResponse();
        boolean isSuccess = productService.delete(productId);
        baseResponse.setStatusCode(200);
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Sản phẩm được bán
     * @param page
     * @param size
     * @param nameDirection
     * @param idDirection
     * @return
     */
    @GetMapping("products")
    public ResponseEntity<?>findAllProductStatusTrue(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam (defaultValue = "ASC")String nameDirection,
            @RequestParam (defaultValue = "ASC")String idDirection
    ){
        BaseResponse baseResponse = new BaseResponse();
        Page<ProductStatusTrueResponse> productResponses = productService.ProductStatusTrue(page-1,size,nameDirection,idDirection);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("totalPage: "+productResponses.getTotalPages()+" totalProduct: "+productResponses.getTotalElements());
        baseResponse.setData(productResponses.getContent());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Tìm kiếm sản phẩm theo tên hoặc mô tả
     * @param productName
     * @param description
     * @return
     */
    @GetMapping("products/search")
    public ResponseEntity<?>findByNameAndDescription(
            @RequestParam(defaultValue = "") String productName,
            @RequestParam(defaultValue = "") String description
    ){
        BaseResponse baseResponse = new BaseResponse();
        List<ProductPermitResponse>productResponse = productService.findByNameAndDescription(productName,description);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Tìm kiếm sản phẩm theo tên ỏ mô tả");
        baseResponse.setData(productResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Danh sách 10 sản phẩm nổi bật, được yêu thích nhiều nhất.
     * @return
     */
    @GetMapping("/products/featured-products")
    public ResponseEntity<?> featuredProducts(){
        List<ProductResultResponse> productResponses = productService.listFeaturedProduct();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Top 10 Sp nổi bật");
        baseResponse.setData(productResponses);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Danh sách sản phẩm mới nhất
     * @return
     */
    @GetMapping("/products/new-products")
    public ResponseEntity<?> newProducts(){
        List<ProductPermitResponse> productResponses = productService.listNewProduct();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Danh sách 10 sản phẩm  mới nhất.");
        baseResponse.setStatusCode(200);
        baseResponse.setData(productResponses);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Sản phẩm bán chạy
     * @return
     */
    @GetMapping("products/best-seller-products")
    public ResponseEntity<?> bestSellerProducts(
    ){
        BaseResponse baseResponse = new BaseResponse();
        List<ProductResultResponse> orderResponses = productService.findByStatusSuccess();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Top 10 sản phẩm bán chạy nhất");
        baseResponse.setData(orderResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Danh sách sản phẩm theo danh mục
     * @param catalogId
     * @return
     */
    @GetMapping("products/catalogs/{catalogId}")
    public ResponseEntity<?> findListProductByCategoryId(
            @PathVariable long catalogId
    ){
        BaseResponse baseResponse = new BaseResponse();
        List<ProductPermitResponse> responseList = productService.listProductByCategoryId(catalogId);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Danh Sách Sản phẩm theo Danh mục");
        baseResponse.setData(responseList);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}

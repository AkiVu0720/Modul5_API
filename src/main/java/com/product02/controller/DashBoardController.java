package com.product02.controller;

import com.product02.payload.response.BaseResponse;
import com.product02.payload.response.BasicResponse;
import com.product02.payload.response.BestProductsInMonthRes;
import com.product02.payload.response.RevenueByTimeResponse;
import com.product02.service.CategoryService;
import com.product02.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("${global.url}/v1")

public class DashBoardController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    /**
     * Doanh thu theo doanh mục
     * @return
     */
    @GetMapping("admin/dash-board/sales/catagories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> revenueCategory(){
        BaseResponse baseResponse = new BaseResponse();
        List<BasicResponse> categoryResponse = categoryService.revenueCategory();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Doanh thu theo doanh mục");
        baseResponse.setData(categoryResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Danh sách những sản phẩm bán chạy trong tháng
     * @return
     */
    @GetMapping("admin/dash-board/sales/best-seller-products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bestProductsInMonthRes(){
        BaseResponse baseResponse = new BaseResponse();
        List<BestProductsInMonthRes> categoryResponse = productService.bestProductsInMonthRes();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Danh sách những sản phẩm bán chạy trong tháng ");
        baseResponse.setData(categoryResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Thống kê doanh thu theo thoi gian
     * @param from
     * @param to
     * @return
     */
    @GetMapping("/admin/dash-board/sales")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> revenueByTime(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date from,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date to
            ){
        BaseResponse baseResponse = new BaseResponse();
        RevenueByTimeResponse response = productService.revenueByTime(from,to);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Sales from:" + from.toLocaleString()+" to: "+to.toLocaleString());
        baseResponse.setData(response);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}

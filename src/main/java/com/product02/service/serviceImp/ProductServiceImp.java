package com.product02.service.serviceImp;

import com.product02.exception.CustomException;
import com.product02.model.entity.*;
import com.product02.model.mapper.mapperCategory.CategoryMapper;
import com.product02.model.mapper.mapperProduct.ProductMapper;
import com.product02.payload.requet.ProductRequest;
import com.product02.payload.requet.ProductUpdateRequest;
import com.product02.payload.response.*;
import com.product02.repository.ProductRepository;
import com.product02.service.CategoryService;
import com.product02.service.ProductService;
import com.product02.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper mapper;
    @Autowired
    @Lazy
    private CategoryService categoryService;
    @Autowired
    @Lazy
    private WishService wishService;

    @Override
    public Page<ProductResponse> findPageAll(int page, int size, String nameDirection, String idDirection) {
       try {
           Pageable pageable = pageableSort(page,size,nameDirection,idDirection);
           Page<ProductEntity> list = productRepository.findAll(pageable);
           List<ProductResponse>productResponses = list.getContent()
                   .stream()
                   .map(productEntity ->
                           mapper.EntityToResponse(productEntity))
                   .collect(Collectors.toList());
           Page<ProductResponse> listProduct = new PageImpl<>(productResponses,pageable, list.getTotalElements());
           return listProduct;
       } catch (Exception e){
           throw new CustomException("Error find All Product");
       }
    }

    /**
     * Danh sach san pham duoc ban: Status = true
     * @param page
     * @param size
     * @param nameDirection
     * @param idDirection
     * @return
     */
    @Override
    public Page<ProductStatusTrueResponse> ProductStatusTrue(int page, int size, String nameDirection,String idDirection) {
        try {
            Pageable pageable = pageableSort(page,size,nameDirection,idDirection);
            Page<ProductEntity> list = productRepository.findByStatusIsTrue(pageable);
            List<ProductStatusTrueResponse>productResponses = list.getContent()
                    .stream()
                    .map(productEntity ->
                            mapper.EntityToResponseStatusTrue(productEntity))
                    .collect(Collectors.toList());
            Page<ProductStatusTrueResponse> listProduct = new PageImpl<>(productResponses,pageable, list.getTotalElements());
            return listProduct;
        } catch (Exception e){
            throw new CustomException("Error find All Product");
        }
    }


    @Override
    public ProductEntity findEntityById(long productId) {
        try {
        return productRepository.findById(productId).get();
        } catch (Exception e){
            throw  new CustomException("Product not Exist");
        }
    }

    @Override
    public boolean isExistProductName(String productName) {
        return productRepository.existsByProductNameContainingIgnoreCase(productName);
    }

    @Override
    public boolean isExistSku(String sku) {
        return productRepository.existsBySku(sku);
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    /**
     * Tìm kiếm theo tên hoặc mô tả
     * @param nameSort
     * @param descriptionSort
     * @return
     */
    @Override
    public List<ProductPermitResponse> findByNameAndDescription(String nameSort, String descriptionSort) {
        try {
            List<ProductEntity> list = new ArrayList<>();
            list = productRepository.findByProductNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(nameSort,descriptionSort);
            if (descriptionSort.isEmpty()){
                list=productRepository.findByProductNameContainingIgnoreCase(nameSort);
            }
            if (nameSort.isEmpty()){
                list= productRepository.findByDescriptionContainingIgnoreCase(descriptionSort);
            }

        return list.stream().map(productEntity -> mapper.EntityToResponsePermit(productEntity)).collect(Collectors.toList());
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        UUID uuid = UUID.randomUUID();
        if (isExistSku(uuid.toString())){
            throw new CustomException("sku exist!");
        }
        if (isExistProductName(productRequest.getProductName())){
            throw new CustomException("ProductName Exist");
        }
       try {
           ProductEntity product = mapper.requestToEntity(productRequest);
           product.setSku(uuid.toString());
        return mapper.EntityToResponse(save(product));
       } catch (Exception e){
           throw  new CustomException("Error create Product");
       }
    }

    @Override
    public boolean delete(long productId) {
        try {
            ProductEntity product = findEntityById(productId);
            product.setStatus(false);
            save(product);
//            productRepository.delete(product);
            return true;
        } catch (Exception e){
           throw new CustomException(e.getMessage()) ;
        }
    }

    @Override
    public ProductResponse update(long productId, ProductUpdateRequest productRequest) {
            CategoryEntity category = categoryService.findEntityById(productRequest.getCategoryId());
            ProductEntity product = findEntityById(productId);
        try {
            if (!isExistProductName(productRequest.getProductName())){
                product.setProductName(productRequest.getProductName());
            }else {
                throw new CustomException("Product name Exist");
            }

            product.setDescription(productRequest.getDescription());
            product.setImage(productRequest.getImage());
            product.setUpdated(new Date());
            product.setStatus(productRequest.isStatus());
            product.setCategory(category);
            return mapper.EntityToResponse(save(product));
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public ProductResponse findById(long productId) {
        try {
            ProductEntity product = findEntityById(productId);
            return mapper.EntityToResponse(product);
        } catch (Exception e){
            throw new CustomException("Error find Product: "+e.getMessage());
        }
    }

    @Override
    public ProductPermitResponse findProductPermitById(long productId) {
        try {
            ProductEntity product = findEntityById(productId);
            return mapper.EntityToResponsePermit(product);
        } catch (Exception e){
            throw new CustomException("Error find Product: "+e.getMessage());
        }
    }

    /**
     * Danh sách 10 sản phẩm nổi bật
     * @return
     */
    @Override
    public List<ProductResultResponse> listFeaturedProduct() {
        try {
            List<WishListFeatured> listAll = wishService.listFeaturedProduct();
            List<ProductResultResponse> resultResponses = listAll.stream().map(wishListFeatured ->
                            mapper.EntityToProductResultResponse(findEntityById(wishListFeatured.getProductId()), wishListFeatured.getCount()))
                                    .collect(Collectors.toList());
            return resultResponses;
        } catch (Exception e){
            throw  new CustomException(e.getMessage());
        }

    }

    /**
     * Danh sách sản phẩm mới
     * @return
     */
    @Override
    public List<ProductPermitResponse> listNewProduct() {
        try {
            List<ProductEntity> list = productRepository.findTop10ByStatusIsTrueOrderByCreatedDesc();
            return list.stream().map(product ->mapper.EntityToResponsePermit(product)).collect(Collectors.toList());
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }

    /**
     * Danh sach san pham ban chay
     * @return
     */
    @Override
    public List<ProductResultResponse> findByStatusSuccess() {
        try {
            List<BestSellerResponse> list = productRepository.findByStatusSuccess(EStatus.SUCCESS);
            // từ List lấy ra được mã productId và số lượng đã bán.
            //Sau khi lấy được mã productId. từ list productId đó gọi sang tìm Product bằng ID->> chuyển qua List Product
            List<ProductResultResponse> resultResponses = list.stream().map(bestSellerResponse ->
                    mapper.EntityToProductResultResponse(findEntityById(bestSellerResponse
                            .getProductId()),bestSellerResponse.getTotal()))
                    .collect(Collectors.toList());
            return resultResponses;
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public List<BestProductsInMonthRes> bestProductsInMonthRes() {
        return productRepository.bestProductsInMonthRes();
    }

    @Override
    public RevenueByTimeResponse revenueByTime(Date from, Date to) {
        try {
            RevenueByTimeResponse response = new RevenueByTimeResponse();
            double revenue = productRepository.revenueByTime(from,to);
            response.setTotal(revenue);
            return response;
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public List<ProductPermitResponse> listProductByCategoryId(long categoryId) {
        List<ProductEntity> entityList = productRepository.findByCategory_CategoryIdAndStatusIsTrue(categoryId);
        return entityList.stream().map(productEntity ->
                mapper.EntityToResponsePermit(productEntity)).collect(Collectors.toList());
    }


    @Override
    public List<ProductResponse> bestSellerProduct() {

        return null;
    }

    public Pageable pageableSort (int page, int size, String nameDirection, String idDirection){
        List<Sort.Order> oderList = new ArrayList<>();
        Sort.Order orderId = sortProduct(nameDirection,"productId");
        oderList.add(orderId);
        Sort.Order orderName = sortProduct(nameDirection, "productName");
        oderList.add(orderName);
        Pageable pageable = PageRequest.of(page,size,Sort.by(oderList));
        return  pageable;
    }
    public Sort.Order sortProduct(String nameDirection, String nameSort){
        Sort.Order order;
        if (nameDirection.equalsIgnoreCase("asc")){
            order = new Sort.Order(Sort.Direction.ASC,nameSort);
        } else {
            order = new Sort.Order(Sort.Direction.DESC,nameSort);
        }
        return  order;
    }
    public static void addElement(Map<Long, Long> map, long element) {
        if (map.containsKey(element)) {
            long count = map.get(element) + 1;
            map.put(element, count);
        } else {
            map.put(element, 1L);
        }
    }
}

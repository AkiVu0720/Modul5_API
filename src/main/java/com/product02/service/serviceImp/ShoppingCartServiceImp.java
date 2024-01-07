package com.product02.service.serviceImp;

import com.product02.exception.CustomException;
import com.product02.exception.UserNotFoundException;
import com.product02.model.entity.OrderDetailEntity;
import com.product02.model.entity.ProductEntity;
import com.product02.model.entity.ShoppingCardEntity;
import com.product02.model.entity.UserEntity;
import com.product02.model.mapper.mapperShopping.ShoppingMapper;
import com.product02.payload.requet.ShoppingCartRequest;
import com.product02.payload.requet.ShoppingCartUpdateQuantityResponse;
import com.product02.payload.response.ShoppingCartCreateResponse;
import com.product02.payload.response.ShoppingCartResponse;
import com.product02.repository.ShoppingCartRepository;
import com.product02.service.ProductService;
import com.product02.service.ShoppingCartService;
import com.product02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImp implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository cartRepository;
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    private ShoppingMapper cartMapper;

    @Autowired
    @Lazy
    private ProductService productService;
    @Override
    public List<ShoppingCartResponse> listProductInCart(long userId) {
        userService.findUserById(userId);
        List<ShoppingCardEntity> cardEntityList = cartRepository.findByUser_Id(userId);
        return cardEntityList.stream().map(shoppingCardEntity ->
                cartMapper.EntityToResponse(shoppingCardEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShoppingCardEntity> listShoppingCartByUserId(long userId) {
        return cartRepository.findByUser_Id(userId);
    }

    @Override
    public ShoppingCardEntity findByCartIdAndUser_Id(long userId, int cartId) {
        return cartRepository.findByCartIdAndUser_Id(cartId,userId);
    }

    /**
     * Lấy ra list Shopping Cart từ list CartId truyền từ Request
     * @param listCartId
     * @return
     */
    @Override
    public List<ShoppingCardEntity> listShoppingCartByCartId(List<Integer> listCartId) {
        if (listCartId!=null){
            return listCartId.stream().map(cartId -> findById(cartId)).collect(Collectors.toList());
        }else {
            throw new CustomException("No Products");
        }
    }

    @Override
    public boolean deleteShoppingCartByListCartId(long userId,List<Integer> listCartId) {
        try {
            listCartId.forEach(cartId ->deleteByCartId(userId,cartId));
            return true;
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public ShoppingCardEntity findById(int cartId) {
        try {
        return cartRepository.findById(cartId).get();
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Xoá Shopping Cart theo User
     * @param userId
     * @return
     */
    @Override
    public boolean deleteAllProductInCart(long userId) {
        try {
            cartRepository.deleteByUser_Id(userId);
            return true;
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }

    @Override
    public boolean deleteByCartId(long userId,int cartId) {
        if (userService.findUserById(userId)==null){
            throw new UserNotFoundException("User not Exist");
        }
        if (cartRepository.findByCartIdAndUser_Id(cartId,userId)==null){
            throw new CustomException("CartEntity in UserEntity not Exist");
        }
        try {
            cartRepository.deleteByUser_IdAndCartId(userId,cartId);
            return true;
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    /**
     *  Tạo mới giỏ hàng theo UserId
     * @param userId
     * @return
     */
    @Override
    public ShoppingCartCreateResponse createShoppingCard(long userId){
        ShoppingCardEntity shoppingCard = new ShoppingCardEntity();
        UserEntity user = userService.findUserById(userId);
        shoppingCard.setUser(user);
        return cartMapper.EntityToCreateShoppingCart(shoppingCard);
    }

    /**
     * Thêm mới sản phẩm vào giỏ hàng
     * @param userId
     * @param cartRequest
     * @return
     */
    @Override
    public ShoppingCartResponse addShoppingCart(long userId, ShoppingCartRequest cartRequest){
        ShoppingCardEntity findCart = findByUser_IdAndProduct_ProductId(userId, cartRequest.getProductId());
        if (findCart!=null){
            findCart.setQuantity(findCart.getQuantity()+ cartRequest.getQuantity());
            cartRepository.save(findCart);
            return cartMapper.EntityToResponse(findCart);
        }
        ShoppingCardEntity shoppingCard = shoppingCardEntity(userId);
        ProductEntity product = productService.findEntityById(cartRequest.getProductId());
        shoppingCard.setQuantity(cartRequest.getQuantity());
        shoppingCard.setProduct(product);
        return cartMapper.EntityToResponse(cartRepository.save(shoppingCard));
    }

    @Override
    public ShoppingCardEntity findByUser_IdAndProduct_ProductId(long userId, long productId) {

        if (userService.findUserById(userId)==null){
            throw new CustomException("User not Exist");
        }
        if (productService.findById(productId)==null){
            throw new CustomException("Product not Exist");
        }
        return cartRepository.findByUser_IdAndProduct_ProductId(userId,productId);
    }

    /**
     * Thay đổi,Cập nhật số lượng sản phẩm
     * @param userId
     * @param productId
     * @param cartRequest
     * @return
     */
    @Override
    public ShoppingCartResponse update(long userId, int productId, ShoppingCartUpdateQuantityResponse cartRequest) {
        try {
            ShoppingCardEntity shoppingCard = findByUser_IdAndProduct_ProductId(userId,productId);
            shoppingCard.setQuantity(cartRequest.getQuantity());
            cartRepository.save(shoppingCard);
            return cartMapper.EntityToResponse(shoppingCard);
        } catch (Exception e){
            throw  new CustomException(e.getMessage());
        }
    }


    public ShoppingCardEntity shoppingCardEntity(long userId){
        ShoppingCardEntity shoppingCard = new ShoppingCardEntity();
        UserEntity user = userService.findUserById(userId);
        shoppingCard.setUser(user);
        return shoppingCard;
    }

}

package com.product02.repository;

import com.product02.model.entity.ShoppingCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCardEntity,Integer> {
    List<ShoppingCardEntity>findByUser_Id(long userId);
    @Transactional
    void deleteByUser_Id(long userId);
    ShoppingCardEntity findByCartIdAndUser_Id(int cartId, long userId);
    @Transactional
    void deleteByUser_IdAndCartId(long userId, int cartId);
    ShoppingCardEntity findByUser_IdAndProduct_ProductId(long userId, long productId);
}

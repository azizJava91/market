package com.market.repository;

import com.market.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {


    List<ProductCategory> findAllByActive(Integer active);

    List<ProductCategory> findAllByNameAndActive(String name, Integer active);

    ProductCategory findProductCategoryByCategoryIdAndActive(Long categoryId, Integer active);
}

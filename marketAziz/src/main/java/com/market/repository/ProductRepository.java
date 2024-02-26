package com.market.repository;

import com.market.entity.Product;
import com.market.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByActive(Integer active);
    List<Product> findAllByProductIdAndActive(Long productId, Integer active);
    Product findProductByProductIdAndActive(Long productId, Integer active);

    List<Product> findAllByProductCategoryAndActive(ProductCategory productCategory, Integer active);

    List<Product> findAllByNameAndActive(String name, Integer active);
    List<Product> findAllByBrandAndActive(String brand, Integer active);

//    List<Product> findAllByNameAndActiveAndSalePriceGreaterThanAndSalePriceLessThan(String name, Integer active,Double max, Double min);

}

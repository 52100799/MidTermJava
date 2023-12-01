package com.example.gk.Repository;

import com.example.gk.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByPriceLessThan(BigDecimal price);
    List<Product> findByBrand(String brand);
    List<Product> findByColor(String color);

    // Phương thức để đếm số lượng sản phẩm trong mỗi danh mục
    @Query("SELECT p.category, COUNT(p) FROM Product p GROUP BY p.category")
    List<Object[]> countProductsByCategory();

    List<Product> findByPriceBetween(double minPrice, double maxPrice);
}


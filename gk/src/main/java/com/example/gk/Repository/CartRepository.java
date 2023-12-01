package com.example.gk.Repository;

import com.example.gk.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Các phương thức truy vấn hoặc thao tác dữ liệu sẽ được tự động tạo ra bởi Spring Data JPA
    Cart findByProductId(Long productId);
    long countBy();
}

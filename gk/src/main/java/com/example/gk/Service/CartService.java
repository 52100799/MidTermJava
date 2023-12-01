package com.example.gk.Service;

import com.example.gk.Repository.CartRepository;
import com.example.gk.DTO.CartDTO;
import com.example.gk.Model.Cart;
import com.example.gk.Model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;
    private ProductService productService;

    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart createCart(CartDTO cartRequest) {
        Long productId = cartRequest.getProduct_id();
        int quantity = cartRequest.getQuantity();

        Product existingProduct = productService.getProductById(productId);
        Cart existingCartWithProductId = cartRepository.findByProductId(productId);
        if (existingProduct != null && existingCartWithProductId == null) {
            Cart cart = new Cart();
            cart.setProduct(existingProduct);
            cart.setQuantity(quantity);
            return cartRepository.save(cart);
        }
        return null;
    }

    public Cart updateCart(Long id, CartDTO updatedCart) {
        Long productId = updatedCart.getProduct_id();
        Product product = productService.getProductById(productId);
        Cart existingCart = getCartById(id);
        if (existingCart != null && product != null) {
            existingCart.setProduct(product);
            existingCart.setQuantity(updatedCart.getQuantity());
            return cartRepository.save(existingCart);
        }
        return null;
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public void addProductToCart(Long productId) {
        Product product = productService.getProductById(productId);
        Cart existingCartWithProductId = cartRepository.findByProductId(productId);
        if (product != null) {
            if (existingCartWithProductId == null) {
                Cart cart = new Cart(product,1);
                cartRepository.save(cart);
            }
            else {
                int quantity = existingCartWithProductId.getQuantity() + 1;
                existingCartWithProductId.setProduct(product);
                existingCartWithProductId.setQuantity(quantity);
                cartRepository.save(existingCartWithProductId);
            }
        }
    }

    public void subProductFromCart(Long productId) {
        Product product = productService.getProductById(productId);
        Cart existingCartWithProductId = cartRepository.findByProductId(productId);
        if (product != null && existingCartWithProductId != null) {
            int quantity = existingCartWithProductId.getQuantity();
            if (quantity > 1) {
                quantity -= 1;
                existingCartWithProductId.setProduct(product);
                existingCartWithProductId.setQuantity(quantity);
                cartRepository.save(existingCartWithProductId);
            }
            else {
                deleteCart(existingCartWithProductId.getId());
            }
        }
    }

    public long countCarts() {
        return cartRepository.countBy();
    }
}

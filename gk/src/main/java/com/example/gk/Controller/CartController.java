package com.example.gk.Controller;

import com.example.gk.Model.Cart;
import com.example.gk.DTO.CartDTO;
import com.example.gk.Service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CartDTO cart) {
        Cart createdCart = cartService.createCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable Long id, @RequestBody CartDTO cart) {
        return cartService.updateCart(id, cart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        try {
            cartService.deleteCart(id);
            String successMessage = "{\"message\": \"Delete successfully\"}";
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "{\"error\": \"Error: " + e.getMessage() + "\"}";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long productId) {
        cartService.addProductToCart(productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sub-product/{productId}")
    public ResponseEntity<Void> subProductFromCart(@PathVariable Long productId) {
        cartService.subProductFromCart(productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

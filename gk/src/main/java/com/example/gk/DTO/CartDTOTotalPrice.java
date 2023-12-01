package com.example.gk.DTO;

import com.example.gk.Model.Cart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTOTotalPrice {
    private Cart cart;
    private double totalPrice;
}

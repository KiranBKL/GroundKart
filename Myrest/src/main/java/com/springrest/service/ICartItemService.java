package com.springrest.service;

import com.springrest.exception.CartItemException;
import com.springrest.model.*;

public interface ICartItemService {
    void addCartItem(CartItem cartItem);

    void removeCartItemById(int CartItemId) throws CartItemException;

    
}


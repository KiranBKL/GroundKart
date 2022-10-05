package com.springrest.service;

import com.springrest.exception.CartException;
import com.springrest.model.*;

public interface CartService {
    Cart getCartById(int CartId) throws CartException;
}


package com.springrest.service;

import com.springrest.exception.CartException;
import com.springrest.model.*;

public interface ICartService {
    Cart getCartById(int CartId) throws CartException;
}


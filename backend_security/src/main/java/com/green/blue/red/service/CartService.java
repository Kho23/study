package com.green.blue.red.service;

import com.green.blue.red.dto.CartItemDto;
import com.green.blue.red.dto.CartItemListDto;

import java.util.List;

public interface CartService {
    public List<CartItemListDto> addOrModify(CartItemDto cartItemDto);
    public List<CartItemListDto> getCartItems(String email);
    public List<CartItemListDto> remove(Long cino);
}

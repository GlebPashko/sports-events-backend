package com.sportsevents.backend.sportseventsbackend.cart.service;

import com.sportsevents.backend.sportseventsbackend.cart.dto.cartitem.CartItemRequestDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.shoppingcart.ShoppingCartResponseDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.shoppingcart.UpdateShoppingCartRequestDto;
import com.sportsevents.backend.sportseventsbackend.user.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCart();

    ShoppingCartResponseDto addBookToShoppingCart(CartItemRequestDto requestDto);

    ShoppingCartResponseDto updateQuantity(Long id, UpdateShoppingCartRequestDto requestDto);

    void deleteBook(Long id);

    void addShoppingCartToUser(User user);
}

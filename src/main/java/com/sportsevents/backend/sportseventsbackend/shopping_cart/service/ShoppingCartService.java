package com.sportsevents.backend.sportseventsbackend.shopping_cart.service;


import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.cartItem.CartItemRequestDto;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.shoppingCart.ShoppingCartResponseDto;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.shoppingCart.UpdateShoppingCartRequestDto;
import com.sportsevents.backend.sportseventsbackend.user.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCart();

    ShoppingCartResponseDto addBookToShoppingCart(CartItemRequestDto requestDto);

    ShoppingCartResponseDto updateQuantity(Long id, UpdateShoppingCartRequestDto requestDto);

    void deleteBook(Long id);

    void addShoppingCartToUser(User user);
}

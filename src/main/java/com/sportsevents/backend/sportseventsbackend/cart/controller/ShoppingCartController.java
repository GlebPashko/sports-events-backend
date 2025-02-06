package com.sportsevents.backend.sportseventsbackend.cart.controller;

import com.sportsevents.backend.sportseventsbackend.cart.dto.cartitem.CartItemRequestDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.shoppingcart.ShoppingCartResponseDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.shoppingcart.UpdateShoppingCartRequestDto;
import com.sportsevents.backend.sportseventsbackend.cart.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping cart")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Get user's cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ShoppingCartResponseDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @Operation(summary = "Add the event to the cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ShoppingCartResponseDto addEventToShoppingCart(
            @RequestBody @Valid CartItemRequestDto requestDto) {
        return shoppingCartService.addBookToShoppingCart(requestDto);
    }

    @Operation(summary = "Update the event's quantity")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ShoppingCartResponseDto updateQuantity(
            @PathVariable Long id,
            @RequestBody @Valid UpdateShoppingCartRequestDto requestDto) {
        return shoppingCartService.updateQuantity(id, requestDto);
    }

    @Operation(summary = "Delete the event from the cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public void deleteEventFromCart(@PathVariable Long id) {
        shoppingCartService.deleteBook(id);
    }
}

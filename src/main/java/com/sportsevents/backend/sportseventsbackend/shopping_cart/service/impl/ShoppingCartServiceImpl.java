package com.sportsevents.backend.sportseventsbackend.shopping_cart.service.impl;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.event.EventRepository;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.cartItem.CartItemRequestDto;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.shoppingCart.ShoppingCartResponseDto;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.shoppingCart.UpdateShoppingCartRequestDto;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.mapper.CartItemMapper;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.mapper.ShoppingCartMapper;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.model.CartItem;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.model.ShoppingCart;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.repository.cartitem.CartItemRepository;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.repository.shoppingcart.ShoppingCartRepository;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.service.ShoppingCartService;
import com.sportsevents.backend.sportseventsbackend.user.model.User;
import com.sportsevents.backend.sportseventsbackend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto getShoppingCart() {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(getAuthenticatedUser());

        return shoppingCartMapper.toShoppingCartResponseDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto addBookToShoppingCart(CartItemRequestDto requestDto) {
        Event event = eventRepository.findById(requestDto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Book with id "
                        + requestDto.getEventId() + " not found"));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(getAuthenticatedUser());

        CartItem cartItem = cartItemRepository.findByShoppingCartId(shoppingCart.getId())
                .stream()
                .filter(item -> item.getEvent().getId().equals(requestDto.getEventId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newCartItem = cartItemMapper.toModel(requestDto);
                    newCartItem.setShoppingCart(shoppingCart);
                    newCartItem.setEvent(event);
                    return newCartItem;
                });
        if (cartItem.getId() != null) {
            cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
        }

        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);

        return shoppingCartMapper.toShoppingCartResponseDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateQuantity(
            Long id, UpdateShoppingCartRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(getAuthenticatedUser());

        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(id, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toShoppingCartResponseDto(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(getAuthenticatedUser());
        cartItemRepository.deleteByIdAndShoppingCartId(id, shoppingCart.getId());
    }

    @Override
    public void addShoppingCartToUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    private Long getAuthenticatedUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        return user.getId();
    }
}

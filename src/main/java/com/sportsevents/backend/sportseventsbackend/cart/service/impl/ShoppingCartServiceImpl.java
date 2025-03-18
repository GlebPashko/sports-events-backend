package com.sportsevents.backend.sportseventsbackend.cart.service.impl;

import com.sportsevents.backend.sportseventsbackend.cart.dto.cartitem.CartItemRequestDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.shoppingcart.ShoppingCartResponseDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.shoppingcart.UpdateShoppingCartRequestDto;
import com.sportsevents.backend.sportseventsbackend.cart.mapper.CartItemMapper;
import com.sportsevents.backend.sportseventsbackend.cart.mapper.ShoppingCartMapper;
import com.sportsevents.backend.sportseventsbackend.cart.model.CartItem;
import com.sportsevents.backend.sportseventsbackend.cart.model.ShoppingCart;
import com.sportsevents.backend.sportseventsbackend.cart.repository.cartitem.CartItemRepository;
import com.sportsevents.backend.sportseventsbackend.cart.repository.shoppingcart.ShoppingCartRepository;
import com.sportsevents.backend.sportseventsbackend.cart.service.ShoppingCartService;
import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.event.EventRepository;
import com.sportsevents.backend.sportseventsbackend.user.model.User;
import com.sportsevents.backend.sportseventsbackend.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Lazy
    @Autowired
    private UserService userService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final EventRepository eventRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto getShoppingCart() {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(userService.getUser().getId());

        return shoppingCartMapper.toShoppingCartResponseDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto addBookToShoppingCart(CartItemRequestDto requestDto) {
        Event event = eventRepository.findById(requestDto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Book with id "
                        + requestDto.getEventId() + " not found"));
        ShoppingCart shoppingCart =
                shoppingCartRepository.findByUserId(userService.getUser().getId());

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
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(userService.getUser().getId());

        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(id, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toShoppingCartResponseDto(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(userService.getUser().getId());
        cartItemRepository.deleteByIdAndShoppingCartId(id, shoppingCart.getId());
    }

    @Override
    public void addShoppingCartToUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }
}

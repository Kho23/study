package com.green.blue.red.controller;

import com.green.blue.red.dto.CartItemDto;
import com.green.blue.red.dto.CartItemListDto;
import com.green.blue.red.dto.MemberDto;
import com.green.blue.red.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PreAuthorize("#itemDto.email == authentication.name")
    @PostMapping("/change")
    public List<CartItemListDto> changeCart(@RequestBody CartItemDto itemDto){
        log.info("itemDto:{}",itemDto);
        if(itemDto.getQty()<=0){
            return cartService.remove(itemDto.getCino());
        }
        return cartService.addOrModify(itemDto);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/items")
    public List<CartItemListDto> getCartItems(Principal principal){

        Authentication authentication = (Authentication) principal;
        MemberDto memberDto = (MemberDto) authentication.getPrincipal();

        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = principal.getName();
        log.info("----------------");
        log.info("email={}",email);
        log.info("cartservice.get = {}",cartService.getCartItems(email));
        return cartService.getCartItems(email);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{cino}")
    public List<CartItemListDto> removeFromCart(@PathVariable("cino") Long cino){
        log.info("cartItem no: {}", cino);
        return cartService.remove(cino);
    }
}

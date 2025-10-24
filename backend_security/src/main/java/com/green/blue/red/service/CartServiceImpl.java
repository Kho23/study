package com.green.blue.red.service;

import com.green.blue.red.domain.Cart;
import com.green.blue.red.domain.CartItem;
import com.green.blue.red.domain.Member;
import com.green.blue.red.domain.Product;
import com.green.blue.red.dto.CartItemDto;
import com.green.blue.red.dto.CartItemListDto;
import com.green.blue.red.repository.CartItemRepository;
import com.green.blue.red.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    @Autowired
    private final CartItemRepository cartItemRepository;
    @Autowired
    private final CartRepository cartRepository;
    @Override
    public List<CartItemListDto> addOrModify(CartItemDto cartItemDto) {
        String email = cartItemDto.getEmail();
        Long pno = cartItemDto.getPno();
        int qty = cartItemDto.getQty();
        Long cino = cartItemDto.getCino();
        if(cino!=null){
            Optional<CartItem> cartItemResult = cartItemRepository.findById(cino);
            CartItem cartItem = cartItemResult.orElseThrow();
            cartItem.changeQty(qty);
            cartItemRepository.save(cartItem);
            return getCartItems(email);
        }
        Cart cart = getCart(email);
        CartItem cartItem = null;
        cartItem = cartItemRepository.getItemOfCartDtoByEmail(email,pno);
        if(cartItem==null){
            Product product = Product.builder().pno(pno).build();
            cartItem = CartItem.builder()
                    .product(product)
                    .cart(cart)
                    .qty(qty)
                    .build();
        }else{
            cartItem.changeQty(qty);
        }
        cartItemRepository.save(cartItem);
        return getCartItems(email);
    }
    private Cart getCart(String email){
        Cart cart = null;
        Optional<Cart> result = cartRepository.getCartOfMember(email);
        if(result.isEmpty()){
            log.info("Cart of the member is not exist");
            Member member= Member.builder()
                    .email(email)
                    .build();
            Cart tempCart = Cart.builder()
                    .owner(member)
                    .build();
            cart = cartRepository.save(tempCart);
        }else{
            cart=result.get();
        }
        return cart;
    }

    @Override
    public List<CartItemListDto> getCartItems(String email) {
        return cartItemRepository.getItemOfCartDtoByEmail(email);
    }

    @Override
    public List<CartItemListDto> remove(Long cino) {
        Long cno = cartItemRepository.getCartFromItem(cino);
        cartItemRepository.deleteById(cino);
        return cartItemRepository.getItemsOfCartDtoByCart(cno);
    }
}

package com.green.blue.red.controller.repository;

import com.green.blue.red.domain.Cart;
import com.green.blue.red.domain.CartItem;
import com.green.blue.red.domain.Member;
import com.green.blue.red.domain.Product;
import com.green.blue.red.repository.CartItemRepository;
import com.green.blue.red.repository.CartRepository;
import com.green.blue.red.repository.MemberRepository;
import com.green.blue.red.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.Optional;

@SpringBootTest
@Slf4j
public class CartRepositoryTests {

    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;

    // 반드시 아래 2개 리포지토리도 주입하세요
    @Autowired private MemberRepository memberRepository;
    @Autowired private ProductRepository productRepository;

    @Transactional
    @Commit
    @Test
    public void testInsertByProduct() {
        log.info("testInsertByProduct start");
        String email = "user1@naver.com";
        Long pno = 5L;
        int qty = 1;

        // 0) 회원 보장: (없으면 생성)
        Member owner = memberRepository.findById(email)
                .orElseGet(() -> {
                    Member m = Member.builder()
                            .email(email)
                            .pw("pw")
                            .nickname("user1")
                            .social(false)
                            .build();
                    return memberRepository.save(m);
                });

        // 1) 기존 CartItem 있는지 확인
        CartItem cartItem = cartItemRepository.getItemOfCartDtoByEmail(email, pno);
        if (cartItem != null) {
            // 엔티티 메서드 이름 확인: changetQty vs changeQty (실제 이름과 맞추세요!)
            cartItem.changeQty(qty); // 혹은 cartItem.changeQty(qty);
            cartItemRepository.saveAndFlush(cartItem);
            return;
        }

        // 2) 사용자의 Cart 확보 (없으면 생성)
        Optional<Cart> result = cartRepository.getCartOfMember(email);
        Cart cart = result.orElseGet(() -> cartRepository.save(
                Cart.builder().owner(owner).build()
        ));

        log.info("cart: {}", cart);

        // 3) Product 확보: FK 대상이 실제 DB에 있어야 함
        Product product = productRepository.findById(pno)
                .orElseGet(() -> {
                    // 테스트용 더미 상품 생성 (필수 필드 채워주세요)
                    Product p = Product.builder()
                            .pname("테스트상품")
                            .price(10000)
                            .build();
                    p = productRepository.save(p);
                    // pno를 5로 강제해야 한다면, 미리 5번 상품을 migration/insert 해두는 게 더 안전합니다.
                    return p;
                });

        // 4) CartItem 생성/저장 (관리 엔티티 사용!)
        CartItem newItem = CartItem.builder()
                .product(product)  // 영속/관리 엔티티
                .cart(cart)        // 영속/관리 엔티티
                .qty(qty)
                .build();

        cartItemRepository.saveAndFlush(newItem);
    }
}

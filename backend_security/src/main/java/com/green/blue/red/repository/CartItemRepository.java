package com.green.blue.red.repository;

import com.green.blue.red.domain.CartItem;
import com.green.blue.red.dto.CartItemDto;
import com.green.blue.red.dto.CartItemListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT NEW com.green.blue.red.dto.CartItemListDto(" +
            "ci.cino, ci.qty, p.pno, p.pname, p.price, pi.fileName ) " +
            " FROM CartItem ci INNER JOIN Cart mc ON ci.cart = mc " +
            " LEFT JOIN Product p ON ci.product = p LEFT JOIN p.imageList pi WHERE mc.owner.email = :email" +
            " AND pi.ord=0 ORDER BY ci.cino DESC"
    )
    public List<CartItemListDto> getItemOfCartDtoByEmail(@Param("email") String email);

    // 🚨 공백 추가: " where" 대신 " WHERE " 또는 다음 줄에 공백 추가
    @Query("select ci from CartItem ci inner join Cart c on ci.cart = c where " // 🔴 여기에 공백 추가
            + "c.owner.email = :email and ci.product.pno = :pno")
    public CartItem getItemOfCartDtoByEmail(@Param("email") String email, @Param("pno") Long pno);

    @Query("select c.cno from Cart c inner join CartItem ci on ci.cart = c where ci.cino = :cino")
    public Long getCartFromItem(@Param("cino")Long cino);

    @Query("SELECT NEW com.green.blue.red.dto.CartItemListDto(ci.cino, ci.qty, p.pno, p.pname, p.price, pi.fileName ) " +
            "FROM CartItem ci INNER JOIN Cart mc ON ci.cart = mc LEFT JOIN Product p ON ci.product = p " +
            "LEFT JOIN p.imageList pi WHERE mc.cno = :cno AND pi.ord=0 ORDER BY ci.cino DESC"
    )
    public List<CartItemListDto> getItemsOfCartDtoByCart(@Param("cno") Long cno);
}
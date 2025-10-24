package com.green.blue.red.repository;

import com.green.blue.red.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "imageList")
    @Query("select p from Product p where p.pno=:pno")
    Optional<Product> selectOne(@PathVariable Long pno);

    @Modifying
    @Query("update Product p set p.delFlag = :flag where p.pno=:pno")
    void updateToDelete(@Param("pno") Long pno, @Param("flag") boolean flag);

    @Query
            (value = "select p,pi from Product p left join p.imageList pi where p.delFlag=false",
                    countQuery = "select count(p) from Product p where p.delFlag=false")
    Page<Object[]> selectList(Pageable pageable);
}

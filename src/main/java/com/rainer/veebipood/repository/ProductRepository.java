package com.rainer.veebipood.repository;

import com.rainer.veebipood.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    //List<Product> findByCategory_Id(Long id);

    // avaleht lehel
    Page<Product> findByActiveTrueOrderByNameAsc(Pageable pageable);

    // admin lehel
    List<Product> findByOrderByNameAsc();

    Page<Product> findByActiveTrue(Pageable pageable);

    Page<Product> findByCategory_IdAndActiveTrueOrderByNameAsc(Long id, Pageable pageable);

    List<Product> findByPriceNotNullOrderByPriceDesc();


//    Page<Product> findByActiveTrueOrderByPriceAsc(Pageable pageable);
//    Page<Product> findByCategory_IdAndActiveTrueOrderByPriceAsc(Long id, Pageable pageable);
//    Page<Product> findByActiveTrueOrderByPriceDesc(Pageable pageable);
//    Page<Product> findByCategory_IdAndActiveTrueOrderByPriceDesc(Long id, Pageable pageable);

}

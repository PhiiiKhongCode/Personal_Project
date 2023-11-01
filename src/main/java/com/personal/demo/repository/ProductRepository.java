package com.personal.demo.repository;

import com.personal.demo.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    @Query(value = "from Product p where p.category.id = '1003' and p.available=true", nativeQuery = false)
    List<Product> findCatePhone();

    @Query(value = "from Product p where p.category.id = '1001' and p.available=true", nativeQuery = false)
    List<Product> findCateLaptop();

    @Query(value = "from Product p where p.category.id = '1002' and p.available=true", nativeQuery = false)
    List<Product> findCateCamera();

    @Query(value = "from Product p where p.available = :available", nativeQuery = false)
    List<Product> findbyAvailable(@Param("available") Boolean avai);

    Optional<Product> findById(Integer id);
}
